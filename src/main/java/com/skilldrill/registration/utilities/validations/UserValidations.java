package com.skilldrill.registration.utilities.validations;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
import com.skilldrill.registration.dto.UserDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class UserValidations {
    @Autowired
    private MessageSource messageSource;

    public List<String> validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        if (isEmpty(userDto)) {
            errors.add(messageSource.getMessage("validation.user.body.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_BODY_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getPassword().matches(ValidationConstants.PASSWORD_CONDITION)) {
            errors.add(messageSource.getMessage("validation.user.password.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_PASSWORD_FAILED, Locale.ENGLISH));
        }
        if (String.valueOf(userDto.getPhone()).matches(ValidationConstants.PHONE_CONDITION)) {
            errors.add(messageSource.getMessage("validation.user.phone.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_PHONE_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getFirstName().matches(ValidationConstants.USERNAME_CONDITION)) {
            errors.add(messageSource.getMessage("validation.user.name.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_NAME_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getLastName().matches(ValidationConstants.USERNAME_CONDITION)) {
            errors.add(messageSource.getMessage("validation.user.name.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_NAME_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getPosition().matches(ValidationConstants.NOT_BLANK)) {
            errors.add(messageSource.getMessage("validation.user.type.failed",
                    null, MessageSourceAlternateResource.VALIDATION_POSITION_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getRole().matches(ValidationConstants.USERTYPE_CONDITION)) {
            errors.add(messageSource.getMessage("validation.user.type.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_TYPE_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getDepartment().matches(ValidationConstants.DEPARTMENT_CONDITION)) {
            errors.add(messageSource.getMessage("validation.user.type.failed",
                    null, MessageSourceAlternateResource.VALIDATION_DEPARTMENT_FAILED, Locale.ENGLISH));
        }
        if (!EmailValidator.getInstance().isValid(userDto.getEmail())) {
            errors.add(messageSource.getMessage("validation.user.email.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_EMAIL_FAILED, Locale.ENGLISH));
        }
        return isEmpty(errors) ? Collections.emptyList() : errors;
    }
}
