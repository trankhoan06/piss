package com.app.pis.controller;

import com.app.pis.dto.request.CategoryRequest;
import com.app.pis.dto.response.CategoryResponse;
import com.app.pis.dto.wrap.ApiResponse;
import com.app.pis.ex.BadRequestException;
import com.app.pis.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createUnit(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.createCategory(request);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Category created successfully",
                category
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllUnit () {
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "successfully",
                        categoryService.getAllCategory()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUnit(@PathVariable Integer id, @Valid @RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, request);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Category updated successfully",
                categoryResponse
        );
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteUnit(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            // todo: check lại bussiness requirement
        } catch (DataIntegrityViolationException exception) {
            throw new BadRequestException("Category is currently in use");
        }
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Category deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }






}
