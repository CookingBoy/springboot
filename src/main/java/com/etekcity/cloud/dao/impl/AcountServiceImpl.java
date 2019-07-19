package com.etekcity.cloud.dao.impl;

import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.etekcity.cloud.common.ErrorCode;
import com.etekcity.cloud.common.exception.UserServiceException;
import com.etekcity.cloud.dao.AccountService;
import com.etekcity.cloud.dao.mapper.UserMapper;
import com.etekcity.cloud.domain.User;
import com.etekcity.cloud.util.HashWithSalt;

/**
 * 用户服务中与MySQL数据库的交互逻辑实现类
 *
 * @author vik
 */
@Service
public class AcountServiceImpl implements AccountService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void addAccount(User user) throws Exception {
        User userInDb = userMapper.selectByEmail(user.getEmail());
        //判断邮箱是否已存在
        if (userInDb != null) {
            throw new UserServiceException(ErrorCode.EMAIL_ALREADY_EXIST_ERROR);
        }
        //产生一个20位的随机盐值
        user.setSalt(RandomStringUtils.randomAlphanumeric(20));
        //对密码加盐哈希
        String hashPassword = HashWithSalt.getMD5String(user.getPassword() + user.getSalt());
        user.setPassword(hashPassword);
        //并发验证，返回错误码
        try {
            userMapper.addAcount(user);
        }
        catch (DuplicateKeyException e) {
            throw new UserServiceException(ErrorCode.SERVER_BUSY);
        }
    }

    @Override
    public User loginCheck(String email, String password) throws Exception {
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            throw new UserServiceException(ErrorCode.EMAIL_NOT_EXIST_ERROR);
        }
        //将数据库中的密码和盐值一起哈希
        String enteredPassword = HashWithSalt.getMD5String(password + user.getSalt());
        if (!user.getPassword().equals(enteredPassword)) {
            throw new UserServiceException(ErrorCode.PASSWORD_ERROR);
        }
        return user;

    }

    @Override
    public User selectById(long id) throws Exception {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public void updateUserInfo(String nickname, String address, long id) throws Exception {
        if (nickname != null) {
            userMapper.updateNicknameById(nickname, id);
        }
        if (address != null) {
            userMapper.updateAddressById(address, id);
        }
    }

    @Override
    public void updatePwdById(String oldPassword, String newPassword, long id) throws Exception {
        User user = userMapper.selectById(id);
        String enteredPassword = HashWithSalt.getMD5String(oldPassword + user.getSalt());
        //由于有密码格式校验，所以这里输入的不为空
        if (!enteredPassword.equals(user.getPassword())) {
            throw new UserServiceException(ErrorCode.OLD_PASSWORD_ERROR);
        }
        //重新生成盐值
        String newSalt = RandomStringUtils.randomAlphanumeric(20);
        String hashPassword = HashWithSalt.getMD5String(newPassword + newSalt);
        userMapper.updatePwdById(hashPassword, newSalt, id);
    }
}
