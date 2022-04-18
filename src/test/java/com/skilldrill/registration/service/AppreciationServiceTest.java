package com.skilldrill.registration.service;

import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
import com.skilldrill.registration.model.Appreciation;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.AppreciationRepository;
import com.skilldrill.registration.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class AppreciationServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    AppreciationRepository appreciationRepository;


    @Autowired
    AppreciationService appreciationService;


    public static Appreciation dummyAppreciation() {
        Appreciation appreciation =
                new Appreciation
                        ("12abcd", "abcd", "2022-04-05", "akhila", dummyUserDetails());
        return appreciation;
    }

    private List<Appreciation> dummyAppreciationList() {
        Appreciation appreciation1 =
                new Appreciation
                        ("134abcd", "abcde", "2022-04-05", "akhila", dummyUserDetails());
        Appreciation appreciation2 =
                new Appreciation
                        ("34abcde", "abcdef", "2022-04-05", "akhila", dummyUserDetails());
        return Arrays.asList(appreciation1, appreciation2);

    }


    public static User dummyUserDetails() {
        User user = new User();
        user.setEmail("akhila.boda@green-apex.com");
        user.setFirstName("Akhila");
        user.setLastName("Boda");
        user.setPassword("Akhila@299");
        user.setDepartment(Department.BACKEND);
        user.setPhone(9855441209L);
        user.setRole(Roles.ROLE_GENERAL);
        return user;
    }

    @Before
    public void setUp() {
        when(appreciationRepository.save(Mockito.any())).thenReturn(dummyAppreciation());
        when(appreciationRepository.findByPraise(Mockito.any())).thenReturn(Optional.of(dummyAppreciation()));
        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(dummyUserDetails()));
        when(appreciationRepository.findByUser(Mockito.any())).thenReturn(dummyAppreciationList());

    }


    @Test
    public void testDeleteAppreciation() {
        Appreciation appreciation = dummyAppreciation();
        appreciationRepository.delete(appreciation);
        verify(appreciationRepository, times(1)).delete(appreciation);
    }

    @Test
    public void testGetAllAppreciationsOfUser() {
        List<Appreciation> list = new ArrayList<>();
        list.add(dummyAppreciation());
        when(appreciationRepository.findAll()).thenReturn(list);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }
}
