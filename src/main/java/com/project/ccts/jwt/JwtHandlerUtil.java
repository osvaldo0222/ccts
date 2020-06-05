package com.project.ccts.jwt;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Object containing what is necessary to respond to authorization and authentication errors
 *
 */
public class JwtHandlerUtil {
    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String BAD_CREDENTIAL = "Bad credentials";
    private static final String FORBIDDEN = "Forbidden";

    public static String getContentType() {
        return CONTENT_TYPE;
    }

    public static String getCharacterEncoding() {
        return CHARACTER_ENCODING;
    }

    public static String getBadCredential() {
        return BAD_CREDENTIAL;
    }

    public static String getFORBIDDEN() {
        return FORBIDDEN;
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
    public static void prepareResponse(HttpServletResponse response, Integer statusCode, String message) throws IOException {
        PrintWriter out = response.getWriter();
        response.setStatus(statusCode);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        out.print(new Gson().toJson(new JwtCustomObjectErrorResponse(
                statusCode,
                message
        )));
        out.flush();
    }
}
