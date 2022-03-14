package com.skilldrill.registration.dto;

import com.skilldrill.registration.enums.BadgeType;
import com.skilldrill.registration.enums.Language;
import com.skilldrill.registration.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgesDto {

    private String id;

    private String badge;

    private String badgeVersion;

    private User user;

    private String description;

    private String language;

    @Nullable
    private byte[] icon;

    private String badgeExpiryDate;
}
