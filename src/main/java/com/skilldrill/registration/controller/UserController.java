package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.service.UserService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.UserValidations;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserValidations userValidations;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        List<String> errors = userValidations.validate(userDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST, messageSource.getMessage("user.registration.failed",
                    null, MessageSourceAlternateResource.USER_REGISTRATION_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        UserDto responseBody = userService.registerUser(userDto);
        return ResponseEntity.ok().body(ResponseStructure.createResponse(HttpStatus.SC_OK, messageSource.getMessage("user.registration.successful",
                null, MessageSourceAlternateResource.USER_REGISTRATION_SUCCESSFUL, Locale.ENGLISH), responseBody));
    }
}
