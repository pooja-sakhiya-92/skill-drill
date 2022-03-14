package com.skilldrill.registration.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skilldrill.registration.enums.ContributionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(value = "contribution")
@AllArgsConstructor
@NoArgsConstructor
public class Contribution {

    @Id
    private String id;

    private User user;

    @Field("type_of_contribution")
    private ContributionType typeOfContribution;

    @JsonFormat(pattern="yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @Field("date_of_contribution")
    private String dateOfContribution;

    @Field("contribution_name")
    private String contributionName;

    @JsonIgnore
    @Field("file_link")
    private String fileLink;

    @JsonIgnore
    private byte[] image;

    @JsonIgnore
    private String problems;


}
