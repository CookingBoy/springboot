package com.etekcity.cloud.domain.request;

/**
 * 修改密码API request data实体类
 *
 * @author vik
 */
public class UpdatePasswordRequestData {

    private String oldPassword;

    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
