package com.skilldrill.registration.utilities.misc;

//import com.ucfs.project.constants.SecurityConstants;

import org.apache.commons.lang3.RandomStringUtils;

public class NanoToolkit {

    public static String randomOtpGenerator() {
        return RandomStringUtils.randomNumeric(6);
    }
/*
    public static String[] getArrayOfPaths(Integer index) {
        return SecurityConstants.paths.get(index).toArray(new String[0]);
    }

    public static UserDetails getCurrentUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }*/
}
