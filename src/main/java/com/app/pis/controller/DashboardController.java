package com.app.pis.controller;

import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", dashboardService.getSummary()));
    }

    @GetMapping("/revenue-chart")
    public ResponseEntity<?> getRevenueChart() {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", dashboardService.getRevenueChart()));
    }

    @GetMapping("/top-medicines")
    public ResponseEntity<?> getTopMedicines() {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", dashboardService.getTopMedicines()));
    }

    @GetMapping("/alerts")
    public ResponseEntity<?> getAlerts() {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", dashboardService.getAlerts()));
    }
}
