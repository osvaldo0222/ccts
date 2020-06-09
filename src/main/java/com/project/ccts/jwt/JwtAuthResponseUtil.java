package com.project.ccts.jwt;

import com.google.gson.Gson;
import com.project.ccts.dto.CustomResponseObjectDTO;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Object containing what is necessary to respond to authorization and authentication errors
 *
 */
public class JwtAuthResponseUtil {
    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    public static String getContentType() {
        return CONTENT_TYPE;
    }

    public static String getCharacterEncoding() {
        return CHARACTER_ENCODING;
    }

    /**
     * This method generates the response with the custom object
     * created for authentication and authorization errors.
     *
     * @param response
     * @param statusCode
     * @param message
     * @throws IOException
     */
    public static void prepareAuthResponse(HttpServletResponse response, HttpStatus statusCode, String message) throws IOException {
        PrintWriter out = response.getWriter();
        response.setStatus(statusCode.value());
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        out.print(new Gson().toJson(new CustomResponseObjectDTO(
                statusCode.value(),
                statusCode.getReasonPhrase(),
                message
        )));
        out.flush();
    }
}
