package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.AppreciationDto;
import com.skilldrill.registration.model.Appreciation;
import com.skilldrill.registration.service.AppreciationService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.AppreciationValidations;
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
@RequestMapping("api/Appreciation")
public class AppreciationController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    AppreciationService appreciationService;

    @Autowired
    AppreciationValidations appreciationValidations;

    @PostMapping("save")
    public ResponseEntity<?> giveAppreciation(@RequestBody AppreciationDto appreciationDto, @RequestParam String userName) {
        Map<String, String> errors = appreciationValidations.validate(appreciationDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("",
                            null, MessageSourceAlternateResource.APPRECIATION_ADD_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        Appreciation responseBody = appreciationService.giveAppreciation(appreciationDto,userName);
        return ResponseEntity.status(201).body(ResponseStructure.createResponse(HttpStatus.SC_CREATED,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.APPRECIATION_ADDED_SUCCESSFULLY,
                        Locale.ENGLISH),
                responseBody));

    }
    @PatchMapping("update")
    public ResponseEntity<?> updateAppreciation(@RequestParam String praise,
                                                @RequestBody AppreciationDto appreciationDto,@RequestParam String userName) {
        AppreciationDto responseBody = appreciationService.updateAppreciation(praise,appreciationDto,userName);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.APPRECIATION_UPDATED_SUCCESSFUL,
                        Locale.ENGLISH),
                responseBody));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteAppreciation(@RequestParam String praise,@RequestParam String userName) {
        appreciationService.deleteAppreciation(praise,userName);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.APPRECIATION_DELETED_SUCCESSFULLY,
                        Locale.ENGLISH)));
    }
    @GetMapping("get")
    public ResponseEntity<?> getAllCreativeAreas(String userName) {
        List<Appreciation> response=appreciationService.findAllAppreciations(userName);
        if (response.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("", null,
                            MessageSourceAlternateResource.NO_APPRECIATION_FOUND,
                            Locale.ENGLISH)));
        }
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.APPRECIATION_FETCHED_SUCCESSFUL,
                        Locale.ENGLISH), response));
    }
}
