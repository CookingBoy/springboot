package com.etekcity.cloud.domain.response;


import lombok.Data;

/**
 * 用户注册API DTO实体类
 *
 * @author vik
 */
@Data
public class RegisterResponseData {
    private String userId;
    private String createAt;
}
