package com.skilldrill.registration.service;


import com.skilldrill.registration.enums.CreativeSkills;
import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
import com.skilldrill.registration.mapper.CreativeAreaMapper;
import com.skilldrill.registration.model.CreativeArea;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.CreativeAreaRepository;
import com.skilldrill.registration.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class CreativeAreaServiceTest {

    @MockBean
    private CreativeAreaRepository creativeAreaRepository;
    @Autowired
    private CreativeAreaService creativeAreaService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private CreativeAreaMapper creativeAreaMapper;
    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() {
        when(creativeAreaRepository.save(Mockito.any())).thenReturn(dummyCreativeArea());
        when(creativeAreaRepository.findByCreativeSkill(Mockito.any())).thenReturn(Optional.of(dummyCreativeArea()));
        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(dummyUserDetails()));

    }

    public static CreativeArea dummyCreativeArea() {
        CreativeArea creativeArea =
                new CreativeArea
                        ("12abcd", CreativeSkills.DANCING,"got state level gold medal","classicall",dummyUserDetails());
        return creativeArea;
    }

    public static User dummyUserDetails() {
        User user = new User();
        user.setEmail("akhila.boda@green-apex.com");
        user.setFirstName("Akhila");
        user.setLastName("Boda");
        user.setPassword("Akhila@11");
        user.setDepartment(Department.BACKEND);
        user.setPhone(9112532233L);
        user.setRole(Roles.ROLE_GENERAL);
        return user;
    }

    @Test
    public void testAddAndUpdateCreativeArea() {
        CreativeArea creativeArea = creativeAreaService.addCreativity(creativeAreaMapper.toDto(dummyCreativeArea()),"akhila.boda@green-apex.com");
        assertEquals(CreativeSkills.DANCING, creativeArea.getCreativeSkill());
        assertEquals("akhila.boda@green-apex.com", creativeArea.getUser().getEmail());
    }

    @Test
    public void testDeleteCreativeArea() {
        CreativeArea creativeArea = dummyCreativeArea();
        creativeAreaService.deleteCreativeArea("DANCING","akhila.boda@greenapex.com");
        verify(creativeAreaRepository, times(1)).delete(creativeArea);
    }

    @Test
    public void testGetAllCreativeAreasOfUser() {
        List<CreativeArea> creativeAreaList = creativeAreaService.findAllCreativeAreas("akhila.boda@green-apex.com");
        creativeAreaList.add(dummyCreativeArea());
        when(creativeAreaRepository.findAll()).thenReturn(creativeAreaList);
        assertFalse(creativeAreaList.isEmpty());
        assertEquals(1,creativeAreaList.size());

    }
}

