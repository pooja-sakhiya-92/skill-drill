package com.skilldrill.registration.utilities.misc;

import com.skilldrill.registration.model.User;

public class HelperFunctions {
    public static void updateBasicFields(User userFromDb, User user) {
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setPhone(user.getPhone());
        userFromDb.setPosition(user.getPosition());
        userFromDb.setDepartment(user.getDepartment());
    }

    public static String getAccountStatus(User userFromDb) {
        if (userFromDb.getActive())
            return "Active";
        else
            return "Inactive";
    }
}
