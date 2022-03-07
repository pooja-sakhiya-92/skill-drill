package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.ContributionDto;
import com.skilldrill.registration.model.Contribution;
import com.skilldrill.registration.repository.ContributionRepository;
import com.skilldrill.registration.service.ContributionService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.ContributionValidations;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;


@RestController
@RequestMapping("api/contribution")
public class ContributionController {

    private final ContributionService contributionService;

    private final MessageSource messageSource;

    private final ContributionValidations contributionValidations;

    @Autowired
    public ContributionController(ContributionService contributionService, MessageSource messageSource, ContributionValidations contributionValidations) {
        this.contributionService = contributionService;
        this.messageSource = messageSource;
        this.contributionValidations = contributionValidations;
    }
    @PostMapping("save")
    public ResponseEntity<?> addContribution(@RequestBody ContributionDto contributionDto,
                                             @RequestParam String userName) {
        Map<String,String> errors = contributionValidations.validate(contributionDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("",
                            null, MessageSourceAlternateResource.CONTRIBUTION_ADD_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        Contribution responseBody = contributionService.addContribution(contributionDto,userName);
        return ResponseEntity.status(201).body(ResponseStructure.createResponse(HttpStatus.SC_CREATED,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CONTRIBUTION_ADDED_SUCCESSFUL,
                        Locale.ENGLISH),
                responseBody));
    }

    @PutMapping("save/image")
    public ResponseEntity<?> uploadImage(@RequestParam String contributionName ,
                                         @RequestParam MultipartFile image) {
        ContributionDto responseBody = contributionService.uploadImage(contributionName,image);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.IMAGE_ADDED_SUCCESSFUL,
                        Locale.ENGLISH),
                responseBody));
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateContribution(@RequestParam String contributionName,
                                                @RequestBody ContributionDto contributionDto) {
        ContributionDto responseBody = contributionService.updateContribution(contributionName,contributionDto);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CONTRIBUTION_UPDATED_SUCCESSFUL,
                        Locale.ENGLISH),
                responseBody));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> archiveContribution(@RequestParam String contributionName) {
        contributionService.archiveContribution(contributionName);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CONTRIBUTION_DELETED_SUCCESSFUL,
                        Locale.ENGLISH)));
    }

    @GetMapping("get-contributions")
    public ResponseEntity<?> getAllContributionOfUser(@RequestParam String userName) {
        List<Contribution> response = contributionService.getAllContributionsOfUser(userName);
        if(response.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseStructure.createResponse(HttpStatus.SC_NOT_FOUND,
                    messageSource.getMessage("", null,
                            MessageSourceAlternateResource.USER_NOT_FOUND,
                            Locale.ENGLISH)));
        }
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.CONTRIBUTION_FETCHED_SUCCESSFUL,
                        Locale.ENGLISH),response));
    }

}
