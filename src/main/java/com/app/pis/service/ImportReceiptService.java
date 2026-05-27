package com.app.pis.service;

import com.app.pis.dto.request.ImportReceiptDetailRequest;
import com.app.pis.dto.request.ImportReceiptRequest;
import com.app.pis.dto.response.ImportReceiptResponse;
import com.app.pis.entity.ImportReceipt;
import com.app.pis.entity.ImportReceiptDetail;
import com.app.pis.entity.Inventory;
import com.app.pis.entity.Medicine;
import com.app.pis.entity.Supplier;
import com.app.pis.entity.User;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.ImportReceiptMapper;
import com.app.pis.repository.ImportReceiptDetailRepository;
import com.app.pis.repository.ImportReceiptRepository;
import com.app.pis.repository.InventoryRepository;
import com.app.pis.repository.MedicineRepository;
import com.app.pis.repository.SupplierRepository;
import com.app.pis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportReceiptService {

    @Autowired
    private ImportReceiptRepository importReceiptRepository;

    @Autowired
    private ImportReceiptDetailRepository importReceiptDetailRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private ImportReceiptMapper importReceiptMapper;

    @Autowired
    private CashReceiptService cashReceiptService;

    @Transactional(readOnly = true)
    public List<ImportReceiptResponse> getAllImportReceipts() {
        return importReceiptRepository.findAll().stream()
                .map(importReceiptMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ImportReceiptResponse createImportReceipt(ImportReceiptRequest request) {
        ImportReceipt receipt = importReceiptMapper.toEntity(request);
        receipt.setDate(LocalDate.now());
        receipt.setStatus("TEMPORARY");

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        receipt.setUser(user);

        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new BadRequestException("Supplier not found"));
        receipt.setSupplier(supplier);

        ImportReceipt savedReceipt = importReceiptRepository.save(receipt);
        List<ImportReceiptDetail> details = new ArrayList<>();

        for (ImportReceiptDetailRequest detailReq : request.details()) {
            ImportReceiptDetail detail = importReceiptMapper.toDetailEntity(detailReq);
            Medicine medicine = medicineRepository.findById(detailReq.medicineId())
                    .orElseThrow(() -> new BadRequestException("Medicine not found"));
            detail.setMedicine(medicine);
            detail.setImportReceipt(savedReceipt);
            details.add(importReceiptDetailRepository.save(detail));
        }

        savedReceipt.setImportReceiptDetails(details);
        return importReceiptMapper.toResponse(savedReceipt);
    }

    @Transactional
    public ImportReceiptResponse acceptImportReceipt(Integer id) {
        ImportReceipt receipt = importReceiptRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Import Receipt not found"));

        if (!"TEMPORARY".equals(receipt.getStatus())) {
            throw new BadRequestException("Only TEMPORARY receipts can be accepted");
        }

        for (ImportReceiptDetail detail : receipt.getImportReceiptDetails()) {
            Inventory inventory = inventoryRepository.findByMedicineIdAndSupplierIdAndBatchNumber(
                    detail.getMedicine().getId(), receipt.getSupplier().getId(), detail.getBatchNumber()
            ).orElse(new Inventory());

            if (inventory.getId() == null) {
                inventory.setMedicine(detail.getMedicine());
                inventory.setSupplier(receipt.getSupplier());
                inventory.setBatchNumber(detail.getBatchNumber());
                inventory.setExpirationDate(detail.getExpirationDate());
                inventory.setImportPrice(detail.getPurchasePrice());
                inventory.setStockQuantity(detail.getQuantity());
            } else {
                inventory.setStockQuantity(inventory.getStockQuantity() + detail.getQuantity());
            }
            inventoryRepository.save(inventory);
        }

        receipt.setStatus("ACCEPTED");
        ImportReceipt savedReceipt = importReceiptRepository.save(receipt);

        java.math.BigDecimal totalAmount = savedReceipt.getImportReceiptDetails().stream()
                .map(d -> d.getPurchasePrice().multiply(java.math.BigDecimal.valueOf(d.getQuantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        com.app.pis.dto.request.CashReceiptRequest cashReceiptReq = new com.app.pis.dto.request.CashReceiptRequest(
                totalAmount,
                "EXPENSE",
                "Thanh toán phiếu nhập kho #" + savedReceipt.getId(),
                "IMPORT",
                savedReceipt.getId(),
                savedReceipt.getUser().getId()
        );
        cashReceiptService.create(cashReceiptReq);

        return importReceiptMapper.toResponse(savedReceipt);
    }

    @Transactional
    public ImportReceiptResponse cancelImportReceipt(Integer id) {
        ImportReceipt receipt = importReceiptRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Import Receipt not found"));

        if ("CANCELLED".equals(receipt.getStatus())) {
            throw new BadRequestException("Receipt is already cancelled");
        }

        // Only decrease inventory if the receipt was accepted
        if ("ACCEPTED".equals(receipt.getStatus())) {
            for (ImportReceiptDetail detail : receipt.getImportReceiptDetails()) {
                Inventory inventory = inventoryRepository.findByMedicineIdAndSupplierIdAndBatchNumber(
                        detail.getMedicine().getId(), receipt.getSupplier().getId(), detail.getBatchNumber()
                ).orElseThrow(() -> new BadRequestException("Inventory not found to reverse import"));

                if (inventory.getStockQuantity() < detail.getQuantity()) {
                    throw new BadRequestException("Not enough stock to cancel this import receipt");
                }
                inventory.setStockQuantity(inventory.getStockQuantity() - detail.getQuantity());
                inventoryRepository.save(inventory);
            }
        }

        receipt.setStatus("CANCELLED");
        return importReceiptMapper.toResponse(importReceiptRepository.save(receipt));
    }

    @Transactional
    public List<ImportReceiptDetailRequest> parseExcelFile(org.springframework.web.multipart.MultipartFile file) {
        List<ImportReceiptDetailRequest> details = new ArrayList<>();
        try (java.io.InputStream is = file.getInputStream();
             org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is)) {
            
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null) continue;

                String medicineId = "";
                if (row.getCell(0) != null) {
                    if (row.getCell(0).getCellType() == org.apache.poi.ss.usermodel.CellType.STRING) {
                        medicineId = row.getCell(0).getStringCellValue();
                    } else if (row.getCell(0).getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
                        medicineId = String.valueOf((int)row.getCell(0).getNumericCellValue());
                    }
                }

                int quantity = row.getCell(1) != null ? (int) row.getCell(1).getNumericCellValue() : 0;
                java.math.BigDecimal price = row.getCell(2) != null ? java.math.BigDecimal.valueOf(row.getCell(2).getNumericCellValue()) : java.math.BigDecimal.ZERO;
                
                String batch = "";
                if (row.getCell(3) != null) {
                    if (row.getCell(3).getCellType() == org.apache.poi.ss.usermodel.CellType.STRING) {
                        batch = row.getCell(3).getStringCellValue();
                    } else if (row.getCell(3).getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
                        batch = String.valueOf((int)row.getCell(3).getNumericCellValue());
                    }
                }

                LocalDate expDate = null;
                org.apache.poi.ss.usermodel.Cell dateCell = row.getCell(4);
                if (dateCell != null) {
                    if (dateCell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
                        expDate = dateCell.getLocalDateTimeCellValue().toLocalDate();
                    }
                }

                details.add(new ImportReceiptDetailRequest(medicineId, quantity, price, expDate, batch));
            }
        } catch (Exception e) {
            throw new BadRequestException("Error parsing Excel file: " + e.getMessage());
        }
        return details;
    }
}
