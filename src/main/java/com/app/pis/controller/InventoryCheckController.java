package com.app.pis.controller;

import com.app.pis.dto.request.InventoryCheckRequest;
import com.app.pis.dto.response.InventoryCheckResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.InventoryCheckService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory-checks")
public class InventoryCheckController {

    @Autowired
    private InventoryCheckService inventoryCheckService;

    @GetMapping
    public ResponseEntity<?> getAllInventoryChecks() {
        List<InventoryCheckResponse> checks = inventoryCheckService.getAllInventoryChecks();
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "successfully",
                checks
        ));
    }

    @PostMapping
    public ResponseEntity<?> createInventoryCheck(@Valid @RequestBody InventoryCheckRequest request) {
        InventoryCheckResponse responseData = inventoryCheckService.createInventoryCheck(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Inventory check created successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptInventoryCheck(@PathVariable Integer id) {
        InventoryCheckResponse responseData = inventoryCheckService.acceptInventoryCheck(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Inventory check accepted successfully",
                responseData
        ));
    }
}
