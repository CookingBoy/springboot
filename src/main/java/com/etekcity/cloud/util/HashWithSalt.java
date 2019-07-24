package com.etekcity.cloud.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.etekcity.cloud.common.Constant;

/**
 * 加盐哈希算法
 *
 * @author vik
 */
public class HashWithSalt {

    /**
     * md5加密
     *
     * @param str 需要加密的数据
     * @return 加密结果
     * @author vik
     */
    public static String getMd5String(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        messageDigest.update(str.getBytes());
        return byteArray2HexString(messageDigest.digest());
    }

    /**
     * byte转String
     *
     * @param bytes
     * @return str
     * @author vik
     */
    private static String byteArray2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Constant.HEX_DIGITS[(b & 0xf0) >> 4]).append(Constant.HEX_DIGITS[(b & 0x0f)]);
        }
        return sb.toString();
    }
}
