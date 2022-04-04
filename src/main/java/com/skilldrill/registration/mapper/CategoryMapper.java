package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.CategoryDto;
import com.skilldrill.registration.model.Categories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends ForeignKeyMapper {

    CategoryDto toDto(Categories categories);

    Categories toCategory(CategoryDto categoryDto);
}
