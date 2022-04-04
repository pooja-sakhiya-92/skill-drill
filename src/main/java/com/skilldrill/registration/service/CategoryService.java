package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.CategoryDto;
import com.skilldrill.registration.model.Categories;

import java.util.List;

public interface CategoryService {

    Categories addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(String categoryName);

    List<Categories> showAllCategories();
}
