package com.skilldrill.registration.utilities.validations;


import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.constants.ValidationConstants;
import com.skilldrill.registration.dto.TopicDto;
import com.skilldrill.registration.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class TopicValidations {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private TopicRepository topicRepository;

    public Map<String, String> validate(TopicDto topicDto) {
        Map<String,String> mappedError = new HashMap<>();
        if (isEmpty(topicDto)) {
            mappedError.put("Topic Body",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_TOPIC_BODY_FAILED, Locale.ENGLISH));
        }

        if(!topicDto.getCreatedAt().matches(ValidationConstants.DATE_FORMAT)){
            mappedError.put("createdAt",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_CREATED_DATE_FAILED,Locale.ENGLISH));
        }
        if(isEmpty(topicDto.getTopicName())){
            mappedError.put("topicName",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_TOPIC_NAME,Locale.ENGLISH));
        }
        if(topicRepository.findByTopicName(topicDto.getTopicName()).isPresent()){
            mappedError.put("topicName",messageSource.getMessage("",
                    null, MessageSourceAlternateResource.VALIDATION_TOPIC_NAME_EXISTS,Locale.ENGLISH));
        }


        return isEmpty(mappedError) ? Collections.emptyMap() : mappedError;
    }
}

