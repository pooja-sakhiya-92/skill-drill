package com.skilldrill.registration.constants;

public class MessageSourceAlternateResource {

    //User Validations
    public static final String VALIDATION_USER_BODY_FAILED = "Please enter all the details of the user to register";
    public static final String VALIDATION_USER_PASSWORD_FAILED = "Password must be of minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character";
    public static final String VALIDATION_USER_PHONE_FAILED = "Please enter a valid phone number";
    public static final String VALIDATION_USER_NAME_FAILED = "Username must start with a character and must not contain any special characters/digits";
    public static final String VALIDATION_USER_TYPE_FAILED = "Please specify a user type";
    public static final String VALIDATION_USER_EMAIL_FAILED = "Invalid Email id. Please enter a valid email id";
    public static final String VALIDATION_POSITION_FAILED = "Invalid Employee designation";
    public static final String VALIDATION_DEPARTMENT_FAILED = "Invalid Employee Department";

    //User response messages
    public static final String USER_REGISTRATION_FAILED = "User registration failed";
    public static final String USER_REGISTRATION_SUCCESSFUL = "User registration successful";
    public static final String USER_ALREADY_REGISTERED = "User already registered. please login to access your account.";
    public static final String USER_NOT_FOUND = "User not found";


    //EMAIL EXCEPTIONS
    public static final String MANDATORY_FIELDS_EMPTY = "Mandatory fields are empty cannot continue";
    public static final String EMPTY_RECIPIENT_EMAIL = "Recipient email empty cannot proceed";
}
