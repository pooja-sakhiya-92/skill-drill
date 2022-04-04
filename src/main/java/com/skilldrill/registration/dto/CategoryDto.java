package com.skilldrill.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class CategoryDto {

    private String id;

    @NotNull(message = "Subjects shouldn't be empty/null")
    private List<String> subjects;
    @NotBlank(message = "Please enter the category name")
    private String categoryName;

    private String parent;
    @Pattern(regexp = "^.{1,50}$",message = "description word limit is 50.")
    private String descriptions;
}
