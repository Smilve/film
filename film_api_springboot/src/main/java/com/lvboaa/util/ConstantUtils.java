package com.lvboaa.util;

/**
 * 常量
 * @author lvbo
 * @date 2021年 04月15日 21:19:22
 */
public class ConstantUtils {
    /**
     * token密匙
     */
    public static final String TOKEN_SECRET="56ads24822ef59d9e75745449b";

    /**
     * 密码盐值
     */
    public static final String PASSWORD_SECRET="s4dsad1545d4s555d";

    /**
     * 超级管理员访问权限
     */
    public static final String[] SUPER_ADMIN_URI = {
            "/user",
            "/cinema",
            "/commet",
            "/hall",
            "/movie",
            "/order",
            "/schedule",
            "/alipay",
    };

    /**
     * 管理员访问权限
     */
    public static final String[] ADMIN_URI = {
            "/user",
            "/cinema",
            "/commet",
            "/hall",
            "/movie",
            "/order",
            "/schedule",
            "/alipay",
    };

    /**
     * 用户访问权限
     */
    private static final String[] USER_URI = {
            "/user",
            "/cinema",
            "/commet",
            "/hall",
            "/movie",
            "/order",
            "/schedule",
            "/alipay",
    };
}
