package org.aoptut.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserValidation {

    public static Boolean checkPasswordParameters(String password) {
        if (password.length() < 8) {
            System.out.println("Password length should be greater than 8 characters");
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Password should contain atleast one uppercase letter");
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            System.out.println("Password should contain atleast one lowercase letter");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            System.out.println("Password should contain atleast one number");
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()\":,.<>{}|?].*")) {
            System.out.println("Password should contain atleast one special character");
            return false;
        }
        return true;
    }

    public static Boolean checkEmail(String email){
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            System.out.println("Email is invalid");
            return false;
        }
        return true;
    }
}
