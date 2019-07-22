package com.etekcity.cloud.dao;


import com.etekcity.cloud.common.ErrorCode;
import com.etekcity.cloud.domain.User;

/**
 * 用户服务中与MySQL数据库的交互接口类
 *
 * @author vik
 */

public interface AccountService {

    /**
     * 检验账号是否存在，不存在则注册成功
     *
     * @param user
     * @return 错误码或成功
     * @throws exception all kinds of exceptions
     */
    void addAccount(User user) throws Exception;

    /**
     * 校验登录账号是否存在，存在时密码是否正确
     *
     * @param email    账号
     * @param password 密码
     * @return 错误码或成功
     */
    User loginCheck(String email, String password) throws Exception;

    /**
     * 通过id获取信息
     *
     * @param id
     * @return User
     * @throws exception
     */
    User selectById(long id) throws Exception;

    /**
     * 更新用户信息
     *
     * @param nickname
     * @param address
     * @param id
     * @throws exception
     */
    void updateUserInfo(String nickname, String address, long id) throws Exception;

    /**
     * 更新密码
     *
     * @param oldPassword
     * @param newPassword
     * @param id
     * @throws exception
     */
    void updatePwdById(String oldPassword, String newPassword, long id) throws Exception;
}
