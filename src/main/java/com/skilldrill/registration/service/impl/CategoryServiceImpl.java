package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.dto.CategoryDto;
import com.skilldrill.registration.exceptions.InvalidRequestException;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.CategoryMapper;
import com.skilldrill.registration.model.Categories;
import com.skilldrill.registration.repository.CategoryRepository;
import com.skilldrill.registration.service.CategoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Categories addCategory(CategoryDto categoryDto) {
        Categories categories = categoryMapper.toCategory(categoryDto);
        if(!categoryRepository.findByCategoryName(categories.getCategoryName()).isPresent()) {
            categoryRepository.save(categories);
        } else {
            throw new InvalidRequestException("Category already exist");
        }
        return categories;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Categories categories = categoryMapper.toCategory(categoryDto);
        Optional<Categories> category = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if (category.isPresent()) {
            String id = category.get().getId();
            Categories uCa = categoryRepository.findById(new ObjectId(id))
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            uCa.setDescriptions(categoryDto.getDescriptions());
            uCa.setParent(categoryDto.getParent());
            uCa.setSubjects(categoryDto.getSubjects());
            categoryRepository.save(uCa);
        } else {
            throw new NotFoundException("Category not Found");
        }
        return categoryMapper.toDto(categories);
    }

    @Override
    public void deleteCategory(String categoryName) {
        Categories categories = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        categoryRepository.delete(categories);
    }

    @Override
    public List<Categories> showAllCategories() {
        return categoryRepository.findAll();
    }
}
