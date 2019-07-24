package com.etekcity.cloud.domain.response;


import lombok.Data;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.util.GetFormatTime;

/**
 * 获取用户信息API DTO实体类
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
}
