package com.skilldrill.registration.constants;

public class ValidationConstants {

    public static String PASSWORD_CONDITION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,16}$";
    public static String PHONE_CONDITION = " ^\\d{10}$";
    public static String USERNAME_CONDITION = "^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    public static String RATING_CONDITION = "[1-5]";
    public static String NOT_BLANK = "^(?!\\s*$).+";
    public static String USERTYPE_CONDITION = "ROLE_ADMIN|ROLE_TOPIC_ADMIN|ROLE_GENERAL";
    public static String DEPARTMENT_CONDITION = "BACKEND|FRONTEND|UI|UX|QA|HR";
}
