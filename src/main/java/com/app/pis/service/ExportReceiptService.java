package com.app.pis.service;

import com.app.pis.dto.request.ExportReceiptDetailRequest;
import com.app.pis.dto.request.ExportReceiptRequest;
import com.app.pis.dto.response.ExportReceiptResponse;
import com.app.pis.entity.ExportReceipt;
import com.app.pis.entity.ExportReceiptDetail;
import com.app.pis.entity.Inventory;
import com.app.pis.entity.User;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.ExportReceiptMapper;
import com.app.pis.repository.ExportReceiptDetailRepository;
import com.app.pis.repository.ExportReceiptRepository;
import com.app.pis.repository.InventoryRepository;
import com.app.pis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportReceiptService {

    @Autowired
    private ExportReceiptRepository exportReceiptRepository;

    @Autowired
    private ExportReceiptDetailRepository exportReceiptDetailRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExportReceiptMapper exportReceiptMapper;

    @Transactional(readOnly = true)
    public List<ExportReceiptResponse> getAllExportReceipts() {
        return exportReceiptRepository.findAll().stream()
                .map(exportReceiptMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExportReceiptResponse createExportReceipt(ExportReceiptRequest request) {
        ExportReceipt receipt = exportReceiptMapper.toEntity(request);
        receipt.setDate(LocalDate.now());
        receipt.setStatus("TEMPORARY");

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        receipt.setUser(user);

        ExportReceipt savedReceipt = exportReceiptRepository.save(receipt);
        List<ExportReceiptDetail> details = new ArrayList<>();

        for (ExportReceiptDetailRequest detailReq : request.details()) {
            ExportReceiptDetail detail = exportReceiptMapper.toDetailEntity(detailReq);
            Inventory inventory = inventoryRepository.findById(detailReq.inventoryId())
                    .orElseThrow(() -> new BadRequestException("Inventory not found with ID: " + detailReq.inventoryId()));

            detail.setInventory(inventory);
            detail.setExportReceipt(savedReceipt);
            details.add(exportReceiptDetailRepository.save(detail));
        }

        savedReceipt.setExportReceiptDetails(details);
        return exportReceiptMapper.toResponse(savedReceipt);
    }

    @Transactional
    public ExportReceiptResponse acceptExportReceipt(Integer id) {
        ExportReceipt receipt = exportReceiptRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Export Receipt not found"));

        if (!"TEMPORARY".equals(receipt.getStatus())) {
            throw new BadRequestException("Only TEMPORARY receipts can be accepted");
        }

        for (ExportReceiptDetail detail : receipt.getExportReceiptDetails()) {
            Inventory inventory = detail.getInventory();

            if (inventory.getStockQuantity() < detail.getQuantity()) {
                throw new BadRequestException("Not enough stock for inventory ID: " + inventory.getId());
            }

            inventory.setStockQuantity(inventory.getStockQuantity() - detail.getQuantity());
            inventoryRepository.save(inventory);
        }

        receipt.setStatus("ACCEPTED");
        return exportReceiptMapper.toResponse(exportReceiptRepository.save(receipt));
    }

    @Transactional
    public ExportReceiptResponse cancelExportReceipt(Integer id) {
        ExportReceipt receipt = exportReceiptRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Export Receipt not found"));

        if ("CANCELLED".equals(receipt.getStatus())) {
            throw new BadRequestException("Receipt is already cancelled");
        }

        // Increase inventory logic only if accepted
        if ("ACCEPTED".equals(receipt.getStatus())) {
            for (ExportReceiptDetail detail : receipt.getExportReceiptDetails()) {
                Inventory inventory = detail.getInventory();
                inventory.setStockQuantity(inventory.getStockQuantity() + detail.getQuantity());
                inventoryRepository.save(inventory);
            }
        }

        receipt.setStatus("CANCELLED");
        return exportReceiptMapper.toResponse(exportReceiptRepository.save(receipt));
    }
}
