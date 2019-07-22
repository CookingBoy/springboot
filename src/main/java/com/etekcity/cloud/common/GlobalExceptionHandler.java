package com.etekcity.cloud.common;


import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import com.mysql.cj.jdbc.exceptions.SQLError;
import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.etekcity.cloud.common.exception.UserServiceException;
import com.etekcity.cloud.domain.request.RegisterAndLoginRequestData;
import com.etekcity.cloud.domain.response.RegisterResponseData;
import com.etekcity.cloud.domain.response.ResponseData;

/**
 * 全局异常处理
 *
 * @author vik
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的错误码
     * @param exception
     * @return
     */
    @ExceptionHandler(UserServiceException.class)
    @ResponseBody
    public ResponseData<RegisterResponseData> exceptionHandler(UserServiceException exception) {
        log.warn("Exception Occured: {}", exception.getErrorCode().getMsg());
        return new ResponseData(exception.getErrorCode(), new HashMap<>(0));
    }

    /**
     * 处理请求参数缺失或格式不对的错误
     * @param e
     * @return
     */
    @ExceptionHandler({NullPointerException.class, ArrayIndexOutOfBoundsException.class})
    @ResponseBody
    public ResponseData paramExceptionHandler(Exception e) {
        log.warn("Warning: {}", e.getMessage(), e);
        return new ResponseData(ErrorCode.INVALID_REQUEST_PARAM, new HashMap<>(0));
    }

    /**
     * mysql数据库错误
     * MyBatisSystemException 也可以捕捉到
    */
    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    public ResponseData mysqlExceptionHandler(Exception e) {
        log.error("Error Occured: {}", e);
        return new ResponseData(ErrorCode.SERVER_INTERNAL_ERROR, new HashMap<>(0));
    }

    /**
     * redis数据库错误
    */
    @ExceptionHandler({RedisCommandTimeoutException.class, RedisConnectionException.class})
    @ResponseBody
    public ResponseData redisExceptionHandler(Exception e) {
        log.error("Error Occured: ", e);
        return new ResponseData(ErrorCode.SERVER_INTERNAL_ERROR, new HashMap<>(0));
    }
}
