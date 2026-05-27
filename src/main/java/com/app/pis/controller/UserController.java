package com.app.pis.controller;

import com.app.pis.dto.request.CreateUserRequest;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "User created successfully",
                null
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        System.out.println(authentication);
        authentication.getAuthorities()
                .forEach(a -> System.out.println(a.getAuthority()));
        return ResponseEntity.ok(authentication.getAuthorities());
    }



}
