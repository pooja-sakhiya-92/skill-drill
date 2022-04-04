package com.skilldrill.registration.utilities.validations;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
import com.skilldrill.registration.dto.AppreciationDto;
import com.skilldrill.registration.dto.ContributionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class AppreciationValidations {
    @Autowired
    private MessageSource messageSource;

    public Map<String, String> validate(AppreciationDto appreciationDto) {
        Map<String,String> mappedError = new HashMap<>();
        if (isEmpty(appreciationDto)) {
            mappedError.put("Appreciation Body",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_APPRECIATION_BODY_FAILED, Locale.ENGLISH));
        }
        return isEmpty(mappedError) ? Collections.emptyMap() : mappedError;
    }
}
