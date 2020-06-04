package com.project.ccts.jwt;

import com.project.ccts.util.Logger;

import javax.servlet.http.HttpServletResponse;


/**
 * Classes that contains the general aspects of the protocol with JWT between the applications.
 *
 */
public class JwtUtil {
    private static JwtUtil jwtUtil;
    private static final String NEED_AUTH = "NEED AUTHENTICATION";
    private static final String BAD_CREDENTIAL = "BAD CREDENTIALS";
    private static final String CREDENTIAL_NOT_FOUND = "CREDENTIALS NOT FOUND";
    private static final String APPLICATION_HEADER = "Application-error";

    private JwtUtil() {}

    public static JwtUtil getInstance() {
        if (jwtUtil == null) {
            jwtUtil = new JwtUtil();
        }
        return jwtUtil;
    }

    public static String getNeedAuth() {
        return NEED_AUTH;
    }

    public static String getBadCredential() {
        return BAD_CREDENTIAL;
    }

    public static String getCredentialNotFound() {
        return CREDENTIAL_NOT_FOUND;
    }

    public static String getApplicationHeader() {
        return APPLICATION_HEADER;
    }

    /**
     * Method that allows setting the response parameters for exceptions caused by JWT.
     *
     * @param response
     * @param errorMessage
     * @param logMessage
     * @param statusCode
     */
    public void prepareErrorHandler(HttpServletResponse response, String errorMessage, String logMessage, Integer statusCode){
        response.setStatus(statusCode);
        response.addHeader(APPLICATION_HEADER, errorMessage);
        Logger.getInstance().getLog(this.getClass()).error(logMessage);
    }
}
