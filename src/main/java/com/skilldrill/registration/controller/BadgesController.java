package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.BadgesDto;
import com.skilldrill.registration.model.Badges;
import com.skilldrill.registration.service.BadgeService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.BadgeValidations;
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
@RequestMapping("api/badges")
public class BadgesController {

    private final BadgeService badgeService;

    private final BadgeValidations badgeValidations;

    private final MessageSource messageSource;

    @Autowired
    public BadgesController(BadgeService badgeService, BadgeValidations badgeValidations, MessageSource messageSource) {
        this.badgeService = badgeService;
        this.badgeValidations = badgeValidations;
        this.messageSource = messageSource;
    }

    @PostMapping("assign")
    public ResponseEntity<?> addBadge(@RequestBody BadgesDto badgesDto, @RequestParam String userName) {
        Map<String,String> errors = badgeValidations.validate(badgesDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("",
                            null, MessageSourceAlternateResource.BADGES_ADD_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        Badges badges = badgeService.addBadges(badgesDto, userName);
        return ResponseEntity.status(201).body(ResponseStructure.createResponse(HttpStatus.SC_CREATED,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.BADGES_ADDED_SUCCESSFUL,
                        Locale.ENGLISH), badges));
    }

    @PatchMapping("icon")
    public ResponseEntity<?> addIcon(@RequestParam String userName,
                             @RequestParam String badge,
                             @RequestParam MultipartFile icon) {
        Badges badges = badgeService.updateIconImage(userName, badge, icon);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.IMAGE_ADDED_SUCCESSFUL,
                        Locale.ENGLISH),
                badges));

    }
    @PutMapping("update")
    public ResponseEntity<?> updateBadges(@RequestParam String userName,
                                  @RequestParam String badge,
                                  @RequestBody BadgesDto badgesDto) {
        final BadgesDto badgesRes = badgeService.updateBadges(userName, badge, badgesDto);

        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.BADGES_UPDATED_SUCCESSFUL,
                        Locale.ENGLISH),
                badgesRes));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteBadge(@RequestParam String badge , @RequestParam String userName) {
        badgeService.deleteBadges(userName,badge);
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.BADGES_DELETED_SUCCESSFUL,
                        Locale.ENGLISH)));
    }

    @GetMapping("get-all")
    public List<Badges> getAllBadgeOfUser(@RequestParam String userName) {
        return badgeService.getAllBadgesOfUser(userName);
    }
}
