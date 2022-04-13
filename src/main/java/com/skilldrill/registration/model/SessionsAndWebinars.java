package com.skilldrill.registration.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "session_webinar")
public class SessionsAndWebinars {
    @Id
    private String id;

    private String link;

    private String note;

    @Field("start_time")
    private String startTime;

    private String duration;

    @Field("date_of_session")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private String dateOfSession;

    private Topic topic;

}
