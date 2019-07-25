package com.etekcity.cloud.domain;


import lombok.Data;

/**
 * 用户实体类
 *
 * @author vik
 */
@Data
public class User {
    /**
     * 用户id
     */
    private long id;

    /**
     * 用户邮箱账号
     */
    private String email;

    /**
     * 用户密码，密文
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 创建时间，输出为ISO8601标准格式
     */
    private String createAt;

    /**
     * 更新时间
     */
    private String updateAt;

    /**
     * 哈希盐值
     */
    private String salt;
}
