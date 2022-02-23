package com.skilldrill.registration.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.dto.UserInfoDto;
import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HelperFunctions helperFunctions;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MiniToolkit miniToolkit;

    @Autowired
    private NanoToolkit nanoToolkit;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${sender.email}")
    private String SENDER_EMAIL;

    @Value("${sender.password}")
    private String SENDER_PASSWORD;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        boolean userExists = userRepository.existsByEmail(user.getEmail());
        if (userExists) {
            throw new InvalidRequestException(messageSource.getMessage("user.already.registered",
                    null, MessageSourceAlternateResource.USER_ALREADY_REGISTERED, Locale.ENGLISH));
        } else {
            user.setActive(false);
            user.setUpdateFlag(false);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            user.setPassword(null);
            miniToolkit.sendEmailVerificationOTP(user.getEmail());
            return userMapper.toDto(user);
        }
    }

    @Override
    public UserDto googleLogin(Authentication principal) {
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        Map<?, ?> response = objectMapper.convertValue(principal.getPrincipal(), Map.class);
        Map<?, ?> attribute = objectMapper.convertValue(response.get("attributes"), Map.class);
        String generatedPassword = attribute.get("given_name") + "G00gle$" + attribute.get("family_name");
        user.setFirstName((String) attribute.get("given_name"));
        user.setLastName((String) attribute.get("family_name"));
        user.setEmail((String) attribute.get("email"));
        user.setPassword(bCryptPasswordEncoder.encode(generatedPassword));
        user.setRole(Roles.ROLE_GENERAL);
        user.setActive(true);
        user.setUpdateFlag(false);
        userRepository.save(user);
        return userMapper.toDto(user);
    }


    @Override
    public UserDto updateUserDetails(UserDto userDetails) {
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        user.setPosition(userDetails.getPosition());
        user.setDepartment(Department.valueOf(userDetails.getDepartment()));
        user.setPhone(userDetails.getPhone());
        user.setUpdateFlag(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        user.setPassword(null);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto verifyEmail(String email, String otp) {
        User userFromDb = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        userFromDb.setActive(miniToolkit.verifyEmailOTP(userFromDb.getEmail(), otp));
        userRepository.save(userFromDb);
        try {
            miniToolkit.sendAcknowledgement(new UserInfoDto(userFromDb.getFirstName(),
                    helperFunctions.getAccountStatus(userFromDb), userFromDb.getEmail()));
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return userMapper.toDto(userFromDb);
    }

    @Override
    public UserDto setUpdateFlag(String password) {
        UserDetails authentication = nanoToolkit.getCurrentUserDetails();
        User userFromDb = userRepository.findByEmail(authentication.getUsername())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        if (!userFromDb.getUpdateFlag()) {
            if (helperFunctions.checkPassword(password, userFromDb.getPassword())) {
                userFromDb.setUpdateFlag(true);
                userRepository.save(userFromDb);
            }
            userFromDb.setPassword(null);
            return userMapper.toDto(userFromDb);
        }
        return null;
    }

    @Override
    public UserDto updateTechnicalDetails(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        UserDetails authentication = nanoToolkit.getCurrentUserDetails();
        User userFromDb = userRepository.findByEmail(authentication.getUsername())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        if (Boolean.TRUE.equals(userFromDb.getUpdateFlag())) {
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
        UserDetails authentication = nanoToolkit.getCurrentUserDetails();
        User userFromDb = userRepository.findByEmail(authentication.getUsername())
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.not.found",
                        null, MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        if (Boolean.TRUE.equals(userFromDb.getUpdateFlag())) {
            helperFunctions.updateBasicFields(userFromDb, user);
            userFromDb.setUpdateFlag(false);
            userRepository.save(userFromDb);
        }
        userFromDb.setPassword(null);
        return userMapper.toDto(userFromDb);
    }

    @Override
    public Boolean checkIfUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
