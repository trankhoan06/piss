package com.app.pis.service;

import com.app.pis.dto.request.InvoiceDetailRequest;
import com.app.pis.dto.request.InvoiceRequest;
import com.app.pis.dto.response.InvoiceResponse;
import com.app.pis.entity.Customer;
import com.app.pis.entity.Invoice;
import com.app.pis.entity.InvoiceDetail;
import com.app.pis.entity.Medicine;
import com.app.pis.entity.User;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.InvoiceMapper;
import com.app.pis.repository.CustomerRepository;
import com.app.pis.repository.InvoiceDetailRepository;
import com.app.pis.repository.InvoiceRepository;
import com.app.pis.repository.MedicineRepository;
import com.app.pis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.app.pis.dto.wrap.PageResponse;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private com.app.pis.repository.InventoryRepository inventoryRepository;

    @Autowired
    private CashReceiptService cashReceiptService;

    @Transactional(readOnly = true)
    public PageResponse<InvoiceResponse> getAllInvoices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Invoice> invoicePage = invoiceRepository.findAll(pageable);

        java.util.List<InvoiceResponse> content = invoicePage.getContent().stream()
                .map(invoiceMapper::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                invoicePage.getNumber(),
                invoicePage.getSize(),
                invoicePage.getTotalElements(),
                invoicePage.getTotalPages(),
                invoicePage.isLast()
        );
    }

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = invoiceMapper.toEntity(request);
        invoice.setSaleDate(LocalDateTime.now());
        invoice.setStatus("TEMPORARY");

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        invoice.setUser(user);

        if (request.customerId() != null) {
            Customer customer = customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new BadRequestException("Customer not found"));
            invoice.setCustomer(customer);
        }

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<InvoiceDetail> details = new ArrayList<>();
        for (InvoiceDetailRequest detailReq : request.invoiceDetails()) {
            InvoiceDetail detail = invoiceMapper.toDetailEntity(detailReq);
            Medicine medicine = medicineRepository.findById(detailReq.medicineId())
                    .orElseThrow(() -> new BadRequestException("Medicine not found: " + detailReq.medicineId()));
            
            detail.setMedicine(medicine);
            detail.setInvoice(savedInvoice);
            details.add(invoiceDetailRepository.save(detail));
        }

        savedInvoice.setInvoiceDetails(details);
        return invoiceMapper.toResponse(savedInvoice);
    }

    @Transactional
    public InvoiceResponse acceptInvoice(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invoice not found"));

        if (!"TEMPORARY".equals(invoice.getStatus())) {
            throw new BadRequestException("Only TEMPORARY invoices can be accepted");
        }

        // FIFO Inventory Deduction
        for (InvoiceDetail detail : invoice.getInvoiceDetails()) {
            int remainingToDeduct = detail.getQuantity();
            
            List<com.app.pis.entity.Inventory> inventories = inventoryRepository
                    .findByMedicineIdAndStockQuantityGreaterThanOrderByExpirationDateAsc(detail.getMedicine().getId(), 0);

            for (com.app.pis.entity.Inventory inv : inventories) {
                if (remainingToDeduct == 0) break;
                
                if (inv.getStockQuantity() >= remainingToDeduct) {
                    inv.setStockQuantity(inv.getStockQuantity() - remainingToDeduct);
                    remainingToDeduct = 0;
                } else {
                    remainingToDeduct -= inv.getStockQuantity();
                    inv.setStockQuantity(0);
                }
                inventoryRepository.save(inv);
            }

            if (remainingToDeduct > 0) {
                throw new BadRequestException("Không đủ hàng trong kho cho sản phẩm: " + detail.getMedicine().getName());
            }
        }

        invoice.setStatus("ACCEPTED");
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Auto-create CashReceipt (INCOME)
        com.app.pis.dto.request.CashReceiptRequest cashReceiptReq = new com.app.pis.dto.request.CashReceiptRequest(
                invoice.getTotalAmound(),
                "INCOME",
                "Thu tiền hoá đơn bán hàng #" + invoice.getId(),
                "INVOICE",
                invoice.getId(),
                invoice.getUser().getId()
        );
        cashReceiptService.create(cashReceiptReq);

        return invoiceMapper.toResponse(savedInvoice);
    }

    @Transactional
    public InvoiceResponse cancelInvoice(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invoice not found"));

        if ("CANCELLED".equals(invoice.getStatus())) {
            throw new BadRequestException("Invoice is already cancelled");
        }

        // We do not implement automated inventory rollback for Invoice in this MVP
        // Because FIFO deduction makes rollback complex (we don't know which exact batch was deducted).
        // For a complete system, we would need to store the exact mapping in a new table (InvoiceInventoryMapping).

        invoice.setStatus("CANCELLED");
        return invoiceMapper.toResponse(invoiceRepository.save(invoice));
    }
}
