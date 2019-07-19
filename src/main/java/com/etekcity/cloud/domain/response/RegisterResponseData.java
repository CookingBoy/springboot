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

    private long id;

    private String createAt;

    public RegisterResponseData(User user) throws Exception {

        id = user.getId();

        createAt = GetFormatTime.toUtcTime(user.getCreateAt());
    }

}
