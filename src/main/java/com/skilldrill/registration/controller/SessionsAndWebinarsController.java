package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.SessionsAndWebinarsDto;
import com.skilldrill.registration.model.SessionsAndWebinars;
import com.skilldrill.registration.service.SessionsAndWebinarsService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("api/SessionsAndWebinars")
public class SessionsAndWebinarsController {

    @Autowired
    SessionsAndWebinarsService sessionsAndWebinarsService;

    @Autowired
    MessageSource messageSource;

    @PostMapping("save")
    public ResponseEntity<?> addSessionOrWebinar(@RequestBody @Valid SessionsAndWebinarsDto sessionsAndWebinarsDto) {
        SessionsAndWebinars responseBody = sessionsAndWebinarsService.add(sessionsAndWebinarsDto);
        return ResponseEntity.status(201).body(ResponseStructure.createResponse(HttpStatus.SC_CREATED,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.SESSIONS_AND_WEBINARS_ADDED_SUCCESSFULLY,
                        Locale.ENGLISH),
                responseBody));

    }

    @PutMapping("update")
    public ResponseEntity<?> updateSessionOrWebinar(@RequestBody @Valid SessionsAndWebinarsDto sessionsAndWebinarsDto, @RequestParam String id) {
        SessionsAndWebinarsDto responseBody = sessionsAndWebinarsService.update(sessionsAndWebinarsDto, id);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.SESSIONS_AND_WEBINARS_UPDATED_SUCCESSFULLY,
                        Locale.ENGLISH),
                responseBody));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteSessionOrWebinar(@RequestParam String id) {
        sessionsAndWebinarsService.delete(id);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.SESSION_OR_WEBINAR_DELETED_SUCCESSFULLY,
                        Locale.ENGLISH)));
    }

    @GetMapping("get")
    public ResponseEntity<?> getAll(String topicName) {
        List<SessionsAndWebinars> response = sessionsAndWebinarsService.findAllByTopic(topicName);
        if (response.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("", null,
                            MessageSourceAlternateResource.NO_SESSIONS_OR_WEBINARS_FOUND,
                            Locale.ENGLISH)));
        }
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.SESSION_OR_WEBINAR_DETAILS_FETCHED_SUCCESSFULLY,
                        Locale.ENGLISH), response));
    }

}
