package com.etekcity.cloud.service.impl;


import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.xml.ws.handler.LogicalHandler;

import lombok.Builder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.EscapedErrors;
import org.springframework.web.bind.annotation.RequestBody;
import com.etekcity.cloud.common.ErrorCode;
import com.etekcity.cloud.dao.TokenDao;
import com.etekcity.cloud.dao.mapper.UserMapper;
import com.etekcity.cloud.domain.User;
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
import com.etekcity.cloud.dao.AccountService;


/**
 * 用户业务逻辑实现类
 *
 * @author vik
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenDao tokenDao;

    @Override
    public ResponseData<RegisterResponseData> register(RegisterAndLoginRequestData ralData) throws Exception {

        //参数校验
        CheckUtils.emailFormatCheck(ralData.getEmail());
        CheckUtils.passwordFormatCheck(ralData.getPassword(), "pwd");
        //读取数据，写入数据库
        User user = new User();
        user.setEmail(ralData.getEmail());
        user.setPassword(ralData.getPassword());
        user.setCreateAt(GetFormatTime.getTime());
        user.setUpdateAt(user.getCreateAt());
        accountService.addAccount(user);
        //构造返回结果
        RegisterResponseData registerResponseData = new RegisterResponseData(user);
        return new ResponseData<>(ErrorCode.SUCCESS, registerResponseData);


    }

    @Override
    public ResponseData<LoginResponseData> login(RegisterAndLoginRequestData ralData) throws Exception {
        //参数校验
        CheckUtils.emailFormatCheck(ralData.getEmail());
        CheckUtils.passwordFormatCheck(ralData.getPassword(), "pwd");
        //账号密码合法性校验
        User user = accountService.loginCheck(ralData.getEmail(), ralData.getPassword());
        //构造返回result
        LoginResponseData loginResponseData = new LoginResponseData(user);
        //生成token并写入redis(调用函数)，生成不重复的token
        String token = UUID.randomUUID().toString();
        tokenDao.addToken(token, loginResponseData.getId());
        loginResponseData.setToken(token);
        return new ResponseData<>(ErrorCode.SUCCESS, loginResponseData);
    }

    @Override
    public ResponseData logout(String authorization) throws Exception {
        //校验token
        tokenDao.tokenCheck(authorization);
        //删除token
        tokenDao.deleteOneToken(authorization);
        return new ResponseData(ErrorCode.SUCCESS, new HashMap<>(0));
    }

    @Override
    public ResponseData<GetUserInfoResponseData> getUserInfo(String authorization) throws Exception {
        //校验token
        String id = tokenDao.tokenCheck(authorization);
        //根据id查信息
        User user = accountService.selectById(Long.parseLong(id));
        //构造返回结果
        GetUserInfoResponseData getUserInfoResponseData = new GetUserInfoResponseData(user);
        return new ResponseData<>(ErrorCode.SUCCESS, getUserInfoResponseData);
    }

    @Override
    public ResponseData updateUserInfo(UpdateUserInfoRequestData updateUserInfoRequestData,
                                       String authorization) throws Exception {
        //参数校验
        CheckUtils.nicknameFormatCheck(updateUserInfoRequestData.getNickname());
        CheckUtils.addressFormatCheck(updateUserInfoRequestData.getAddress());
        //token校验
        String id = tokenDao.tokenCheck(authorization);
        //根据id更新信息
        accountService.updateUserInfo(updateUserInfoRequestData.getNickname(), updateUserInfoRequestData.getAddress(),
                Long.parseLong(id));
        return new ResponseData(ErrorCode.SUCCESS, new HashMap<>(0));
    }

    @Override
    public ResponseData updatePassword(UpdatePasswordRequestData updatePasswordRequestData,
                                       String authorization) throws Exception {
        //参数校验
        CheckUtils.passwordFormatCheck(updatePasswordRequestData.getOldPassword(), "oldPwd");
        CheckUtils.passwordFormatCheck(updatePasswordRequestData.getNewPassword(), "newPwd");
        //token校验
        String id = tokenDao.tokenCheck(authorization);
        //根据id修改密码
        accountService.updatePwdById(updatePasswordRequestData.getOldPassword(),
                updatePasswordRequestData.getNewPassword(), Long.parseLong(id));
        //删掉以前的所有token
        tokenDao.deleteAllToken(authorization);
        return new ResponseData(ErrorCode.SUCCESS, new HashMap<>(0));
    }
}
