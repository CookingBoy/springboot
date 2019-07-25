package com.etekcity.cloud.common;


import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonSyntaxException;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etekcity.cloud.common.exception.UserServiceException;
import com.etekcity.cloud.domain.response.ResponseData;

/**
 * 全局异常处理
 *
 * @author vik
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 处理用户服务错误
     *
     * @param UserServiceException
     * @return ResponseData
     */
    @ExceptionHandler(UserServiceException.class)
    public ResponseData exceptionHandler(UserServiceException exception) {
        log.info("UserService Exception Occured: {}", exception.getErrorCode().getMsg());
        return new ResponseData(exception.getErrorCode(), new Object());
    }

    /**
     * 处理请求参数缺失或格式不对的错误
     *
     * @param
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData paramExceptionHandler(MethodArgumentNotValidException e) {
        log.info("Request Param Exception Occured: ", e);
        return new ResponseData(ErrorCode.INVALID_REQUEST_PARAM, new Object());
    }

    /**
     * mysql与Redis连接数据库错误
     * 也可捕捉MyBatisSystemException错误(MySQL)
     */
    @ExceptionHandler({PersistenceException.class,RedisConnectionException.class})
    public ResponseData mysqlExceptionHandler(Exception e) {
        log.error("DataBase Error Occured: ", e);
        return new ResponseData(ErrorCode.SERVER_INTERNAL_ERROR, new Object());
    }

    /**
     * redis数据库连接超时错误
     */
    @ExceptionHandler(RedisCommandTimeoutException.class)
    public ResponseData redisExceptionHandler(RedisCommandTimeoutException e) {
        log.error("Redis Error Occured: ", e);
        return new ResponseData(ErrorCode.SERVER_TIMEOUT, new Object());
    }

    /**
     * request json格式错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(JsonSyntaxException.class)
    public ResponseData jsonExceptionHandler(JsonSyntaxException e) {
        log.info("Request Json Exception Occured: ", e);
        return new ResponseData(ErrorCode.JSON_PARSE_ERROR, new Object());
    }
}
