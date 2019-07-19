package com.etekcity.cloud.domain.response;


import lombok.Data;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.util.GetFormatTime;

/**
 * 用户登录API response data实体类
 *
 * @author vik
 */
@Data
public class LoginResponseData {

    private long id;

    private String email;

    private String token;

    private int expiresIn;

    private String createAt;

    private String updateAt;

    private String nickname;

    private String address;

    public LoginResponseData(User user) throws Exception {

        id = user.getId();

        email = user.getEmail();

        createAt = GetFormatTime.toUtcTime(user.getCreateAt());

        updateAt = GetFormatTime.toUtcTime(user.getUpdateAt());

        nickname = user.getNickname();

        address = user.getAddress();

        expiresIn = 172800;
    }
}
