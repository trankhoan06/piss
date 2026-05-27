package com.app.pis.controller;

import com.app.pis.dto.request.SupplierRequest;
import com.app.pis.dto.response.SupplierResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.ex.BadRequestException;
import com.app.pis.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/supplies")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @GetMapping
    public ResponseEntity<?> getAllSupplier () {
         supplierService.getAllSupplier();
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "successfully",
                        supplierService.getAllSupplier()));

    }

    @PostMapping
    public ResponseEntity<?> createSupplier (@Valid @RequestBody SupplierRequest request) {
        SupplierResponse supplier = supplierService.createSupplier(request);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Category created successfully",
                supplier
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSupplier (@PathVariable Integer id, @RequestBody SupplierRequest request) {
        SupplierResponse supplierResponse = supplierService.updateSupplier(id, request);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "SupplierResponse updated successfully",
                supplierResponse
        );
        return ResponseEntity.ok(response);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier (@PathVariable Integer id) {
        try {
            supplierService.deleteSupplier(id);
            // todo: check lại bussiness requirement
        } catch (DataIntegrityViolationException exception) {
            throw new BadRequestException("Supplier is currently in use");
        }
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Supplier deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }


}
