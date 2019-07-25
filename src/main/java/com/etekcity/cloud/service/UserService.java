package com.etekcity.cloud.service;


import com.etekcity.cloud.domain.request.RegisterAndLoginRequestData;
import com.etekcity.cloud.domain.request.UpdatePasswordRequestData;
import com.etekcity.cloud.domain.request.UpdateUserInfoRequestData;
import com.etekcity.cloud.domain.response.ResponseData;


/**
 * 用户业务逻辑接口类
 *
 * @author vik
 */
public interface UserService {

    /**
     * 用户注册接口
     *
     * @param ralData 实体类
     */
    ResponseData register(RegisterAndLoginRequestData ralData) throws Exception;

    /**
     * 用户登录接口
     *
     * @param ralData 实体类
     */
    ResponseData login(RegisterAndLoginRequestData ralData) throws Exception;

    /**
     * 用户登出接口
     */
    ResponseData logout(String authorization) throws Exception;

    /**
     * 获取用户信息接口
     */
    ResponseData getUserInfo(String authorization) throws Exception;

    /**
     * 更新用户信息
     */
    ResponseData updateUserInfo(UpdateUserInfoRequestData updateUserInfoRequestData,
                                String authorization) throws Exception;

    /**
     * 修改密码
     */
    ResponseData updatePassword(UpdatePasswordRequestData updatePasswordRequestData,
                                String authorization) throws Exception;
}
