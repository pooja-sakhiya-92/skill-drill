package com.skilldrill.registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

@Document(value = "topic")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic {
    @Id
    @Field("topic_id")
    private String id;


    @Field("topic_name")
    @Indexed(unique = true)
    private String topicName;

    @Field("content")
    private String content;

    @Field("content_writer")
    private String contentWriter;

    @DateTimeFormat(fallbackPatterns = "dd/mm/yyyy")
    private String createdAt;


    private Skills skills;
}
