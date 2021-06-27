package com.lvboaa.util;

public enum ResultCode {
    /*1000～1999 区间表示参数错误
      2000～2999 区间表示用户错误
      3000～3999 区间表示接口异常*/
    /*成功状态码*/
    SUCCESS(200,"success"),
    /*失败状态码*/
    ERROR(500,"error"),
    /*参数错误：1001-1999*/
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_BIND_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺失"),
    /*用户错误*/
    USER_NOT_LOGGED_IN(2001,"用户未登录，访问的路径需要验证，请登录"),
    USER_LOGIN_ERROR(2002,"账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003,"账号已被冻结"),
    USER_NOT_EXIST(2004,"用户不存在"),
    USER_HAS_EXISTED(2005,"用户已存在"),

    /*token签名错误*/
    TOKEN_VERIFY_ERROR(400,"token错误"),
    /*无权限*/
    AUTH_ERROR(401,"权限不够"),
    /*服务器错误*/
    SERVER_ERROR(500,"数据库服务器异常");

    private Integer code;
    private String message;
    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    public Integer code(){
        return this.code;
    }
    public String message(){
        return this.message;
    }

}
