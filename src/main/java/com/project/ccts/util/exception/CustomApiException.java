package com.project.ccts.util.exception;

public class CustomApiException extends Exception {
    private Integer code;

    public CustomApiException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
