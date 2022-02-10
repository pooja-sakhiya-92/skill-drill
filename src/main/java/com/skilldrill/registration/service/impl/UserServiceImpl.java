package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.exceptions.InvalidRequestException;
import com.skilldrill.registration.mapper.UserMapper;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.UserService;
import com.skilldrill.registration.utilities.misc.MiniToolkit;
import com.skilldrill.registration.utilities.misc.NanoToolkit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MiniToolkit miniToolkit;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${sender.email}")
    private String SENDER_EMAIL;

    @Value("${sender.password}")
    private String SENDER_PASSWORD;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new InvalidRequestException(messageSource.getMessage("user.already.registered",
                    null, MessageSourceAlternateResource.USER_ALREADY_REGISTERED, Locale.ENGLISH));
        } else {
            user.setActive(false);
            user.setUpdateFlag(false);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setOtp(Integer.valueOf(NanoToolkit.randomOtpGenerator()));
            userRepository.save(user);
            user.setPassword(null);
            miniToolkit.mailingTool(SENDER_EMAIL, SENDER_PASSWORD, user.getEmail(),
                    messageSource.getMessage("user.register.subject", new Object[]{user.getFirstName()}, null, Locale.ENGLISH),
                    messageSource.getMessage("user.register.body", new Object[]{String.valueOf(user.getOtp())}, null, Locale.ENGLISH));
            return userMapper.toDto(user);
        }
    }
}
