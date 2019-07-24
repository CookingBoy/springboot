package com.etekcity.cloud.domain.response;



import lombok.Data;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.util.GetFormatTime;

/**
 * 用户注册API DTO实体类
 *
 * @author vik
 */
@Data
public class RegisterResponseData {
    private long userId;
    private String createAt;
}
