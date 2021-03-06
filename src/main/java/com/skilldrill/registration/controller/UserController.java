package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.model.AuthRequest;
import com.skilldrill.registration.security.JwtUtil;
import com.skilldrill.registration.service.UserService;
import com.skilldrill.registration.utilities.misc.HelperFunctions;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.UserValidations;
import com.twilio.exception.ApiException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserValidations userValidations;

    @Autowired
    private HelperFunctions helperFunctions;

    @PostMapping("login")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        if (!helperFunctions.checkIfUserExists(authRequest.getUserName())) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("user.not.found", null, MessageSourceAlternateResource.USER_NOT_FOUND,
                            Locale.ENGLISH)));
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                    authRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("user.login.failed",
                            null, MessageSourceAlternateResource.USER_LOGIN_FAILED, Locale.ENGLISH)));
        }
        String response = JwtUtil.generateToken(authRequest.getUserName());
        return ResponseEntity.ok().body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("user.login.successful",
                        null, MessageSourceAlternateResource.USER_LOGIN_SUCCESSFUL, Locale.ENGLISH), response));
    }

    @PostMapping("registration")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        Map<String, String> errors = userValidations.validate(userDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("user.registration.failed",
                            null, MessageSourceAlternateResource.USER_REGISTRATION_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        UserDto responseBody = userService.registerUser(userDto);
        return ResponseEntity.status(201).body(ResponseStructure.createResponse(HttpStatus.SC_CREATED,
                messageSource.getMessage("user.registration.successful",
                        null, MessageSourceAlternateResource.USER_LOGIN_SUCCESSFUL, Locale.ENGLISH), responseBody));
    }

    @PutMapping("verify/email")
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String otp) throws MessagingException, IOException {
        if (!helperFunctions.checkIfUserExists(email)) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("user.not.found", null,
                            MessageSourceAlternateResource.USER_NOT_FOUND, Locale.ENGLISH)));
        }
        UserDto responseBody;

        responseBody = userService.verifyEmail(email, otp);

        if (responseBody == null)
            return ResponseEntity.status(406).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_ACCEPTABLE,
                    messageSource.getMessage("invalid.email.otp", null,
                            MessageSourceAlternateResource.INVALID_EMAIL_OTP, Locale.ENGLISH)));
        return ResponseEntity.ok().body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("email.verification.successful", null,
                        MessageSourceAlternateResource.EMAIL_VERIFICATION_SUCCESSFUL, Locale.ENGLISH), responseBody));
    }

    @PutMapping("verify/password")
    public ResponseEntity<?> setUpdateFlag(@RequestParam String password) {
        UserDto responseBody = userService.setUpdateFlag(password);
        if (responseBody == null) {
            return ResponseEntity.badRequest().body(ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("updateflag.already.set", null, MessageSourceAlternateResource.UPDATEFLAG_ALREADY_SET, Locale.ENGLISH)));
        }
        if (!HelperFunctions.checkUpdateFlag(responseBody)) {
            return ResponseEntity.status(401).body(ResponseStructure.createResponse(HttpStatus.SC_UNAUTHORIZED,
                    messageSource.getMessage("invalid.user.password", null, MessageSourceAlternateResource.INVALID_USER_PASSWORD, Locale.ENGLISH)));
        }
        return ResponseEntity.ok().body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("password.verified.successfully",
                        null, MessageSourceAlternateResource.PASSWORD_VERIFICATION_SUCCESSFUL, Locale.ENGLISH), responseBody));
    }

    @PutMapping("update/technical-details")
    public ResponseEntity<?> updateTechnicalDetails(@RequestBody UserDto userDto) {
        List<String> errors = userValidations.validateTechnicalDetails(userDto.getTechnicalDetails());
        Map<String, String> mappedError = new HashMap<>();
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("technical-details_update_failed",
                            null, MessageSourceAlternateResource.TECHNICAL_DETAILS_UPDATE_FAILED, Locale.ENGLISH));
            mappedError.put("technicalDetails", errors.toString());
            response.setErrors(mappedError);
            return ResponseEntity.badRequest().body(response);
        }
        UserDto responseBody = userService.updateTechnicalDetails(userDto);
        return ResponseEntity.ok().body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("technical-details_update_successful",
                        null, MessageSourceAlternateResource.TECHNICAL_DETAILS_UPDATE_SUCCESSFUL, Locale.ENGLISH), responseBody));
    }

    @GetMapping("google-login")
    public ResponseEntity<?> loginUsingGoogle(Authentication principal) {
        return ResponseEntity.ok().body(userService.googleLogin(principal));
    }

    @PutMapping("add/user-details")
    public ResponseEntity<?> addUserDetails(@RequestBody UserDto userDto) {
        UserDto responseBody = userService.updateUserDetails(userDto);
        return ResponseEntity.ok().body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("user-details.added.successful",
                        null, MessageSourceAlternateResource.USER_DETAILS_ADDED_SUCCESSFUL, Locale.ENGLISH), responseBody));
    }


    @PutMapping("update/basic-details")
    public ResponseEntity<?> updateBasicDetails(@RequestBody UserDto userDto) {
        Map<String, String> errors = userValidations.validate(userDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("basic-details_update_failed",
                            null, MessageSourceAlternateResource.BASIC_DETAILS_UPDATE_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        UserDto responseBody = userService.updateBasicDetails(userDto);
        return ResponseEntity.ok().body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("basic-details_update_successful",
                        null, MessageSourceAlternateResource.BASIC_DETAILS_UPDATE_SUCCESSFUL, Locale.ENGLISH), responseBody));
    }

}
