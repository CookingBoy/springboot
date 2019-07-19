package com.etekcity.cloud.util;


import java.util.regex.Pattern;

import com.etekcity.cloud.common.ErrorCode;
import com.etekcity.cloud.common.exception.UserServiceException;
import com.etekcity.cloud.service.UserService;

/**
 * 工具类
 * 用于各种参数格式的校验：
 * 1.邮箱：用户邮箱地址，匹配正则："^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"
 * 2.密码：6～20位，允许字符范围为 ASCII 表编号33到126的字符
 * 3.昵称：最大32个字符，支持emoji
 * 4.地址：最大255个字符
 *
 * @author vik
 */
public class CheckUtils {


    private static final int PWD_MAX_LENGTH = 20;
    private static final int PWD_MIN_LENGTH = 6;
    private static final int NICKNAME_MAX_LENGTH = 32;
    private static final int ADDRESS_MAX_LENGTH = 255;
    private static final String PWD = "pwd";
    private static final String OLD_PWD = "oldPwd";
    private static final String NEW_PWD = "newPwd";

    /**
     * 邮箱格式校验
     */
    public static void emailFormatCheck(String email) throws Exception {
        String pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        boolean isMatch = Pattern.matches(pattern, email);
        if (!isMatch) {
            throw new UserServiceException(ErrorCode.EMAIL_FORMAT_ERROR);
        }
    }

    /**
     * 密码格式校验
     */
    public static void passwordFormatCheck(String password, String type) throws Exception {
        String pattern = "^[\\x21-\\x7e]*$";
        int len = password.length();
        boolean isMatch = Pattern.matches(pattern, password);
        if (len < PWD_MIN_LENGTH || len > PWD_MAX_LENGTH || !isMatch) {
            if (PWD.equals(type)) {
                throw new UserServiceException(ErrorCode.PASSWORD_FORMAT_ERROR);
            }
            if (OLD_PWD.equals(type)) {
                throw new UserServiceException(ErrorCode.OLD_PASSWORD_FORMAT_ERROR);
            }
            if (NEW_PWD.equals(type)) {
                throw new UserServiceException(ErrorCode.NEW_PASSWORD_FORMAT_ERROR);
            }
        }

    }

    /**
     * 昵称格式校验
     */
    public static void nicknameFormatCheck(String nickname) throws Exception {
        if (nickname != null) {
            int len = nickname.length();
            if (len > NICKNAME_MAX_LENGTH) {
                throw new UserServiceException(ErrorCode.NICKNAME_FORMAT_ERROR);
            }
        }
    }

    /**
     * 地址格式校验
     */
    public static void addressFormatCheck(String address) throws Exception {
        if (address != null) {
            int len = address.length();
            if (len > ADDRESS_MAX_LENGTH) {
                throw new UserServiceException(ErrorCode.ADDRESS_FORMAT_ERROR);
            }
        }
    }
}
