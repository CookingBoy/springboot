package com.etekcity.cloud.domain.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 修改密码API request data实体类
 *
 * @author vik
 */
@Data
public class UpdatePasswordRequestData {

    @NotBlank(message = "老密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
