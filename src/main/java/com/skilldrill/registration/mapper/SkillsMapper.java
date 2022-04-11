package com.skilldrill.registration.mapper;

import com.skilldrill.registration.dto.SkillsDto;
import com.skilldrill.registration.model.Skills;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillsMapper {
    SkillsDto toDto(Skills skills);

    Skills toSkill(SkillsDto skillsDto);
}
