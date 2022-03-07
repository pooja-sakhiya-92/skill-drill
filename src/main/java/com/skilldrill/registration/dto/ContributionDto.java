package com.skilldrill.registration.dto;

import com.skilldrill.registration.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributionDto {

    private String id;

    @Nullable
    private User user;

    private String typeOfContribution;

    private String dateOfContribution;

    private String contributionName;

    @Nullable
    private String fileLink;

    @Nullable
    private byte[] image;

    @Nullable
    private String problems;

}
