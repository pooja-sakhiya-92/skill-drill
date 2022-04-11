package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.dto.SkillsDto;
import com.skilldrill.registration.exceptions.InvalidRequestException;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.SkillsMapper;
import com.skilldrill.registration.model.Categories;
import com.skilldrill.registration.model.Skills;
import com.skilldrill.registration.repository.CategoryRepository;
import com.skilldrill.registration.repository.SkillsRepository;
import com.skilldrill.registration.service.SkillsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillsServiceImpl implements SkillsService {

    private final SkillsRepository skillsRepository;
    private final SkillsMapper skillsMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SkillsServiceImpl(SkillsRepository skillsRepository, SkillsMapper skillsMapper, CategoryRepository categoryRepository) {
        this.skillsRepository = skillsRepository;
        this.skillsMapper = skillsMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Skills addSkills(SkillsDto skillsDto) {
        Skills skills = skillsMapper.toSkill(skillsDto);
        Optional<Categories> categories = categoryRepository.findByCategoryName(skillsDto.getCategories().getCategoryName());
        if(!skillsRepository.findBySkillName(skillsDto.getSkillName()).isPresent() && categories.isPresent()) {
            skills.setCategories(categories.get());
            skillsRepository.save(skills);
        } else if(!skillsRepository.findBySkillName(skillsDto.getSkillName()).isPresent() && !categories.isPresent()) {
            categoryRepository.save(skills.getCategories());
            skillsRepository.save(skills);
        }
        else {
            throw new InvalidRequestException("skill already exist");
        }
        return skills;
    }

    @Override
    public SkillsDto updateSkills(SkillsDto skillsDto) {
        Skills skills = skillsMapper.toSkill(skillsDto);
        Optional<Skills> skill = skillsRepository.findBySkillName(skillsDto.getSkillName());
        if(skill.isPresent()) {
            Skills skillFromDB = skillsRepository.findById(new ObjectId(skill.get().getId()))
                    .orElseThrow(() -> new NotFoundException("Skill not available"));
            skillFromDB.setDescription(skillsDto.getDescription());
            skillFromDB.setCategories(skillsDto.getCategories());
            skillsRepository.save(skillFromDB);
        } else {
            throw new NotFoundException("skill not found");
        }
        return skillsMapper.toDto(skills);
    }

    @Override
    public void deleteSkill(String skillName) {
        Skills skills = skillsRepository.findBySkillName(skillName)
                .orElseThrow(() -> new NotFoundException("Skill not found"));
        skillsRepository.delete(skills);
    }

    @Override
    public SkillsDto getSkillInfo(String skillName) {
        Skills skills = skillsRepository.findBySkillName(skillName)
                .orElseThrow(() -> new NotFoundException("Skill not found"));
        return skillsMapper.toDto(skills);
    }

    @Override
    public List<Skills> getAllSkillInfoFromCategory(String categoryName) {
        List<Skills> allSkills = skillsRepository.findAll();
        return allSkills.stream()
                .filter(skill -> skill.getCategories().getCategoryName().equals(categoryName))
                .collect(Collectors.toList());
    }
}
