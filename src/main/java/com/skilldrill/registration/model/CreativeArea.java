package com.skilldrill.registration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skilldrill.registration.enums.CreativeSkills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "creativeArea")
public class CreativeArea {

    @Id
    private String id;

    @Field("creative_skill")
    private CreativeSkills creativeSkill;
    @JsonIgnore
    private String achievements;
    @JsonIgnore
    private String specialization;
    @JsonIgnore
    private User user;
}
