package com.skilldrill.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {

    @Field("topic_id")
    private String id;

    @NotNull
    @Field("topic_name")
    private String topicName;

    @Field("content")
    private String content;

    @Field("content_writer")
    private String contentWriter;

    @DateTimeFormat(fallbackPatterns = "dd/mm/yyyy")
    private String createdAt;

    private String skillName;

    private String ParentTopicName;
}
