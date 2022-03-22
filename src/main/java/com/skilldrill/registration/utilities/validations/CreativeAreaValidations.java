package com.skilldrill.registration.utilities.validations;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
import com.skilldrill.registration.dto.CreativeAreaDto;
import com.skilldrill.registration.dto.RatingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class CreativeAreaValidations {
    @Autowired
    private MessageSource messageSource;

    public Map<String, String> validate(CreativeAreaDto creativityDto) {
        Map<String, String> mappedError = new HashMap<>();
        if (isEmpty(creativityDto)) {
            mappedError.put("CreativeArea Body", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_CREATIVEAREA_BODY_FAILED, Locale.ENGLISH));
        }
        /*if (!creativityDto.getStars().toString().matches(ValidationConstants.STARS)) {
            mappedError.put("Stars", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_RATINGS_STARS_FAILED, Locale.ENGLISH));
        }*/

        if (!creativityDto.getCreativeSkill().matches(ValidationConstants.CREATIVESKILL)) {
            mappedError.put("Comments", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_CREATIVEAREA_FAILED, Locale.ENGLISH));

        }
        return isEmpty(mappedError) ? Collections.emptyMap() : mappedError;
    }
}
