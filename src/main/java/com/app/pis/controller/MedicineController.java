package com.app.pis.controller;

import com.app.pis.dto.request.MedicineRequest;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public ResponseEntity<?> getAllMedicines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", medicineService.getAllMedicines(page, size, search)));
    }

    @PostMapping
    public ResponseEntity<?> createMedicine(@RequestBody MedicineRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "successfully", medicineService.createMedicine(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedicine(@PathVariable String id, @RequestBody MedicineRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", medicineService.updateMedicine(id, request)));
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleStatus(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", medicineService.toggleStatus(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable String id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", null));
    }
}
