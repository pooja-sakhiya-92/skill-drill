package com.skilldrill.registration.service;

import com.skilldrill.registration.mapper.CategoryMapper;
import com.skilldrill.registration.model.Categories;
import com.skilldrill.registration.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Before
    public void setUp() {
        when(categoryRepository.save(Mockito.any())).thenReturn(dummyCategory());
        when(categoryRepository.findByCategoryName(Mockito.any())).thenReturn(Optional.of(dummyCategory()));

    }

    public static Categories dummyCategory() {
        return new Categories("abc123", Arrays.asList("xyz", "abc"),
                "JAVA", null, "description...");
    }

    @Test
    public void testAddCategories() {
        Categories categories = categoryService.addCategory(categoryMapper.toDto(dummyCategory()));
        assertEquals(categories.getCategoryName(), "JAVA");
        assertNotEquals(categories.getDescriptions(), "dummy...");
    }

    @Test
    public void testDeleteCategory() {
        Categories categories = dummyCategory();
        categoryService.deleteCategory("JAVA");
        verify(categoryRepository, times(1)).delete(categories);

    }

    @Test
    public void testGetAllCategory() {
        List<Categories> categoriesList = categoryService.showAllCategories();
        categoriesList.add(dummyCategory());
        when(categoryRepository.findAll()).thenReturn(categoriesList);
        assertEquals(categoriesList.size(), 1);
        assertFalse(categoriesList.isEmpty());
    }
}
