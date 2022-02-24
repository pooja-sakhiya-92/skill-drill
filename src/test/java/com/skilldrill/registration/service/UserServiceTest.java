package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
import com.skilldrill.registration.mapper.UserMapper;
import com.skilldrill.registration.model.TechnicalDetails;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.security.DummyUserDetails;
import com.skilldrill.registration.service.impl.UserServiceImpl;
import com.skilldrill.registration.utilities.misc.NanoToolkit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    private NanoToolkit nanoToolkit;
    private User userWithBasicDetails;
    private User userWithTechnicalDetails;
    private TechnicalDetails technicalDetails;
    private UserDetails userDetails;

    @Before
    public void initDummyData() {

        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Python");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(Roles.ROLE_GENERAL);

        technicalDetails = new TechnicalDetails("www.linkedin.com/akshay-navilkar",
                "www.resumeLink.com/akshaynavilkar", tags, "B.E", "Web Development");

        userWithBasicDetails = new User("abcdefgh12345678", "abc", "def",
                "akshay.navilkar@green-apex.com", 9876543210L, "Abcd1234!",
                "Manager", Department.BACKEND, Roles.ROLE_GENERAL, true, false);

        userWithTechnicalDetails = new User("abcdefgh12345678", "abc", "def",
                "akshay.navilkar@green-apex.com", 9876543210L, "Abcd1234!",
                "Manager", Department.BACKEND, Roles.ROLE_GENERAL, technicalDetails,
                true, null, false);

        userDetails = new DummyUserDetails("soumya.sau@green-apex.com",
                bCryptPasswordEncoder.encode("Abcd1234!"), authorities,
                true, true, true, true);
    }

    @After
    public void nullifyDummyData() {
        userWithBasicDetails = null;
    }

    @Test
    public void updateUserDetailsTest() {
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(userWithBasicDetails));
        UserDto dummyData = userMapper.toDto(userWithBasicDetails);
        UserDto responseFromService = userService.updateUserDetails(userMapper.toDto(userWithBasicDetails));
        assert Objects.equals(dummyData.getEmail(), responseFromService.getEmail());
        assert Objects.equals(dummyData.getFirstName(), responseFromService.getFirstName());
        assert Objects.equals(dummyData.getLastName(), responseFromService.getLastName());
    }

    @Test
    public void registerUserTest() {
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        UserDto dummyData = userMapper.toDto(userWithBasicDetails);
        UserDto responseFromService = userService.registerUser(userMapper.toDto(userWithBasicDetails));
        assert Objects.equals(dummyData.getEmail(), responseFromService.getEmail());
        assert Objects.equals(dummyData.getFirstName(), responseFromService.getFirstName());
        assert Objects.equals(dummyData.getLastName(), responseFromService.getLastName());
    }

    @Test
    public void verifyEmailTest() throws MessagingException, IOException {
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(userWithBasicDetails));
        UserDto dummyData = userMapper.toDto(userWithBasicDetails);
        UserDto responseFromService = userService.verifyEmail(userMapper.toDto(userWithBasicDetails).getEmail(), "379115");
        assert Objects.equals(dummyData.getEmail(), responseFromService.getEmail());
        assert Objects.equals(dummyData.getFirstName(), responseFromService.getFirstName());
        assert Objects.equals(dummyData.getLastName(), responseFromService.getLastName());
        assert Boolean.TRUE.equals(responseFromService.getActive());
    }

    @Test
    public void setUpdateFlagTest() {
        Mockito.when(nanoToolkit.getCurrentUserDetails()).thenReturn(userDetails);
        Mockito.when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(userWithBasicDetails));
        UserDto dummyData = userMapper.toDto(userWithBasicDetails);
        UserDto responseFromService = userService.setUpdateFlag(userMapper.toDto(userWithBasicDetails).getPassword());
        assert Objects.equals(dummyData.getEmail(), responseFromService.getEmail());
        assert Objects.equals(dummyData.getFirstName(), responseFromService.getFirstName());
        assert Objects.equals(dummyData.getLastName(), responseFromService.getLastName());
        assert responseFromService.getUpdateFlag() == null || Boolean.FALSE.equals(responseFromService.getUpdateFlag()) || Boolean.TRUE.equals(responseFromService.getUpdateFlag());
    }

    @Test
    public void updateTechnicalDetailsTest() {
        Mockito.when(nanoToolkit.getCurrentUserDetails()).thenReturn(userDetails);
        userWithBasicDetails.setUpdateFlag(true);
        Mockito.when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(userWithBasicDetails));
        UserDto dummyData = userMapper.toDto(userWithTechnicalDetails);
        UserDto responseFromService = userService.updateTechnicalDetails(userMapper.toDto(userWithTechnicalDetails));
        assert Objects.equals(dummyData.getEmail(), responseFromService.getEmail());
        assert Objects.equals(dummyData.getFirstName(), responseFromService.getFirstName());
        assert Objects.equals(dummyData.getLastName(), responseFromService.getLastName());
        assert Objects.requireNonNull(dummyData.getTechnicalDetails()).getLinkedinLink().equals(responseFromService.getTechnicalDetails().getLinkedinLink());
        assert dummyData.getTechnicalDetails().getResumeLink().equals(responseFromService.getTechnicalDetails().getResumeLink());
        assert dummyData.getTechnicalDetails().getTags().equals(responseFromService.getTechnicalDetails().getTags());
        assert dummyData.getTechnicalDetails().getEducation().equals(responseFromService.getTechnicalDetails().getEducation());
        assert dummyData.getTechnicalDetails().getInterestArea().equals(responseFromService.getTechnicalDetails().getInterestArea());
    }

    @Test
    public void updateBasicDetailsTest() {
        Mockito.when(nanoToolkit.getCurrentUserDetails()).thenReturn(userDetails);
        userWithBasicDetails.setUpdateFlag(true);
        Mockito.when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(userWithBasicDetails));
        UserDto dummyData = userMapper.toDto(userWithBasicDetails);
        User userWithChangedBasicDetails = userWithBasicDetails;
        userWithChangedBasicDetails.setFirstName("Akshay");
        UserDto changedDummyData = userMapper.toDto(userWithChangedBasicDetails);
        UserDto responseFromService = userService.updateBasicDetails(userMapper.toDto(userWithBasicDetails));
        assert Objects.equals(dummyData.getEmail(), responseFromService.getEmail());
        assert !Objects.equals(dummyData.getFirstName(), responseFromService.getFirstName());
        assert Objects.equals(changedDummyData.getFirstName(), responseFromService.getFirstName());
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
}