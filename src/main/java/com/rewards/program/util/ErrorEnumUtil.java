package com.rewards.program.util;

public enum ErrorEnumUtil {
    

    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND", "Customer doesn't exist [%s]."),
    TRANSACTION_NOT_FOUND("TRANSACTION_NOT_FOUND", "Transaction doesn't exist [%s]"),
    DATE_FORMAT_ERROR("DATE_FORMAT_ERROR", "Date format error [%s]");

    private final String code;
    private final String msg;

    ErrorEnumUtil(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
