package com.etekcity.cloud.domain.request;

/**
 * 用户注册API request data实体类
 *
 * @author vik
 */
public class RegisterAndLoginRequestData {

    private String email;

    private String password;

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

    @Override
    public String toString() {
        return "RegisterAndLoginRequestData{ " + "email=" + email + " }";
    }

}