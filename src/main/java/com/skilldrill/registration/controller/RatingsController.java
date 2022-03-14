package com.skilldrill.registration.controller;

import com.skilldrill.registration.constants.MessageSourceAlternateResource;
import com.skilldrill.registration.dto.RatingsDto;
import com.skilldrill.registration.model.Ratings;
import com.skilldrill.registration.model.User;
import com.skilldrill.registration.service.RatingsService;
import com.skilldrill.registration.utilities.misc.ResponseStructure;
import com.skilldrill.registration.utilities.validations.RatingsValidations;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("/api/ratings")
public class RatingsController {

    @Autowired
    RatingsValidations ratingsValidations;
    RatingsService ratingsService;
    MessageSource messageSource;
    @Autowired
    public RatingsController(RatingsService ratingsService, MessageSource messageSource){
        this.ratingsService=ratingsService;
        this.messageSource = messageSource;
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllRatingsOfUser(String userName){
        RatingsDto ratings=ratingsService.getAllRatingsOfUser(userName);
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(org.apache.http.HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.RATINGS_FETCHED_SUCCESSFUL,
                        Locale.ENGLISH),ratings));
    }


    @PostMapping("save")
    public  ResponseEntity<?> saveRatings(@RequestBody RatingsDto ratingsDto,@RequestParam String userName){
        Map<String,String> errors = ratingsValidations.validate(ratingsDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("",
                            null, MessageSourceAlternateResource.RATINGS_ADD_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        RatingsDto response= ratingsService.addRatings(ratingsDto,userName);
//        HttpHeaders httpHeaders= new HttpHeaders();
//        httpHeaders.add("ratings", "/api/ratings" + response.getId().toString());
        //return new ResponseEntity<>(ratings1, httpHeaders, HttpStatus.CREATED);
          return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                  messageSource.getMessage("", null,
                          MessageSourceAlternateResource.RATINGS_ADDED_SUCCESSFUL,
                          Locale.ENGLISH),response));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateRatingsDto(@RequestParam String userName, @RequestBody RatingsDto ratingsDto){
        Map<String,String> errors = ratingsValidations.validate(ratingsDto);
        if (!isEmpty(errors)) {
            ResponseStructure<Void> response = ResponseStructure.createResponse(HttpStatus.SC_BAD_REQUEST,
                    messageSource.getMessage("",
                            null, MessageSourceAlternateResource.RATINGS_ADD_FAILED, Locale.ENGLISH));
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }
        RatingsDto response = ratingsService.updateRatings(userName, ratingsDto);
        // return new ResponseEntity<>(ratingsService.getRatingsById(id), HttpStatus.OK);
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.RATINGS_UPDATED_SUCCESSFUL,
                        Locale.ENGLISH),response));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteRatings(@RequestParam String userName){
        ratingsService.deleteRatings(userName);
      // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.status(200).body(ResponseStructure.createResponse(HttpStatus.SC_OK,
                messageSource.getMessage("", null,
                        MessageSourceAlternateResource.RATINGS_DELETED_SUCCESSFUL,
                        Locale.ENGLISH)));
    }
}
