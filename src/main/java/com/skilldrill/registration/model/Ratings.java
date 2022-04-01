package com.skilldrill.registration.model;

import com.skilldrill.registration.dto.RatingsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "ratings")
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {

    @Id
    private String id;
    private User user;
    private Integer stars;
    private String comments;

}



