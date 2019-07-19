package com.etekcity.cloud.domain.request;

/**
 * 更新用户信息API request data实实体类
 *
 * @author vik
 */
public class UpdateUserInfoRequestData {

    private String nickname;

    private String address;

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
}
