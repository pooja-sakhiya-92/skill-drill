package com.skilldrill.registration.model;

import com.skilldrill.registration.enums.Department;
import com.skilldrill.registration.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(value = "user")
@AllArgsConstructor
@NoArgsConstructor
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

    
    @Field(name = "technical_details")
    private TechnicalDetails technicalDetails;


    private Boolean active;


    private Integer otp;

    @Field(name = "update_flag")
    private Boolean updateFlag;

    public User(String id, String firstName, String lastName, String email, long phone, String password, String position, Department department, Roles role, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.position = position;
        this.department = department;
        this.role = role;
        this.active = active;
    }
}
