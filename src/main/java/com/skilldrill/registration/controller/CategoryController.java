package com.skilldrill.registration.controller;

import com.skilldrill.registration.dto.CategoryDto;
import com.skilldrill.registration.service.BadgeService;
import com.skilldrill.registration.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController( CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteCategory(@RequestParam String categoryName) {
        categoryService.deleteCategory(categoryName);
        return ResponseEntity.ok(categoryName+" category deleted!");
    }

    @GetMapping("getCategories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.showAllCategories());
    }


}
