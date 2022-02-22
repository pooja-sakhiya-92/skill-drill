package com.skilldrill.registration.utilities.misc;

import com.skilldrill.registration.dto.UserDto;
import com.skilldrill.registration.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HelperFunctions {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static Boolean checkUpdateFlag(UserDto userDto) {
        return userDto.getUpdateFlag();

    }

    public void updateBasicFields(User userFromDb, User user) {
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setPhone(user.getPhone());
        userFromDb.setPosition(user.getPosition());
        userFromDb.setDepartment(user.getDepartment());
    }

    public String getAccountStatus(User userFromDb) {
        if (Boolean.TRUE.equals(userFromDb.getActive()))
            return "Active";
        else
            return "Inactive";
    }

    public Boolean checkPassword(String password, String actualPassword) {
        return bCryptPasswordEncoder.matches(password, actualPassword);
    }
}
