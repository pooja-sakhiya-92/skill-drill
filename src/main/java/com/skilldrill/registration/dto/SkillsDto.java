package com.skilldrill.registration.dto;

import com.skilldrill.registration.model.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillsDto {
    private String id;
    @NotNull(message = "category section cannot be empty!")
    private Categories categories;
    @NotBlank(message = "Please add skill name")
    private String skillName;
    @Pattern(regexp = "^.{1,50}$",message = "description word limit is 50.")
    private String description;
}
