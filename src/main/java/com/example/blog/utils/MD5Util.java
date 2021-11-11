package com.example.blog.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt="m0ov3e22e";

    // 第一次加密，前端到表单
    public static String inputPassToFromPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(3)+salt.charAt(5)+salt.charAt(8);
        return md5(str);
    }

    // 第二次加密，表单到数据库
    public static String fromPassToDBPass(String formPass, String salt){
        String str = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(3)+salt.charAt(5)+salt.charAt(8);
        return md5(str);
    }

    // 实际调用的方法
    public static String inputPassToDBPass(String inputPass, String salt){
        String formPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("mj031017"));
        System.out.println(fromPassToDBPass("9a2dde11a55e164f3eeeeb088fba9891", "m0ov3e22e"));
        System.out.println(inputPassToDBPass("mj031017", "m0ov3e22e"));
    }
}
