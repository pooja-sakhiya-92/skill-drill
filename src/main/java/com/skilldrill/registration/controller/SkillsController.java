package com.skilldrill.registration.controller;

import com.skilldrill.registration.dto.SkillsDto;
import com.skilldrill.registration.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/skills")
public class SkillsController {
    private final SkillsService skillsService;

    private final MessageSource messageSource;

    @Autowired
    public SkillsController(SkillsService skillsService, MessageSource messageSource) {
        this.skillsService = skillsService;
        this.messageSource = messageSource;
    }
    @PostMapping("add")
    public ResponseEntity<?> addSkills(@RequestBody @Valid SkillsDto skillsDto) {
        return ResponseEntity.ok(skillsService.addSkills(skillsDto));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateSkills(@RequestBody @Valid SkillsDto skillsDto) {
        return ResponseEntity.ok(skillsService.updateSkills(skillsDto));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteSkills(@RequestParam String skillName) {
        skillsService.deleteSkill(skillName);
        return ResponseEntity.ok(skillName+" deleted successfully!");
    }

    @GetMapping("get")
    public ResponseEntity<?> getSkillInfoByName(@RequestParam String skillName) {
        return ResponseEntity.ok(skillsService.getSkillInfo(skillName));
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getSkillsFromCategory(@RequestParam String categoryName) {
        return ResponseEntity.ok(skillsService.getAllSkillInfoFromCategory(categoryName));
    }
}
