package com.etekcity.cloud.domain.response;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.util.GetFormatTime;

/**
 * 用户注册API response data实体类
 *
 * @author vik
 */
@Data
@JsonSerialize
public class RegisterResponseData {

    private long userId;

    private String createAt;

    public RegisterResponseData(User user) throws Exception {

        userId = user.getId();

        createAt = GetFormatTime.toUtcTime(user.getCreateAt());
    }

}
