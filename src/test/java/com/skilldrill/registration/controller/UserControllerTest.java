//package com.skilldrill.registration.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.skilldrill.registration.dto.UserDto;
//import com.skilldrill.registration.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(JUnit4.class)
//class UserControllerTest {
//
//    @InjectMocks
//    UserController userController;
//
//    @Mock
//    UserService userService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    UserDto userDto = new UserDto();
//
//    @BeforeEach
//    void setUp() {
//        userDto.setFirstName("Akshay");
//        userDto.setLastName("Navilkar");
//        userDto.setEmail("akshay.navilkar@green-apex.com");
//        userDto.setPhone(9876543210L);
//        userDto.setPassword("Abcd123!");
//        userDto.setPosition("JuniorEngineer");
//        userDto.setDepartment("BACKEND");
//        userDto.setRole("ROLE_GENERAL");
//    }
//
//    @Test
//    void generateToken() {
//
//    }
//
//    @Test
//    void registerUser() throws Exception {
////        MockHttpServletRequest request = new MockHttpServletRequest();
////        when(userService.registerUser(any(UserDto.class))).getMock();
////        String url = "http://localhost:8080/api/user/registration";
////        String verifyPassword = "http://localhost:8080/api/user/verify-email?email="+userDto.getEmail();
////        this.perform(post(url))
////                .andExpect(forwardedUrl(verifyPassword));
//    }
//
//    @Test
//    void verifyEmail() {
//    }
//
//    @Test
//    void setUpdateFlag() {
//    }
//
//    @Test
//    void updateTechnicalDetails() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        when(userService.registerUser(any(UserDto.class))).thenReturn(userDto);
//        ResponseEntity<?> responseEntity = userController.registerUser(userDto);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }
//
//    @Test
//    void updateBasicDetails() {
//    }
//}