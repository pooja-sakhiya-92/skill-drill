package com.skilldrill.registration.dto;

import com.mongodb.lang.NonNull;
import com.skilldrill.registration.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionsAndWebinarsDto {

    private String id;

    @NotBlank(message = "Please enter the link")
    private String link;

    private String note;

    @NotBlank(message = "please mention the session starting time")
    private String startTime;

    private String duration;

    private String dateOfSession;

    @NonNull
    private Topic topic;
}
