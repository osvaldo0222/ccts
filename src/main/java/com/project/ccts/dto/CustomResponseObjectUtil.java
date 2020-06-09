package com.project.ccts.dto;

import org.springframework.http.HttpStatus;

public class CustomResponseObjectUtil {

    public static CustomResponseObjectDTO createResponse(HttpStatus status, Object message) {
        return new CustomResponseObjectDTO(status.value(), status.getReasonPhrase(), message);
    }
}
