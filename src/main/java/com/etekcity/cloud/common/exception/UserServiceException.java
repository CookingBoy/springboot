package com.etekcity.cloud.common.exception;


import lombok.Data;
import com.etekcity.cloud.common.ErrorCode;

/**
 * 自定的统一异常类，处理所有错误码
 *
 * @author vik
 */
@Data
public class UserServiceException extends Exception {

    private ErrorCode errorCode;

    public UserServiceException(ErrorCode e) {
        errorCode = e;
    }
}
