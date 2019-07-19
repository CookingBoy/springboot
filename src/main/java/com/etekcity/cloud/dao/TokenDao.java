package com.etekcity.cloud.dao;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.etekcity.cloud.common.ErrorCode;
import com.etekcity.cloud.common.exception.UserServiceException;

/**
 * Token管理DAO
 *
 * @author vik
 */
@Repository
public class TokenDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final long REDIS_EXPIRES_IN = 172800;
    private static final long MAX_TOKEN_LIST_NUMBER = 6;

    /**
     * 存入token
     *
     * @param token
     * @param id
     */
    public void addToken(String token, long id) {
        String tokenListValue = "vik:user:id:" + id + ":token";
        String tokenStringValue = "vik:user:token:" + token;
        //先把token存进去，再判断token list里面token的数量
        stringRedisTemplate.opsForValue().set(tokenStringValue, (id + ""), REDIS_EXPIRES_IN, TimeUnit.SECONDS);
        stringRedisTemplate.opsForList().leftPush(tokenListValue, token);
        long tokenNumber = stringRedisTemplate.opsForList().size(tokenListValue);
        //删除最早生成的token,并发考虑用while而不用if
        while (tokenNumber >= MAX_TOKEN_LIST_NUMBER) {
            String deleteEarliestToken = stringRedisTemplate.opsForList().rightPop(tokenListValue);
            stringRedisTemplate.delete("vik:user:token:" + deleteEarliestToken);
            tokenNumber = stringRedisTemplate.opsForList().size(tokenListValue);
        }
    }

    /**
     * 校验token
     *
     * @param token
     * @param id
     * @throws Exception
     */
    public String tokenCheck(String authorization) throws Exception {
        //获取token和id
        String[] tokenAndId = authorization.split(" +");
        String token = tokenAndId[0];
        String id = tokenAndId[1];
        //构造key
        String tokenStringValue = "vik:user:token:" + token;
        //查询token是否为空,为空时返回null
        String tokenAlreadyExist = stringRedisTemplate.opsForValue().get(tokenStringValue);
        //检查是否还在有效期和id与token是否对应
        if (tokenAlreadyExist == null) {
            throw new UserServiceException(ErrorCode.TOKEN_INVALIDE_ERROR);
        }
        if (!id.equals(tokenAlreadyExist)) {
            throw new UserServiceException(ErrorCode.TOKEN_ERROR);
        }
        return id;
    }

    /**
     * 删除单个token
     *
     * @param authorization
     * @throws Exception
     */
    public void deleteOneToken(String authorization) throws Exception {
        //获取token和id
        String[] tokenAndId = authorization.split(" ");
        String token = tokenAndId[0];
        String id = tokenAndId[1];
        //构造key
        String tokenListValue = "vik:user:id:" + id + ":token";
        String tokenStringValue = "vik:user:token:" + token;
        //删除指定token
        stringRedisTemplate.opsForList().remove(tokenListValue, 1, token);
        stringRedisTemplate.delete(tokenStringValue);
    }

    /**
     * 删除单个用户的所有token
     *
     * @param authorization
     * @throws Exception
     */
    public void deleteAllToken(String authorization) throws Exception {
        //获取token和id
        String[] tokenAndId = authorization.split(" ");
        String token = tokenAndId[0];
        String id = tokenAndId[1];
        //构造key
        String tokenListValue = "vik:user:id:" + id + ":token";
        String tokenStringValue = "vik:user:token:" + token;
        //删除该用户之前的所有token,
        List<String> allToken = stringRedisTemplate.opsForList().range(tokenListValue, 0, 4);
        for (String t : allToken) {
            stringRedisTemplate.delete("vik:user:token:" + t);
        }
        stringRedisTemplate.delete(tokenListValue);
    }


}
