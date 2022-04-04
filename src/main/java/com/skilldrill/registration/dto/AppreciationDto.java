package com.skilldrill.registration.dto;


import com.skilldrill.registration.model.User;
import lombok.*;
import org.springframework.lang.Nullable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppreciationDto {
    private String id;

    private String praise;

    private String date_of_praise;

    private String appreciatorName;

    @Nullable
    private User user;
}
