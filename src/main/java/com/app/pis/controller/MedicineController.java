package com.app.pis.controller;

import com.app.pis.dto.request.MedicineRequest;
import com.app.pis.entity.Medicine;
import com.app.pis.service.MedicineService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public ResponseEntity<?> create (@RequestBody MedicineRequest request) {
        Medicine medicine = medicineService.createMedicine(request);
        return ResponseEntity.ok("ss");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable String id) {
        medicineService.deleteMedicine(id);

        return ResponseEntity.ok().build();

    }



}
