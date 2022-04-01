package com.skilldrill.registration.utilities.validations;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
import com.skilldrill.registration.dto.TechnicalDetailsDto;
import com.skilldrill.registration.dto.UserDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class UserValidations {
    @Autowired
    private MessageSource messageSource;

    public Map<String,String > validate(UserDto userDto) {
        Map<String,String> mappedError = new HashMap<>();
        if (isEmpty(userDto)) {
            mappedError.put("User Body",messageSource.getMessage("validation.user.body.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_BODY_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getPassword().matches(ValidationConstants.PASSWORD_CONDITION)) {
            mappedError.put("password", messageSource.getMessage("validation.user.password.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_PASSWORD_FAILED, Locale.ENGLISH));
        }
        if (String.valueOf(userDto.getPhone()).matches(ValidationConstants.PHONE_CONDITION)) {
            mappedError.put("phone",messageSource.getMessage("validation.user.phone.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_PHONE_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getFirstName().matches(ValidationConstants.USERNAME_CONDITION)) {
            mappedError.put("firstName",messageSource.getMessage("validation.first.name.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_NAME_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getLastName().matches(ValidationConstants.USERNAME_CONDITION)) {
            mappedError.put("lastName",messageSource.getMessage("validation.last.name.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_NAME_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getPosition().matches(ValidationConstants.NOT_BLANK)) {
            mappedError.put("position",messageSource.getMessage("validation.user.position.failed",
                    null, MessageSourceAlternateResource.VALIDATION_POSITION_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getRole().matches(ValidationConstants.USERTYPE_CONDITION)) {
            mappedError.put("role",messageSource.getMessage("validation.user.type.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_TYPE_FAILED, Locale.ENGLISH));
        }
        if (!userDto.getDepartment().matches(ValidationConstants.DEPARTMENT_CONDITION)) {
            mappedError.put("department",messageSource.getMessage("validation.user.department.failed",
                    null, MessageSourceAlternateResource.VALIDATION_DEPARTMENT_FAILED, Locale.ENGLISH));
        }
        if (!EmailValidator.getInstance().isValid(userDto.getEmail())) {
            mappedError.put("email",messageSource.getMessage("validation.user.email.failed",
                    null, MessageSourceAlternateResource.VALIDATION_USER_EMAIL_FAILED, Locale.ENGLISH));
        }
        return isEmpty(mappedError) ? Collections.emptyMap() : mappedError;
    }

    public List<String> validateTechnicalDetails(TechnicalDetailsDto technicalDetailsDto) {
        List<String> errors = new ArrayList<>();
        if (isEmpty(technicalDetailsDto)) {
            errors.add(messageSource.getMessage("validation.technical-details.body.failed",
                    null, MessageSourceAlternateResource.VALIDATION_TECHNICAL_DETAILS_FAILED, Locale.ENGLISH));
        }
        if (!technicalDetailsDto.getLinkedinLink().matches(ValidationConstants.NOT_BLANK)) {
            errors.add(messageSource.getMessage("validation.technical-details.linkedin-link.failed",
                    null, MessageSourceAlternateResource.VALIDATION_TECHNICAL_DETAILS_LINKEDIN_LINK_FAILED, Locale.ENGLISH));
        }
        if (!technicalDetailsDto.getResumeLink().matches(ValidationConstants.NOT_BLANK)) {
            errors.add(messageSource.getMessage("validation.technical-details.resume-link.failed",
                    null, MessageSourceAlternateResource.VALIDATION_TECHNICAL_DETAILS_RESUME_LINK_FAILED, Locale.ENGLISH));
        }
        ListIterator<String> listIterator = technicalDetailsDto.getTags().listIterator();
        while (listIterator.hasNext()) {
            if (!listIterator.next().matches(ValidationConstants.NOT_BLANK)) {
                errors.add(messageSource.getMessage("validation.technical-details.tags.failed",
                        null, MessageSourceAlternateResource.VALIDATION_TECHNICAL_DETAILS_TAGS_FAILED, Locale.ENGLISH));
            }
        }
        if (!technicalDetailsDto.getEducation().matches(ValidationConstants.NOT_BLANK)) {
            errors.add(messageSource.getMessage("validation.technical-details.education.failed",
                    null, MessageSourceAlternateResource.VALIDATION_TECHNICAL_DETAILS_EDUCATION_FAILED, Locale.ENGLISH));
        }
        if (!technicalDetailsDto.getInterestArea().matches(ValidationConstants.NOT_BLANK)) {
            errors.add(messageSource.getMessage("validation.technical-details.interest-area.failed",
                    null, MessageSourceAlternateResource.VALIDATION_TECHNICAL_DETAILS_INTEREST_AREA_FAILED, Locale.ENGLISH));
        }
        return isEmpty(errors) ? Collections.emptyList() : errors;
    }
}
