package com.zyz.demo.mytools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptUtil {
    public static String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");

            byte[] bytes = md.digest(password.getBytes());

            String str = Base64.getEncoder().encodeToString(bytes);

            return str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}