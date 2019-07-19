package com.etekcity.cloud.common;


import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

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
public class GlobalExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    @ResponseBody
    public ResponseData<RegisterResponseData> exceptionHandler(UserServiceException exception) {
        return new ResponseData(exception.getErrorCode(), new HashMap<>(0));
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResponseData sqlExceptionHandler() {
        return new ResponseData(ErrorCode.SERVER_INTERNAL_ERROR, new HashMap<>(0));
    }
}
