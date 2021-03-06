package com.project.ccts.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ccts.model.entities.Credential;
import com.project.ccts.model.entities.NodeCredential;
import com.project.ccts.model.entities.UserCredential;
import com.project.ccts.service.CredentialService;
import com.project.ccts.model.enums.NodeStatus;
import com.project.ccts.util.logger.Logger;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
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
    private final CredentialService credentialService;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey, CredentialService credentialService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.credentialService = credentialService;
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
            //Mapping the username and password to a Credential object
            Credential authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), Credential.class);

            //Setting authentication parameters q
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername().trim(),
                    authenticationRequest.getPassword()
            );

            //Would make sure that user is in the database and if the password is correct
            return authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            //Logging the exception
            Logger.getInstance().getLog(getClass()).warn(String.format("User from %s try to login to the system, but failed - Exception message: %s", request.getRemoteAddr(), e.getMessage()));

            //When the object JSON credentials is null or username or password incorrect
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
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

        //Updating the user if is from mobile or it's a node
        Credential user = credentialService.findByUsername(authResult.getName());

        String name = authResult.getName();

        if (user instanceof UserCredential) {
            response.addHeader("uuid", ((UserCredential) user).getUuid().toString());
            String notificationToken = request.getHeader("NotificationToken");
            name = ((UserCredential) user).getPerson().getFirstName() + " " + ((UserCredential) user).getPerson().getLastName();

            if (notificationToken != null && !notificationToken.equalsIgnoreCase("")) {
                credentialService.addNotificationToken(user, notificationToken);
                Logger.getInstance().getLog(getClass()).info(String.format("%s has a notification token %s", authResult.getName(), notificationToken));
            }
        }

        credentialService.createOrUpdate(user);

        //Generating the token
        String token = Jwts.builder()
                .setIssuer(jwtConfig.getIssuer())
                .setSubject(authResult.getName())
                .claim("name", name)
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpirationAfterMilliseconds()))
                .signWith(secretKey)
                .compact();

        //Adding token to the response

        JwtAuthResponseUtil.prepareAuthResponse(response, HttpStatus.OK, jwtConfig.getTokenPrefix() + token);
    }
}
