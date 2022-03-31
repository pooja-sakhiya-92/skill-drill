package com.skilldrill.registration.service;

import com.skilldrill.registration.enums.BadgeType;
import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Language;
import com.skilldrill.registration.enums.Roles;
import com.skilldrill.registration.mapper.BadgeMapper;
import com.skilldrill.registration.model.Badges;
import com.skilldrill.registration.model.Ratings;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.BadgeRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class BadgesServiceTest {

    @MockBean
    private BadgeRepository badgeRepository;
    @Autowired
    private BadgeMapper badgeMapper;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RatingsRepository ratingsRepository;
    @Autowired
    private BadgeService badgeService;
    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() {

        when(ratingsRepository.findByUser(Mockito.any())).thenReturn(Optional.of((dummyRatings())));
        when(badgeRepository.save(Mockito.any())).thenReturn(dummyBadges());
        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(dummyUserDetails()));
        when(badgeRepository.findByUser(Mockito.any())).thenReturn(dummyBadgeList());
    }

    public static Badges dummyBadges() {
        Badges badges = new Badges
                        ("123Avc", BadgeType.NEW,"V1.0",dummyUserDetails(),
                                "dummy-description", Language.ENGLISH,null,"Mon Mar 14 10:27:51 IST 2022");
        return badges;
    }

    public static List<Badges> dummyBadgeList() {
        Badges firstBadge = new Badges
                ("123Avc", BadgeType.NEW,"V1.0",dummyUserDetails(),
                        "dummy-description", Language.ENGLISH,null,"Mon Mar 14 10:27:51 IST 2022");
        Badges secondBadge = new Badges
                ("123Abc", BadgeType.MOST_RATED,"V1.0",dummyUserDetails(),
                        "dummy-description", Language.ENGLISH,null,"Mon Mar 15 10:27:51 IST 2022");

        return Arrays.asList(firstBadge,secondBadge);
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
        Ratings ratings = new Ratings("Abc123",dummyUserDetails(),4,"demo");
        return ratings;
    }

    @Test
    public void testAddBadges() {
        Badges badges = badgeService.addBadges(badgeMapper.toDto(dummyBadges()),"soumya.sau@green-apex.com");
        assertEquals(BadgeType.NEW,badges.getBadge());
        assertNotEquals(BadgeType.MULTI_TALENTED,badges.getBadge());
        assertEquals(Language.ENGLISH,badges.getLanguage());
    }

    @Test
    public void testDeleteBadges() {
        Badges badges = dummyBadges();
        badgeService.deleteBadges("soumya.sau@green-apex.com",String.valueOf(BadgeType.NEW));
        verify(badgeRepository,times(1)).delete(badges);
    }

}
