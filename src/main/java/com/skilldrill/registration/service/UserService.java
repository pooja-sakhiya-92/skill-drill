package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;


public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto user(Authentication principal);

    UserDto updateUserDetails(UserDto userDetails);

    UserDto verifyEmail(String email, String otp) throws MessagingException, IOException;

    UserDto setUpdateFlag();

    UserDto updateTechnicalDetails(UserDto userDto);

    UserDto updateBasicDetails(UserDto userDto);

}
