package com.autocode.autocodeback.utils;

import cn.hutool.crypto.digest.BCrypt;

/**
 * 密码加密匹配工具类
 */
public class BCryptPasswordEncoder {
    public static String encode(String password) {
        return BCrypt.hashpw(password);
    }
    
    public static boolean matches(String password, String encodePassword) {
        return BCrypt.checkpw(password, encodePassword);
    }
}
