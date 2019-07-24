package com.etekcity.cloud.common;

/**
 * 定义所用到的所有常量
 *
 * @author vik
 */
public class Constant {
    /**
     * token相关常量
     */
    //token过期时间，单位：S
    public static final long REDIS_EXPIRES_IN = 172800;
    public static final long MAX_TOKEN_LIST_NUMBER = 5;

    /**
     * 校验工具类常量
     */
    public static final int PWD_MAX_LENGTH = 20;
    public static final int PWD_MIN_LENGTH = 6;
    public static final int NICKNAME_MAX_LENGTH = 32;
    public static final int ADDRESS_MAX_LENGTH = 255;
    //表明密码类型，方便返回对应错误码
    public static final String PWD = "pwd";
    public static final String OLD_PWD = "oldPwd";
    public static final String NEW_PWD = "newPwd";

    /**
     * 加盐哈希算法常量
     * 定义char数组，16进制对应的基本字符
     */
    public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
