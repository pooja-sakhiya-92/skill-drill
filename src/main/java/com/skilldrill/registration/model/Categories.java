package com.skilldrill.registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(value = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
    @Id
    private String id;
    private List<String> subjects;
    private String categoryName;

    private String parent;
    private String descriptions;

}
