package com.app.pis.controller;

import com.app.pis.dto.request.CustomerRequest;
import com.app.pis.dto.response.CustomerResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "successfully",
                customers
        ));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse responseData = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Customer created successfully",
                responseData
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody CustomerRequest request) {
        CustomerResponse responseData = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer updated successfully",
                responseData
        ));
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleStatus(@PathVariable Integer id) {
        customerService.toggleStatus(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer status toggled successfully",
                null
        ));
    }
}
