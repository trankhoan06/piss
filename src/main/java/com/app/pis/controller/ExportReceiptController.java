package com.app.pis.controller;

import com.app.pis.dto.request.ExportReceiptRequest;
import com.app.pis.dto.response.ExportReceiptResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.ExportReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/export-receipts")
public class ExportReceiptController {

    @Autowired
    private ExportReceiptService exportReceiptService;

    @GetMapping
    public ResponseEntity<?> getAllExportReceipts() {
        List<ExportReceiptResponse> receipts = exportReceiptService.getAllExportReceipts();
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "successfully",
                receipts
        ));
    }

    @PostMapping
    public ResponseEntity<?> createExportReceipt(@Valid @RequestBody ExportReceiptRequest request) {
        ExportReceiptResponse responseData = exportReceiptService.createExportReceipt(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Export receipt created successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptExportReceipt(@PathVariable Integer id) {
        ExportReceiptResponse responseData = exportReceiptService.acceptExportReceipt(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Export receipt accepted successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelExportReceipt(@PathVariable Integer id) {
        ExportReceiptResponse responseData = exportReceiptService.cancelExportReceipt(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Export receipt cancelled successfully",
                responseData
        ));
    }
}
