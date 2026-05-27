package com.app.pis.controller;

import com.app.pis.dto.request.ImportReceiptRequest;
import com.app.pis.dto.response.ImportReceiptResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.ImportReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/import-receipts")
public class ImportReceiptController {

    @Autowired
    private ImportReceiptService importReceiptService;

    @GetMapping
    public ResponseEntity<?> getAllImportReceipts() {
        List<ImportReceiptResponse> receipts = importReceiptService.getAllImportReceipts();
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "successfully",
                receipts
        ));
    }

    @PostMapping
    public ResponseEntity<?> createImportReceipt(@Valid @RequestBody ImportReceiptRequest request) {
        ImportReceiptResponse responseData = importReceiptService.createImportReceipt(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Import receipt created successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptImportReceipt(@PathVariable Integer id) {
        ImportReceiptResponse responseData = importReceiptService.acceptImportReceipt(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Import receipt accepted successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelImportReceipt(@PathVariable Integer id) {
        ImportReceiptResponse responseData = importReceiptService.cancelImportReceipt(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Import receipt cancelled successfully",
                responseData
        ));
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        List<com.app.pis.dto.request.ImportReceiptDetailRequest> details = importReceiptService.parseExcelFile(file);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Excel parsed successfully",
                details
        ));
    }
}
