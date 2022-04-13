package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.TopicDto;
import com.skilldrill.registration.model.Topic;
import com.skilldrill.registration.service.TopicService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.TopicValidations;
import org.apache.http.HttpStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("api/topic")
public class TopicController {

    private final TopicService topicService;

    private final TopicValidations topicValidations;

    private final MessageSource messageSource;

    public TopicController(TopicService topicService, TopicValidations topicValidations, MessageSource messageSource) {
        this.topicService = topicService;
        this.topicValidations = topicValidations;
        this.messageSource = messageSource;
    }

    @PostMapping("save")
    public ResponseEntity<?> addTopic(@RequestBody TopicDto topicDto) {
        Map<String, String> errors = topicValidations.validate(topicDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("",
                            null, MessageSourceAlternateResource.TOPIC_ADD_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        Topic responseBody = topicService.addTopic(topicDto);
        return ResponseEntity.status(201).body(ResponseStructure.createResponse(HttpStatus.SC_CREATED,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.TOPIC_ADDED_SUCCESSFUL,
                        Locale.ENGLISH),
                responseBody));
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllTopics() {
        List<Topic> response = topicService.getAllTopics();
        if (response.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("", null,
                            MessageSourceAlternateResource.TOPICS_NOT_FOUND,
                            Locale.ENGLISH)));
        }
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.TOPICS_FETCHED_SUCCESSFUL,
                        Locale.ENGLISH), response));
    }

    @GetMapping("skill")
    public ResponseEntity<?> getTopicsBySkill(@RequestParam String skillName) {
        List<Topic> response = topicService.getTopicsBySkill(skillName);
        if (response.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("", null,
                            MessageSourceAlternateResource.TOPICS_NOT_FOUND,
                            Locale.ENGLISH)));
        }
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.TOPICS_FETCHED_SUCCESSFUL,
                        Locale.ENGLISH), response));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> archiveContribution(@RequestParam String topicName) {
        topicService.deleteTopic(topicName);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.TOPIC_DELETED_SUCCESSFUL,
                        Locale.ENGLISH)));
    }


}
