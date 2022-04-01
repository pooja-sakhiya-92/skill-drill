package com.skilldrill.registration.utilities.validations;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
import com.skilldrill.registration.dto.BadgesDto;
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
public class BadgeValidations {
    @Autowired
    private MessageSource messageSource;

    public Map<String, String> validate(BadgesDto badgesDto) {
        Map<String, String> mappedError = new HashMap<>();
        if (isEmpty(badgesDto)) {
            mappedError.put("Ratings Body", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_BADGES_BODY_FAILED, Locale.ENGLISH));
        }
        if (!badgesDto.getBadgeVersion().matches(ValidationConstants.VERSION)) {
            mappedError.put("badgeVersion", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_BADGE_VERSION_FAILED, Locale.ENGLISH));
        }

        if (!badgesDto.getDescription().matches(ValidationConstants.COMMENTS)) {
            mappedError.put("Comments", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_BADGES_DESCRIPTION_FAILED, Locale.ENGLISH));

        }
        return isEmpty(mappedError) ? Collections.emptyMap() : mappedError;
    }
}
