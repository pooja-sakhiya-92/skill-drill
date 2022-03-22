package com.skilldrill.registration.dto;

import com.skilldrill.registration.enums.CreativeSkills;
import com.skilldrill.registration.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeAreaDto {

    private String id;
    private String creativeSkill;
    private String achievements;
    private String specialization;
    private User user;

    public CreativeAreaDto(String creativeSkill, String achievements, String specialization) {
        this.creativeSkill = creativeSkill;
        this.achievements = achievements;
        this.specialization = specialization;
    }
}
