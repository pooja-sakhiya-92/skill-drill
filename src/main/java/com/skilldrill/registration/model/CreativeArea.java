package com.skilldrill.registration.model;

import com.skilldrill.registration.enums.CreativeSkills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "creativeArea")
public class CreativeArea {

    @Id
    private String id;

    private CreativeSkills creativeSkill;

    private String achievements;

    private String specialization;

    private User user;
}
