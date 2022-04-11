package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.SkillsDto;
import com.skilldrill.registration.model.Skills;

import java.util.List;

public interface SkillsService {
    Skills addSkills(SkillsDto skillsDto);

    SkillsDto updateSkills(SkillsDto skillsDto);

    void deleteSkill(String skillName);

    SkillsDto getSkillInfo(String skillName);

    List<Skills> getAllSkillInfoFromCategory(String categoryName);
}
