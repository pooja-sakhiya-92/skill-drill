package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.UserDto;
import org.springframework.security.core.Authentication;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto googleLogin(Authentication principal);

    UserDto updateUserDetails(UserDto userDetails);

    UserDto verifyEmail(String email, String otp) throws MessagingException, IOException;

    UserDto setUpdateFlag(String password);

    UserDto updateTechnicalDetails(UserDto userDto);

    UserDto updateBasicDetails(UserDto userDto);
}
