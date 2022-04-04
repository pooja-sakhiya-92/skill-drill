package com.skilldrill.registration.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("appreciation")
public class Appreciation {
    @Id
    private String id;

    private String praise;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Field("date_of_praise")
    private String dateOfPraise;

    @Field("appreciator_name")
    private String appreciatorName;

    private User user;


}
