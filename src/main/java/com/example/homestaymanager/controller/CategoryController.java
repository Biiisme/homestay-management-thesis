package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.model.Category;
import com.example.homestaymanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ApiResponse<Integer> createCategory(@RequestBody Category category) {
        int i= categoryService.createCategory(category);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED, category.getId());
    }

    @GetMapping("/categories/{categoryId}")
    public ApiResponse<Category> getCategoryById(@PathVariable int categoryId) {
        Category category= categoryService.getCategoryByID(categoryId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,category);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ApiResponse<?> deleteCategoryById(@PathVariable int categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }
}