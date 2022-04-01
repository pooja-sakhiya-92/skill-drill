package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.CreativeAreaDto;
import com.skilldrill.registration.model.CreativeArea;
import com.skilldrill.registration.service.CreativeAreaService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.CreativeAreaValidations;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("api/creativeArea")
public class CreativeAreaController {

    @Autowired
    private CreativeAreaService creativeAreaService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CreativeAreaValidations creativeAreaValidations;

    @PostMapping("save")
    public ResponseEntity<?> addCreativity(@RequestBody CreativeAreaDto creativityDto,@RequestParam String userName) {
        Map<String, String> errors = creativeAreaValidations.validate(creativityDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("",
                            null, MessageSourceAlternateResource.VALIDATION_CREATIVEAREA_BODY_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        CreativeArea responseBody = creativeAreaService.addCreativity(creativityDto,userName);
        return ResponseEntity.status(201).body(ResponseStructure.createResponse(HttpStatus.SC_CREATED,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CREATIVEAREA_ADDED_SUCCESSFUL,
                        Locale.ENGLISH),
                responseBody));

    }

    @PatchMapping("update")
    public ResponseEntity<?> updateCreativity(@RequestParam String creativeSkill,
                                             @RequestBody CreativeAreaDto creativityDto) {
        CreativeAreaDto responseBody = creativeAreaService.updateCreativity(creativeSkill, creativityDto);
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CONTRIBUTION_UPDATED_SUCCESSFUL,
                        Locale.ENGLISH),
                responseBody));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteCreativeArea(@RequestParam String creativeSkill,@RequestParam String userName) {
        creativeAreaService.deleteCreativeArea(creativeSkill,userName);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CREATIVE_AREA_DELETED_SUCCESSFULLY,
                        Locale.ENGLISH)));
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllCreativeAreas(String userName) {
        List<CreativeArea> response=creativeAreaService.findAllCreativeAreas(userName);
        if (response.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("", null,
                            MessageSourceAlternateResource.NO_CREATIVE_AREAS,
                            Locale.ENGLISH)));
        }
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CREATIVE_AREA_FETCHED_SUCCESSFUL,
                        Locale.ENGLISH), response));
    }
}
