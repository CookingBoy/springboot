package com.etekcity.cloud.domain;


/**
 * 用户实体类
 * @author vik
 */
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
     * 创建时间(暂时是这个，标准为ISO8601格式)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
