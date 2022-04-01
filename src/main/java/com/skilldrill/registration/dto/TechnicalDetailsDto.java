package com.skilldrill.registration.dto;

import lombok.Data;

import java.util.List;

@Data
public class TechnicalDetailsDto {

    private String linkedinLink;

    private String resumeLink;

    private List<String> tags;

    private String education;

    private String interestArea;
}
