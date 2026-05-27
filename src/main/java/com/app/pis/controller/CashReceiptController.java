package com.app.pis.controller;

import com.app.pis.dto.request.CashReceiptRequest;
import com.app.pis.dto.response.CashReceiptResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.CashReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cash-receipts")
public class CashReceiptController {

    @Autowired
    private CashReceiptService cashReceiptService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CashReceiptResponse> responses = cashReceiptService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", responses));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CashReceiptRequest request) {
        CashReceiptResponse response = cashReceiptService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        cashReceiptService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", null));
    }
}
