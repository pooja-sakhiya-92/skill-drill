package com.skilldrill.registration.model;

import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.lang.Nullable;

@Data
@Document(value = "user")
public class User {

    @Id
    private String id;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    private String email;

    private Long phone;

    private String password;

    private String position;

    private Department department;

    private Roles role;

    @Nullable
    @Field(name = "technical_details")
    private TechnicalDetails technicalDetails;

    @Nullable
    private Boolean active;

    @Nullable
    private Integer otp;

    @Nullable
    @Field(name = "update_flag")
    private Boolean updateFlag;

}
