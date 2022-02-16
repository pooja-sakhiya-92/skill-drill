package com.skilldrill.registration.service.impl;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.dto.UserInfoDto;
import com.skilldrill.registration.exceptions.InvalidRequestException;
import com.skilldrill.registration.exceptions.NotFoundException;
import com.skilldrill.registration.mapper.UserMapper;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.repository.UserRepository;
import com.skilldrill.registration.service.UserService;
import com.skilldrill.registration.utilities.misc.HelperFunctions;
import com.skilldrill.registration.utilities.misc.MiniToolkit;
import com.skilldrill.registration.utilities.misc.NanoToolkit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
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
            miniToolkit.sendEmailVerificationOTP(user.getEmail());
            return userMapper.toDto(user);
        }
    }

    @Override
    public UserDto verifyEmail(String email, String otp){
        User userFromDb = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        userFromDb.setActive(miniToolkit.verifyEmailOTP(userFromDb.getEmail(), otp));
        userRepository.save(userFromDb);
        try {
            miniToolkit.sendAcknowledgement(new UserInfoDto(userFromDb.getFirstName(),
                    HelperFunctions.getAccountStatus(userFromDb), userFromDb.getEmail()));
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userMapper.toDto(userFromDb);
    }

    @Override
    public UserDto setUpdateFlag() {
        UserDetails authentication = NanoToolkit.getCurrentUserDetails();
        User userFromDb = userRepository.findByEmail(authentication.getUsername())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        userFromDb.setUpdateFlag(true);
        userRepository.save(userFromDb);
        userFromDb.setPassword(null);
        return userMapper.toDto(userFromDb);
    }

    @Override
    public UserDto updateTechnicalDetails(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        UserDetails authentication = NanoToolkit.getCurrentUserDetails();
        User userFromDb = userRepository.findByEmail(authentication.getUsername())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        if (userFromDb.getUpdateFlag()) {
            userFromDb.setTechnicalDetails(user.getTechnicalDetails());
            userFromDb.setUpdateFlag(false);
            userRepository.save(userFromDb);
        }
        userFromDb.setPassword(null);
        return userMapper.toDto(userFromDb);
    }

    @Override
    public UserDto updateBasicDetails(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        UserDetails authentication = NanoToolkit.getCurrentUserDetails();
        User userFromDb = userRepository.findByEmail(authentication.getUsername())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        if (userFromDb.getUpdateFlag()) {
            HelperFunctions.updateBasicFields(userFromDb, user);
            userFromDb.setUpdateFlag(false);
            userRepository.save(userFromDb);
        }
        userFromDb.setPassword(null);
        return userMapper.toDto(userFromDb);
    }
}
