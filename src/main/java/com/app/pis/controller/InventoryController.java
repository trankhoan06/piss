package com.app.pis.controller;

import com.app.pis.dto.response.InventoryResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.entity.Inventory;
import com.app.pis.mapper.InventoryMapper;
import com.app.pis.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/inventories")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @GetMapping
    public ResponseEntity<?> getAllInventories() {
        List<InventoryResponse> inventories = inventoryRepository.findAll().stream()
                .map(inventoryMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "successfully",
                inventories
        ));
    }
}
