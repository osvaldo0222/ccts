package com.project.ccts.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ccts.model.Credential;
import com.project.ccts.util.Logger;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * This is the filter for the first authentication step with JWT.
 *
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    /**
     * This is the first part of the JWT implementation,
     * where the user sends the credentials and the server validates
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //TODO: check if the request have a notificationToken
            //Mapping the username and password to a Credential object
            Credential authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), Credential.class);

            //Setting authentication parameters
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            //Would make sure that user is in the database and if the password is correct
            Authentication authenticate = null;
            try {
                authenticate = authenticationManager.authenticate(authentication);
            } catch (AuthenticationException e) {
                JwtUtil.getInstance().prepareErrorHandler(response, JwtUtil.getBadCredential(), String.format("Response bad credential to %s", request.getRemoteAddr()), HttpServletResponse.SC_UNAUTHORIZED);
                throw e;
            }

            return authenticate;
        } catch (IOException e) {
            JwtUtil.getInstance().prepareErrorHandler(response, JwtUtil.getCredentialNotFound(), String.format("Someone from %s try to login without JSON request body - %s", request.getRemoteAddr(), e.getMessage()), HttpServletResponse.SC_UNAUTHORIZED);
            throw new AuthenticationCredentialsNotFoundException(JwtUtil.getCredentialNotFound());
        }
    }

    /**
     * On a successful authentication, here the user token is generated after validation
     * and is sent by the header Authorization: Bearer (token)
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //Log for the user auth on console
        Logger.getInstance().getLog(getClass()).info(String.format("%s has logged in", authResult.getName()));

        //Generating the token
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpirationAfterMilliseconds()))
                .signWith(secretKey)
                .compact();
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
    }
}
