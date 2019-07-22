package com.etekcity.cloud.domain.response;


import lombok.Data;

import com.etekcity.cloud.common.ErrorCode;

/**
 * API Response请求体实体类
 * @author vik
 */
@Data
public class ResponseData<T> {

    private int code;

    private String msg;

    private T result;

    public ResponseData(ErrorCode errorCode, T result) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseData{" + "code=" + code + ", msg='" + msg + '\'' + ", result="
                + result + '}';

    }

}
