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
    public static final String VALIDATION_TECHNICAL_DETAILS_FAILED = "Please enter all the technical-details of the user";
    public static final String VALIDATION_TECHNICAL_DETAILS_LINKEDIN_LINK_FAILED = "Please enter a valid linkedIn profile link";
    public static final String VALIDATION_TECHNICAL_DETAILS_RESUME_LINK_FAILED = "Please enter a valid resume link";
    public static final String VALIDATION_TECHNICAL_DETAILS_TAGS_FAILED = "Please enter tags related to your technical skills";
    public static final String VALIDATION_TECHNICAL_DETAILS_EDUCATION_FAILED = "Please enter your education details";
    public static final String VALIDATION_TECHNICAL_DETAILS_INTEREST_AREA_FAILED = "Please enter your area of interest";

    //User response messages
    public static final String USER_REGISTRATION_FAILED = "User registration failed";
    public static final String USER_REGISTRATION_SUCCESSFUL = "User registration successful";
    public static final String USER_ALREADY_REGISTERED = "User already registered. please login to access your account.";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_LOGIN_FAILED = "Invalid Username or Password/User disabled";
    public static final String USER_LOGIN_SUCCESSFUL = "Login successful";
    public static final String TECHNICAL_DETAILS_UPDATE_FAILED = "Technical details update failed";
    public static final String TECHNICAL_DETAILS_UPDATE_SUCCESSFUL = "Technical details update successful";
    public static final String PASSWORD_VERIFICATION_SUCCESSFUL = "Password verified successfully";
    public static final String EMAIL_VERIFICATION_SUCCESSFUL = "Email verification successful";

    public static final String USER_DETAILS_ADDED_SUCCESSFUL = "User details added successful";
    public static final String BASIC_DETAILS_UPDATE_FAILED = "Basic details Update failed";
    public static final String BASIC_DETAILS_UPDATE_SUCCESSFUL = "Basic details update successful";

    //EMAIL EXCEPTIONS
    public static final String MANDATORY_FIELDS_EMPTY = "Mandatory fields are empty cannot continue";
    public static final String EMPTY_RECIPIENT_EMAIL = "Recipient email empty cannot proceed";
    public static final String INVALID_USER_PASSWORD = "Invalid Password entered.";
    public static final String UPDATEFLAG_ALREADY_SET = "Update Flag already set";
    public static final String INVALID_EMAIL_OTP = "Invalid OTP!. Verification failed";
    public static final String VERIFICATION_TOKEN_EXPIRED = "OTP for email verification expired";

    //Contribution Response message
    public static final String CONTRIBUTION_ADDED_SUCCESSFUL = "Contribution Successfully Added.";
    public static final String IMAGE_ADDED_SUCCESSFUL = "Image Successfully Added.";
    public static final String CONTRIBUTION_UPDATED_SUCCESSFUL = "Contribution Successfully updated";
    public static final String CONTRIBUTION_DELETED_SUCCESSFUL = "Contribution Successfully deleted";
    public static final String CONTRIBUTION_FETCHED_SUCCESSFUL = "All fetched";

    //Rating Response message
    public static final String RATINGS_ADDED_SUCCESSFUL = "Ratings Successfully Added.";
    public static final String RATINGS_UPDATED_SUCCESSFUL = "Ratings Successfully updated";
    public static final String RATINGS_DELETED_SUCCESSFUL = "Ratings Successfully deleted";
    public static final String RATINGS_FETCHED_SUCCESSFUL = "All fetched";


    //Contribution validations
    public static final String VALIDATION_CONTRIBUTION_BODY_FAILED = "Please enter all the details of the contribution";
    public static final String VALIDATION_CONTRIBUTION_TYPE_FAILED = "Please specify a valid contribution type";
    public static final String VALIDATION_URL_FAILED = "Please enter a valid URL";
    public static final String CONTRIBUTION_ADD_FAILED = "Something went wrong!";

    //Ratings validations
    public static final String VALIDATION_RATINGS_BODY_FAILED = "Please enter all the details of the Ratings";
    public static final String VALIDATION_RATINGS_STARS_FAILED = "You can not add more than 5 star and less than 1 star";
    public static final String VALIDATION_RATINGS_COMMENTS_FAILED = "Please enter Comments between 1 to 240 character";
    public static final String RATINGS_ADD_FAILED = "Something went wrong!";

    //Badges validation
    public static final String VALIDATION_BADGES_BODY_FAILED = "Please enter all the details of the badges";
    public static final String VALIDATION_BADGE_VERSION_FAILED = "Please enter a valid version tag!";
    public static final String VALIDATION_BADGES_DESCRIPTION_FAILED = "enter description between 1 to 240 character";
    public static final String BADGES_ADD_FAILED = "Badges not added!";
    public static final String BADGES_ADDED_SUCCESSFUL = "Badges successfully added";
    public static final String BADGES_UPDATED_SUCCESSFUL = "Badges Updated";
    public static final String BADGES_DELETED_SUCCESSFUL = "Badges deleted";


   //craetiveArea validations
    public static final String VALIDATION_CREATIVEAREA_BODY_FAILED = "Please enter all the details of the craetiveArea";
    public static final String VALIDATION_CREATIVEAREA_FAILED = "creativeSkill is not valid";
    public static final String CREATIVEAREA_ADD_FAILED = "Creativity not added!";
    public static final String CREATIVEAREA_ADDED_SUCCESSFUL = "Creativity successfully added";
    public static final String CREATIVEAREA_UPDATED_SUCCESSFUL = "Creativity Updated";
    public static final String CREATIVE_AREA_DELETED_SUCCESSFULLY = "CreativeArea Successfully deleted";
    public static final String CREATIVE_AREA_FETCHED_SUCCESSFUL="All fetched";
    public static final String NO_CREATIVE_AREAS = "No creative Areas present.";
    public static final String VALIDATION_CREATIVEAREA_FIELD_FAILED="Field should be string";

    //appreciation validations
    public static final String VALIDATION_APPRECIATION_BODY_FAILED = "Please enter all the details of the appreciation";
    public static final String APPRECIATION_ADD_FAILED = "appreciation not added";
    public static final String APPRECIATION_ADDED_SUCCESSFULLY = "appreciation successfully added";
    public static final String APPRECIATION_UPDATED_SUCCESSFUL = "Appreciation updated successfully";
    public static final String APPRECIATION_DELETED_SUCCESSFULLY = "Appreciation Successfully deleted";
    public static final String NO_APPRECIATION_FOUND = "No appreciations found";
    public static final String APPRECIATION_FETCHED_SUCCESSFUL = "All fetched";

    //Topic Validations
    public static final String VALIDATION_TOPIC_BODY_FAILED = "Please enter all the details of the topic";
    public static final String VALIDATION_SKILL_NAME_FAILED = "Please specify a valid skill name";
    public static final String VALIDATION_CREATED_DATE_FAILED = "Please enter a valid date format";
    public static final String TOPIC_ADD_FAILED = "Something went wrong!";
    public static final String TOPICS_NOT_FOUND = "Topic not found";
    public static final String TOPICS_FETCHED_SUCCESSFUL = "Topics are fetched";
    public static final String TOPIC_DELETED_SUCCESSFUL = "Topic successfully deleted";
    public static final String TOPIC_ADDED_SUCCESSFUL ="Topic added successfully" ;
    public static final String VALIDATION_TOPIC_NAME = "Topic Name can not be empty";
}
