package com.liao.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//BCrypt 加密
public class BCryptUtils {

    // 强度（cost），默认10，越大越安全但越慢
    private static final int STRENGTH = 10;

    private static final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder(STRENGTH);

    //密码加密
    public static String encode(String password) {
        return encoder.encode(password);
    }

    //密码校验
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
