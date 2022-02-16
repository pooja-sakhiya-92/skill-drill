package com.skilldrill.registration.service;

import com.skilldrill.registration.dto.UserDto;

import javax.mail.MessagingException;
import java.io.IOException;


public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto verifyEmail(String email, String otp) throws MessagingException, IOException;

    UserDto setUpdateFlag();

    UserDto updateTechnicalDetails(UserDto userDto);

    UserDto updateBasicDetails(UserDto userDto);

}
