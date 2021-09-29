package com.rewards.program.model;

import java.io.Serializable;

public class SubErrors implements Serializable {
	
	private static final long serialVersionUID = 1479633998531849623L;
	
	private String field;
    private String message;
    private transient Object rejectedValue;

    public SubErrors(String field, String message, Object rejectedValue) {
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
}
