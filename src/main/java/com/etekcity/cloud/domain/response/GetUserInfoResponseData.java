package com.etekcity.cloud.domain.response;


import lombok.Data;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.util.GetFormatTime;

/**
 * 获取用户信息API response data实体类
 *
 * @author vik
 */
@Data
public class GetUserInfoResponseData {

    private long userId;

    private String email;

    private String nickname;

    private String address;

    private String createAt;

    private String updateAt;

    public GetUserInfoResponseData(User user) throws Exception {

        userId = user.getId();

        email = user.getEmail();

        createAt = GetFormatTime.toUtcTime(user.getCreateAt());

        updateAt = GetFormatTime.toUtcTime(user.getUpdateAt());

        nickname = user.getNickname();

        address = user.getAddress();

    }
}
