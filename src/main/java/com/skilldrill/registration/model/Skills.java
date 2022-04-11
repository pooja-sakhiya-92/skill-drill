package com.skilldrill.registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "skills")
@AllArgsConstructor
@NoArgsConstructor
public class Skills {
    @Id
    private String id;
    private Categories categories;
    private String skillName;
    private String description;
}
