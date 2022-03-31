package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.RatingsDto;
import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
import com.skilldrill.registration.mapper.RatingsMapper;
import com.skilldrill.registration.model.Ratings;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.RatingsRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class RatingsServiceTest {

    @MockBean
    private RatingsRepository ratingsRepository;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private RatingsService ratingsService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RatingsMapper ratingsMapper;

    @Before
    public void setUp() {
        when(ratingsRepository.save(Mockito.any())).thenReturn(dummyRatings());
        when(ratingsRepository.findByUser(dummyUserDetails())).thenReturn(Optional.of(dummyRatings()));
        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(dummyUserDetails()));
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

    public static Ratings dummyRatings() {
        Ratings ratings = new Ratings("Abc123", dummyUserDetails(), 4, "demo");
        return ratings;
    }

    @Test
    public void testAddRatings() {
        RatingsDto ratingsDto = ratingsService.addRatings(ratingsMapper.toDto(dummyRatings()), "soumya.sau@green-apex.com");
        assertEquals(String.valueOf(4), ratingsDto.getStars());
        assertEquals("demo", ratingsDto.getComments());
    }

    @Test
    public void testDeleteRating() {
        Ratings ratings = dummyRatings();
        ratingsService.deleteRatings("soumya.sau@green-apex.com");
        verify(ratingsRepository, times(1)).delete(ratings);
    }

}
