package com.skilldrill.registration.service;

import com.skilldrill.registration.enums.ContributionType;
import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
import com.skilldrill.registration.mapper.ContributionMapper;
import com.skilldrill.registration.model.Contribution;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.ContributionRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class ContributionServiceTest {

    @MockBean
    private ContributionRepository contributionRepository;
    @Autowired
    private ContributionService contributionService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ContributionMapper contributionMapper;
    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() {
        when(contributionRepository.save(Mockito.any())).thenReturn(dummyContribution());
        when(contributionRepository.findByContributionName(Mockito.any())).thenReturn(Optional.of(dummyContribution()));
        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(dummyUserDetails()));

    }

    public static Contribution dummyContribution() {
        Contribution contribution =
                new Contribution
                        ("123ABC", dummyUserDetails(), ContributionType.IMAGE, "2022-03-07", "Spring MVC doc", null, null, "");
        return contribution;
    }

    public static User dummyUserDetails() {
        User user = new User();
        user.setEmail("soumya.sau@green-apex.com");
        user.setFirstName("Soumya");
        user.setLastName("Sau");
        user.setPassword("Abcd1234!");
        user.setDepartment(Department.BACKEND);
        user.setPhone(7059323126L);
        user.setRole(Roles.ROLE_GENERAL);
        return user;
    }

    @Test
    public void testAddAndUpdateContribution() {
        Contribution contribution = contributionService.addContribution(contributionMapper.toDto(dummyContribution()), "soumya.sau@green-apex.com");
        assertEquals(ContributionType.IMAGE, contribution.getTypeOfContribution());
        assertEquals("soumya.sau@green-apex.com", contribution.getUser().getEmail());
    }

    @Test
    public void testArchiveContribution() {
        Contribution contribution = dummyContribution();
        contributionService.archiveContribution("Spring MVC doc");
        verify(contributionRepository, times(1)).delete(contribution);
    }

    @Test
    public void testGetAllContributionOfUser() {
        List<Contribution> contributionList = contributionService.getAllContributionsOfUser("soumya.sau@green-apex.com");
        contributionList.add(dummyContribution());
        when(contributionRepository.findAll()).thenReturn(contributionList);
        assertFalse(contributionList.isEmpty());
        assertEquals(1,contributionList.size());

    }
}
