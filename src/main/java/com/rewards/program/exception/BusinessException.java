package com.rewards.program.exception;

import com.rewards.program.util.ErrorEnumUtil;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4577853273176571331L;

    private final String codeError;
    private final String mensajeError;
    private final HttpStatus httpStatus;

    public BusinessException(ErrorEnumUtil code, String v, HttpStatus httpStatus) {
        this.mensajeError = String.format(code.getMsg(), v);
        this.codeError = code.getCode();
        this.httpStatus = httpStatus;
    }
    
    public BusinessException(ErrorEnumUtil code, String param1, String param2, HttpStatus httpStatus) {
        this.mensajeError = String.format(code.getMsg(), param1, param2);
        this.codeError = code.getCode();
        this.httpStatus = httpStatus;
    }
    
    public BusinessException(ErrorEnumUtil code, HttpStatus httpStatus) {
        this.mensajeError = code.getMsg();
        this.codeError = code.getCode();
        this.httpStatus = httpStatus;
    }

    public String getCodeError() {
        return codeError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
}
