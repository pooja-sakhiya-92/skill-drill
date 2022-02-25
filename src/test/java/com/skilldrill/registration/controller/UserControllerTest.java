package com.skilldrill.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.model.AuthRequest;
import com.skilldrill.registration.security.CustomUserDetailsService;
import com.skilldrill.registration.security.JwtUtil;
import com.skilldrill.registration.security.RestAuthenticationEntryPoint;
import com.skilldrill.registration.service.UserService;
import com.skilldrill.registration.utilities.misc.HelperFunctions;
import com.skilldrill.registration.utilities.validations.UserValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;



@WebMvcTest(value = UserController.class)
public class UserControllerTest {
    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private UserValidations userValidations;

    @MockBean
    private HelperFunctions helperFunctions;

    @MockBean
    CustomUserDetailsService detailsService;

    @MockBean
    RestAuthenticationEntryPoint entryPoint;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto= new UserDto();
        userDto.setFirstName("Sai");
        userDto.setLastName("Singh");
        userDto.setEmail("saishankarsingh@green-apex.com");
        userDto.setPhone(6264091811L);
        userDto.setPassword("Abc1234!");
        userDto.setPosition("JuniorEngineer");
        userDto.setDepartment("BACKEND");
        userDto.setRole("ROLE_GENERAL");
    }

    @Test
    public void testRegisterUser()throws Exception{
        Assertions.assertNotNull(userDto);
        //set error data in Map
        String err1="VALIDATION_USER_BODY_FAILED";
        String err2="VALIDATION_USER_PASSWORD_FAILED";
        String err3="VALIDATION_USER_PHONE_FAILED";
        Map<String, String> mappedError =new HashMap<>();
        //List<String> errors= new ArrayList<>();
        mappedError.put("Sai1234", "sai");
        mappedError.put("Rohit12", "rohit");
        mappedError.put("Amit123", "amit");
        Mockito.when(userValidations.validate(userDto)).thenReturn(mappedError);
        String jsonBody= mapper.writeValueAsString(userDto);
        mockMvc.perform(post("http://localhost:9090/api/user/registration").contentType(MediaType.APPLICATION_JSON).content(jsonBody).accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSetUpdateFlag() throws Exception {
        String password = "Abc1234!";
        Mockito.when(userService.setUpdateFlag(password)).thenReturn(userDto);
        mockMvc.perform(put("http://localhost:9090/api/user/verify/password").contentType(MediaType.APPLICATION_JSON).content(password).accept(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testUpdateTechnicalDetails() throws  Exception{
        Assertions.assertNotNull(userDto);
        //set error data in List
        String err1="VALIDATION_USER_BODY_FAILED";
        String err2="VALIDATION_USER_PASSWORD_FAILED";
        String err3="VALIDATION_USER_PHONE_FAILED";
        //Map<String, String> mappedError= new HashMap<>();
        List<String> errors= new ArrayList<>();
        errors.add("err1");
        errors.add("err2");
        errors.add("err3");
        Mockito.when(userValidations.validateTechnicalDetails(userDto.getTechnicalDetails())).thenReturn(errors);
        String jsonBody= mapper.writeValueAsString(userDto);
        mockMvc.perform(put("http://localhost:9090/api/user/update/technical-details").contentType(MediaType.APPLICATION_JSON).content(jsonBody).accept(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testAddUserDetails() throws Exception{
        Assertions.assertNotNull(userDto);
        Mockito.when(userService.updateUserDetails(userDto)).thenReturn(userDto);
        String jsonBody= mapper.writeValueAsString(userDto);
        mockMvc.perform(put("http://localhost:9090/api/user/add/user-details").contentType(MediaType.APPLICATION_JSON).content(jsonBody).accept(MediaType.APPLICATION_JSON));

    }
}
