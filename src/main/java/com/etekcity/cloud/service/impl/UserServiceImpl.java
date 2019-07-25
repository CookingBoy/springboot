package com.etekcity.cloud.service.impl;


import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.etekcity.cloud.common.ErrorCode;
import com.etekcity.cloud.common.exception.UserServiceException;
import com.etekcity.cloud.dao.TokenManage;
import com.etekcity.cloud.dao.mapper.UserMapper;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.domain.mapstruct.UserConverter;
import com.etekcity.cloud.domain.request.RegisterAndLoginRequestData;
import com.etekcity.cloud.domain.request.UpdatePasswordRequestData;
import com.etekcity.cloud.domain.request.UpdateUserInfoRequestData;
import com.etekcity.cloud.domain.response.GetUserInfoResponseData;
import com.etekcity.cloud.domain.response.LoginResponseData;
import com.etekcity.cloud.domain.response.RegisterResponseData;
import com.etekcity.cloud.domain.response.ResponseData;
import com.etekcity.cloud.service.UserService;
import com.etekcity.cloud.util.CheckUtils;
import com.etekcity.cloud.util.GetFormatTime;
import com.etekcity.cloud.util.HashWithSalt;


/**
 * 用户业务逻辑实现类
 *
 * @author vik
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private TokenManage tokenManage;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseData<RegisterResponseData> register(RegisterAndLoginRequestData ralData) throws Exception {
        log.info("Register request received: {} ", ralData);
        // 参数校验
        CheckUtils.checkEmailFormat(ralData.getEmail());
        CheckUtils.checkPasswordFormat(ralData.getPassword(), "pwd");
        // 判断邮箱是否已存在
        User userInDb = userMapper.selectByEmail(ralData.getEmail());
        if (userInDb != null) {
            throw new UserServiceException(ErrorCode.EMAIL_ALREADY_EXIST_ERROR);
        }
        // 产生一个20位的随机盐值
        String salt = RandomStringUtils.randomAlphanumeric(20);
        // 对密码加盐哈希
        String hashPassword = HashWithSalt.getMd5String(ralData.getPassword() + salt);
        String createAt = GetFormatTime.getTime();
        // 构造User对象
        User user = new User();
        user.setEmail(ralData.getEmail());
        user.setPassword(hashPassword);
        user.setCreateAt(createAt);
        user.setUpdateAt(createAt);
        user.setSalt(salt);
        // 写入数据库，try增加并发验证
        try {
            userMapper.addAcount(user);
        } catch (DuplicateKeyException e) {
            throw new UserServiceException(ErrorCode.EMAIL_ALREADY_EXIST_ERROR);
        }
        // 构造返回结果
        RegisterResponseData registerResponseData = UserConverter.INSTANCE.registerDTO(user);
        log.info("Register request received: {}. resp: {}", ralData, registerResponseData);
        return new ResponseData<>(ErrorCode.SUCCESS, registerResponseData);
    }

    @Override
    public ResponseData<LoginResponseData> login(RegisterAndLoginRequestData ralData) throws Exception {
        log.info("Login request received: {} ", ralData);
        // 参数校验
        CheckUtils.checkEmailFormat(ralData.getEmail());
        CheckUtils.checkPasswordFormat(ralData.getPassword(), "pwd");
        // 账号校验
        User user = userMapper.selectByEmail(ralData.getEmail());
        if (user == null) {
            throw new UserServiceException(ErrorCode.EMAIL_NOT_EXIST_ERROR);
        }
        // 密码校验
        String hashEnteredPassword = HashWithSalt.getMd5String(ralData.getPassword() + user.getSalt());
        if (!user.getPassword().equals(hashEnteredPassword)) {
            throw new UserServiceException(ErrorCode.PASSWORD_ERROR);
        }
        // 生成不重复的token并写入redis(调用函数)
        String token = UUID.randomUUID().toString();
        tokenManage.addToken(token, user.getId());
        // 构造返回result
        LoginResponseData loginResponseData = UserConverter.INSTANCE.loginDTO(user);
        loginResponseData.setToken(token);
        log.info("Login request received: {}. resp: {}", ralData, loginResponseData);
        return new ResponseData<>(ErrorCode.SUCCESS, loginResponseData);
    }

    @Override
    public ResponseData logout(String authorization) throws Exception {
        // 校验token
        tokenManage.checkToken(authorization);
        // 删除token
        tokenManage.deleteOneToken(authorization);
        return new ResponseData(ErrorCode.SUCCESS, new Object());
    }

    @Override
    public ResponseData<GetUserInfoResponseData> getUserInfo(String authorization) throws Exception {
        // 校验token
        String id = tokenManage.checkToken(authorization);
        // 根据id查信息
        User user = userMapper.selectById(Long.parseLong(id));
        // 构造返回结果
        GetUserInfoResponseData getUserInfoResponseData = UserConverter.INSTANCE.getUserInfoDTO(user);
        return new ResponseData<>(ErrorCode.SUCCESS, getUserInfoResponseData);
    }

    @Override
    public ResponseData updateUserInfo(UpdateUserInfoRequestData updateUserInfoRequestData,
                                       String authorization) throws Exception {
        // 参数校验
        CheckUtils.checkNicknameFormat(updateUserInfoRequestData.getNickname());
        CheckUtils.checkAddressFormat(updateUserInfoRequestData.getAddress());
        // token校验
        long id = Long.parseLong(tokenManage.checkToken(authorization));
        // 根据id更新信息
        if (updateUserInfoRequestData.getNickname() != null) {
            userMapper.updateNicknameById(updateUserInfoRequestData.getNickname(), id);
        }
        if (updateUserInfoRequestData.getAddress() != null) {
            userMapper.updateAddressById(updateUserInfoRequestData.getAddress(), id);
        }
        return new ResponseData(ErrorCode.SUCCESS, new Object());
    }

    @Override
    public ResponseData updatePassword(UpdatePasswordRequestData updatePasswordRequestData,
                                       String authorization) throws Exception {
        // 参数校验
        CheckUtils.checkPasswordFormat(updatePasswordRequestData.getOldPassword(), "oldPwd");
        CheckUtils.checkPasswordFormat(updatePasswordRequestData.getNewPassword(), "newPwd");
        // token校验
        long id = Long.parseLong(tokenManage.checkToken(authorization));
        // 根据id修改密码
        User user = userMapper.selectById(id);
        String hashEnteredPassword = HashWithSalt.getMd5String(updatePasswordRequestData.getOldPassword()
                + user.getSalt());
        // 这里由于有之前的参数校验，所以不为空
        if (!hashEnteredPassword.equals(user.getPassword())) {
            throw new UserServiceException(ErrorCode.OLD_PASSWORD_ERROR);
        }
        // 重新生成盐值,并将数据写入数据库
        String newSalt = RandomStringUtils.randomAlphanumeric(20);
        String hashPassword = HashWithSalt.getMd5String(updatePasswordRequestData.getNewPassword() + newSalt);
        userMapper.updatePwdById(hashPassword, newSalt, id);
        // 删掉以前的所有token
        tokenManage.deleteAllToken(authorization);
        return new ResponseData(ErrorCode.SUCCESS, new Object());
    }
}
