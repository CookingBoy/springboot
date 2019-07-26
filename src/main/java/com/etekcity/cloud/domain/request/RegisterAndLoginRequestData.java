package com.etekcity.cloud.domain.request;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * 用户注册API request data实体类
 *
 * @author vik
 */
@Data
public class RegisterAndLoginRequestData {

    @NotBlank(message = "邮箱账号不能为空")
    @Length(max = 50)
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Override
    public String toString() {
        return "RegisterAndLoginRequestData{ " + "email=" + email + " }";
    }
}
