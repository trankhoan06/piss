package com.app.pis.controller;

import com.app.pis.dto.request.InvoiceRequest;
import com.app.pis.dto.response.InvoiceResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<?> getAllInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "successfully",
                invoiceService.getAllInvoices(page, size)
        ));
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        InvoiceResponse responseData = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Invoice created successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptInvoice(@PathVariable Integer id) {
        InvoiceResponse responseData = invoiceService.acceptInvoice(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Invoice accepted successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelInvoice(@PathVariable Integer id) {
        InvoiceResponse responseData = invoiceService.cancelInvoice(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Invoice cancelled successfully",
                responseData
        ));
    }
}
