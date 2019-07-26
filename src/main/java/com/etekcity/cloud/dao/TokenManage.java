package com.etekcity.cloud.dao;


import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.lang.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.etekcity.cloud.common.ErrorCode;
import com.etekcity.cloud.common.exception.UserServiceException;
import com.etekcity.cloud.common.Constant;

/**
 * Token管理DAO
 *
 * @author vik
 */
@Repository
public class TokenManage {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存入token
     *
     * @param token
     * @param id
     */
    public void addToken(String token, long id) {
        String tokenListValue = getTokenListKey(String.valueOf(id));
        String tokenStringValue = getTokenStringKey(token);
        // 先把token存进去，再判断token list里面token的数量,不会造成NullPointerException
        stringRedisTemplate.opsForValue().set(tokenStringValue, (id + ""), Constant.REDIS_EXPIRES_IN, TimeUnit.SECONDS);
        stringRedisTemplate.opsForList().leftPush(tokenListValue, token);
        long tokenNumber = stringRedisTemplate.opsForList().size(tokenListValue);
        // 删除最早生成的token,并发考虑用while而不用if
        if (tokenNumber > Constant.MAX_TOKEN_LIST_NUMBER) {
            List<String> deletedTokens = stringRedisTemplate.opsForList().range(tokenListValue, 5, 10);
            for (String t : deletedTokens) {
                stringRedisTemplate.opsForList().remove(tokenListValue, 1, t);
                stringRedisTemplate.delete("vik:user:token:" + t);
            }
        }
    }

    /**
     * 校验token
     *
     * @param token
     * @param id
     * @throws Exception
     */
    public String checkToken(String authorization) throws Exception {
        // 获取token和id
        HashMap<String, String> tokenAndIdMap = getInfoFromAuth(authorization);
        String token = tokenAndIdMap.get("token");
        String id = tokenAndIdMap.get("id");
        // 构造key
        String tokenStringValue = getTokenStringKey(token);
        // 查询token是否为空,为空时返回null
        String tokenAlreadyExist = stringRedisTemplate.opsForValue().get(tokenStringValue);
        // 检查是否还在有效期和id与token是否对应
        if (tokenAlreadyExist == null) {
            throw new UserServiceException(ErrorCode.TOKEN_INVALID_ERROR);
        }
        if (!tokenAlreadyExist.equals(id)) {
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
        // 获取token和id
        HashMap<String, String> tokenAndIdMap = getInfoFromAuth(authorization);
        String token = tokenAndIdMap.get("token");
        String id = tokenAndIdMap.get("id");
        // 构造key
        String tokenListValue = getTokenListKey(id);
        String tokenStringValue = getTokenStringKey(token);
        // 删除指定token
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
        // 获取token和id
        HashMap<String, String> tokenAndIdMap = getInfoFromAuth(authorization);
        String id = tokenAndIdMap.get("id");
        // 构造key
        String tokenListValue = getTokenListKey(id);
        // 删除该用户之前的所有token,
        List<String> allToken = stringRedisTemplate.opsForList().range(tokenListValue, 0, 4);
        for (String t : allToken) {
            stringRedisTemplate.delete(getTokenStringKey(t));
        }
        stringRedisTemplate.delete(tokenListValue);
    }

    /**
     * 根据用户id，生成token list的key
     *
     * @param id
     * @return
     */
    private String getTokenListKey(String id) {
        return "vik:user:id:" + id + ":token";
    }

    /**
     * 根据token，生成token string的key
     *
     * @param token
     * @return
     */
    private String getTokenStringKey(String token) {
        return "vik:user:token:" + token;
    }

    /**
     * 从鉴权信息中解析token和id
     *
     * @param authorization
     * @return
     */
    private HashMap<String, String> getInfoFromAuth(String authorization) throws Exception{
        String[] tokenAndId = authorization.split(" +");
        if (tokenAndId.length != Constant.AUTHORIZATION_LIST_LENGTH) {
            throw new UserServiceException(ErrorCode.INVALID_REQUEST_PARAM);
        }
        HashMap<String, String> tokenAndIdMap = new HashMap<>(2);
        tokenAndIdMap.put("token", tokenAndId[1]);
        tokenAndIdMap.put("id", tokenAndId[0]);
        return tokenAndIdMap;
    }
}
