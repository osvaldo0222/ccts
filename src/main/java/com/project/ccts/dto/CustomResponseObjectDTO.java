package com.project.ccts.dto;

import java.time.LocalDateTime;

/**
 * Object to make a custom response when something occurred
 *
 */
public class CustomResponseObjectDTO {
    //Status code, same as HTTP for now
    private final Integer status;
    //Reason of the HTTP code
    private final String message;
    //Custom result
    private final Object result;

    public CustomResponseObjectDTO(Integer status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }
}
