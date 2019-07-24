package com.etekcity.cloud.domain.request;

import lombok.Data;

/**
 * 更新用户信息API request data实实体类
 *
 * @author vik
 */
@Data
public class UpdateUserInfoRequestData {
    private String nickname;
    private String address;
}
