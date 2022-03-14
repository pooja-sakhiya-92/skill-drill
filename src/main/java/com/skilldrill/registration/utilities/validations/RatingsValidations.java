package com.skilldrill.registration.utilities.validations;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
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
public class RatingsValidations {

    @Autowired
    private MessageSource messageSource;

    public Map<String, String> validate(RatingsDto ratingsDto) {
        Map<String, String> mappedError = new HashMap<>();
        if (isEmpty(ratingsDto)) {
            mappedError.put("Ratings Body", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_RATINGS_BODY_FAILED, Locale.ENGLISH));
        }
        if (!ratingsDto.getStars().toString().matches(ValidationConstants.STARS)) {
            mappedError.put("Stars", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_RATINGS_STARS_FAILED, Locale.ENGLISH));
        }

        if (!ratingsDto.getComments().matches(ValidationConstants.COMMENTS)) {
            mappedError.put("Comments", messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_RATINGS_COMMENTS_FAILED, Locale.ENGLISH));

        }
            return isEmpty(mappedError) ? Collections.emptyMap() : mappedError;
    }
}
