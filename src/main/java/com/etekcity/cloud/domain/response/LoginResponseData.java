package com.etekcity.cloud.domain.response;


import lombok.Data;

import com.etekcity.cloud.common.Constant;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.util.GetFormatTime;

/**
 * 用户登录API DTO实体类
 *
 * @author vik
 */
@Data
public class LoginResponseData {
    private String token;
    private long expiresIn;
    private long userId;
    private String email;
    private String nickname;
    private String address;
    private String createAt;
    private String updateAt;
}
