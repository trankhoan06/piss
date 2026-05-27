package com.app.pis.controller;

import com.app.pis.dto.request.SupplierGroupRequest;
import com.app.pis.dto.response.SupplierGroupResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.SupplierGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier-groups")
public class SupplierGroupController {

    @Autowired
    private SupplierGroupService supplierGroupService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<SupplierGroupResponse> responses = supplierGroupService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", responses));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SupplierGroupRequest request) {
        SupplierGroupResponse response = supplierGroupService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "successfully", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody SupplierGroupRequest request) {
        SupplierGroupResponse response = supplierGroupService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        supplierGroupService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", null));
    }
}
