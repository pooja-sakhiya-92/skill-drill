package com.skilldrill.registration.dto;

import com.skilldrill.registration.enums.CreativeSkills;
import com.skilldrill.registration.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeAreaDto {

    private String id;
    @NonNull
    private String creativeSkill;
    private String achievements;
    private String specialization;

    @Nullable
    private User user;

    public CreativeAreaDto(String creativeSkill, String achievements, String specialization) {
        this.creativeSkill = creativeSkill;
        this.achievements = achievements;
        this.specialization = specialization;
    }
}
