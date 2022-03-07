package com.skilldrill.registration.utilities.validations;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
import com.skilldrill.registration.dto.ContributionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class ContributionValidations {
    @Autowired
    private MessageSource messageSource;

    public Map<String, String> validate(ContributionDto contributionDto) {
        Map<String,String> mappedError = new HashMap<>();
        if (isEmpty(contributionDto)) {
            mappedError.put("Contribution Body",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_CONTRIBUTION_BODY_FAILED, Locale.ENGLISH));
        }
        if(!contributionDto.getTypeOfContribution().matches(ValidationConstants.CONTRIBUTION_TYPE_CONDITION)) {
            mappedError.put("contributionType",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_CONTRIBUTION_TYPE_FAILED,Locale.ENGLISH));
        }
        if(!contributionDto.getFileLink().matches(ValidationConstants.URL_CONDITION)) {
            mappedError.put("fileLink",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_URL_FAILED,Locale.ENGLISH));
        }
        return isEmpty(mappedError) ? Collections.emptyMap() : mappedError;
    }
}
