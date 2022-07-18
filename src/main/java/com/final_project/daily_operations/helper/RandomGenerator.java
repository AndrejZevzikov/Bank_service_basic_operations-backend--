package com.final_project.daily_operations.helper;

public class RandomGenerator {

    public static String getRandomString(int length){
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
        StringBuilder randomString = new StringBuilder(length);
        for (int i=0; i<length; i++) {
            int ch = (int)(AlphaNumericStr.length() * Math.random());
            randomString.append(AlphaNumericStr.charAt(ch));
        }
        return randomString.toString();
    }
}
