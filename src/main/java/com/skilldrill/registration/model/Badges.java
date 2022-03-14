package com.skilldrill.registration.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skilldrill.registration.enums.BadgeType;
import com.skilldrill.registration.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(value = "badges")
@AllArgsConstructor
@NoArgsConstructor
public class Badges {
    @Id
    private String id;

    private BadgeType badge;

    @Field("badge_version")
    private String badgeVersion;

    @JsonIgnore
    private User user;

    private String description;

    private Language language;

    @JsonIgnore
    private byte[] icon;

    @JsonFormat(pattern="yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @Field("badge_expiry_date")
    private String badgeExpiryDate;
}
