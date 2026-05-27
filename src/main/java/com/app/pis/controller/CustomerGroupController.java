package com.app.pis.controller;

import com.app.pis.dto.request.CustomerGroupRequest;
import com.app.pis.dto.response.CustomerGroupResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.CustomerGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-groups")
public class CustomerGroupController {

    @Autowired
    private CustomerGroupService customerGroupService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CustomerGroupResponse> responses = customerGroupService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", responses));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CustomerGroupRequest request) {
        CustomerGroupResponse response = customerGroupService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "successfully", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody CustomerGroupRequest request) {
        CustomerGroupResponse response = customerGroupService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        customerGroupService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "successfully", null));
    }
}
