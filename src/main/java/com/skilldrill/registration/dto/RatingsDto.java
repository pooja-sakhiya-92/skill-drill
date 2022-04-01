package com.skilldrill.registration.dto;

import com.skilldrill.registration.model.Ratings;
import com.skilldrill.registration.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingsDto {

    private String id;

    @Nullable
    private User user;
    private String stars;
    private String comments;

}





