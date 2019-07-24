package com.etekcity.cloud.common;


/**
 * error code for project
 *
 * @author vik
 */
public enum ErrorCode {

    /**
     * 请求成功
     */
    SUCCESS(0, "请求成功"),

    /**
     * 通用错误码
     */
    SERVER_INTERNAL_ERROR(-10001, "服务器内部错误"),
    SERVER_BUSY(-10002, "服务器繁忙"),
    SERVER_TIMEOUT(-10003, "服务器超时"),

    /**
     * 用户服务相关错误码
     */
    EMAIL_FORMAT_ERROR(-20101, "邮箱不合法"),
    PASSWORD_FORMAT_ERROR(-20102, "密码不合法"),
    EMAIL_ALREADY_EXIST_ERROR(-20111, "邮箱已注册"),
    PASSWORD_ERROR(-20112, "密码不正确"),
    EMAIL_NOT_EXIST_ERROR(-20113, "邮箱不存在"),
    TOKEN_INVALID_ERROR(-20201, "用户凭证已失效(过期、登出)"),
    TOKEN_ERROR(-20202, "用户凭证错误，非该用户token"),
    NICKNAME_FORMAT_ERROR(-20103, "昵称不合法"),
    ADDRESS_FORMAT_ERROR(-20104, "地址不合法"),
    OLD_PASSWORD_FORMAT_ERROR(-20105, "旧密码不合法"),
    NEW_PASSWORD_FORMAT_ERROR(-20106, "新密码不合法"),
    OLD_PASSWORD_ERROR(-20112, "老密码不正确"),

    /**
     * 请求参数错误
     */
    INVALID_REQUEST_PARAM(-10101, "请求参数错误"),
    JSON_PARSE_ERROR(-10102, "Json格式错误");

    private int code;
    private String msg;

    /**
     * Error code的构造函数
     *
     * @param code 错误编码
     * @param msg  错误信息
     */
    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
