package com.etekcity.cloud.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.etekcity.cloud.common.Constant;
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

    /**
     * 邮箱格式校验
     */
    public static void checkEmailFormat(String email) throws Exception {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
        Matcher matcher = pattern.matcher(email);
        boolean isMatch = matcher.matches();
        if (!isMatch) {
            throw new UserServiceException(ErrorCode.EMAIL_FORMAT_ERROR);
        }
    }

    /**
     * 密码格式校验
     */
    public static void checkPasswordFormat(String password, String type) throws Exception {
        Pattern pattern = Pattern.compile("^[\\x21-\\x7e]*$");
        Matcher matcher = pattern.matcher(password);
        boolean isMatch = matcher.matches();
        int len = password.length();
        if (len < Constant.PWD_MIN_LENGTH || len > Constant.PWD_MAX_LENGTH || !isMatch) {
            if (Constant.PWD.equals(type)) {
                throw new UserServiceException(ErrorCode.PASSWORD_FORMAT_ERROR);
            }
            if (Constant.OLD_PWD.equals(type)) {
                throw new UserServiceException(ErrorCode.OLD_PASSWORD_FORMAT_ERROR);
            }
            if (Constant.NEW_PWD.equals(type)) {
                throw new UserServiceException(ErrorCode.NEW_PASSWORD_FORMAT_ERROR);
            }
        }
    }

    /**
     * 昵称格式校验
     */
    public static void checkNicknameFormat(String nickname) throws Exception {
        if (nickname != null) {
            int len = nickname.length();
            if (len > Constant.NICKNAME_MAX_LENGTH) {
                throw new UserServiceException(ErrorCode.NICKNAME_FORMAT_ERROR);
            }
        }
    }

    /**
     * 地址格式校验
     */
    public static void checkAddressFormat(String address) throws Exception {
        if (address != null) {
            int len = address.length();
            if (len > Constant.ADDRESS_MAX_LENGTH) {
                throw new UserServiceException(ErrorCode.ADDRESS_FORMAT_ERROR);
            }
        }
    }
}
