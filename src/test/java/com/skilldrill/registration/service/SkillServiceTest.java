package com.skilldrill.registration.service;

import com.skilldrill.registration.mapper.SkillsMapper;
import com.skilldrill.registration.model.Categories;
import com.skilldrill.registration.model.Skills;
import com.skilldrill.registration.repository.CategoryRepository;
import com.skilldrill.registration.repository.SkillsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class SkillServiceTest {
    @MockBean
    private SkillsRepository skillsRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private SkillsService skillsService;
    @Autowired
    private SkillsMapper skillsMapper;

    @Before
    public void setUp() {
        when(skillsRepository.save(Mockito.any())).thenReturn(dummySkills());
        when(categoryRepository.findByCategoryName(Mockito.any())).thenReturn(Optional.of(dummyCategory()));
        when(categoryRepository.save(Mockito.any())).thenReturn(dummyCategory());
    }

    public static Categories dummyCategory() {
        return new Categories("abc123", Arrays.asList("xyz", "abc"),
                "JAVA", null, "description...");
    }

    public static Skills dummySkills() {
        return new Skills("123ABC",dummyCategory(),"OOPs","description...");
    }

    @Test
    public void testAddSkill() {
        Skills skills = skillsService.addSkills(skillsMapper.toDto(dummySkills()));
        assertEquals(skills.getSkillName(),"OOPs");
        assertEquals(skills.getCategories(),dummyCategory());
    }

    @Test
    public void testDeleteSkill() {
        Skills skills = dummySkills();
        skillsRepository.delete(skills);
        verify(skillsRepository,times(1)).delete(skills);
    }

    @Test
    public void testGetAllSkillInfoFromCategory() {
        List<Skills> skillsList = skillsService.getAllSkillInfoFromCategory("JAVA");
        skillsList.add(dummySkills());
        when(skillsRepository.findByCategories(dummyCategory())).thenReturn(skillsList);
        assertEquals(skillsList.size(),1);
        assertFalse(skillsList.isEmpty());
    }
}
