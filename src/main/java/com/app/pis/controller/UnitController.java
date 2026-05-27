package com.app.pis.controller;

import com.app.pis.dto.request.UnitRequest;
import com.app.pis.dto.response.UnitResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.ex.BadRequestException;
import com.app.pis.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @PostMapping
    public ResponseEntity<?> createUnit(@Valid @RequestBody UnitRequest request) {
        UnitResponse unit = unitService.createUnit(request);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Unit created successfully",
                unit
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllUnit () {
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "successfully",
                        unitService.getAllUnits()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUnit(@PathVariable Integer id, @RequestBody UnitRequest request) {
        UnitResponse unitResponse = unitService.updateUnit(id, request);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Unit updated successfully",
                unitResponse
        );
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Integer id) {
        try {
            unitService.deleteUnit(id);
        } catch (DataIntegrityViolationException exception) {
            throw new BadRequestException("Unit is currently in use");
        }
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Unit deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }
}
