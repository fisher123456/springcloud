package com.cloud.gateway.exception;

/**
 * @Description: 通用业务异常
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -2920910567088140681L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}