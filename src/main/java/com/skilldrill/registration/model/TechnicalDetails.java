package com.skilldrill.registration.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class TechnicalDetails {

    @Field(value = "linkedin_link")
    private String linkedinLink;

    @Field(value = "resume_link")
    private String resumeLink;

    private List<String> tags;

    private String education;

    @Field(value = "interest_area")
    private String interestArea;

}
