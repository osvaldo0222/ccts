package com.project.ccts.jwt;

import com.google.common.base.Strings;
import com.project.ccts.service.security.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Filter for each request, to validate the token of the users or nodes.
 *
 */
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final SecurityService securityService;

    public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig, SecurityService securityService) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.securityService = securityService;
    }

    /**
     * This function executes the validation of the token sent by the user
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Get the authorization header
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        //Check if the authorization header is there and if it have Bearer
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            if (!request.getServletPath().startsWith("/public")) {
                JwtUtil.getInstance().prepareErrorHandler(response, JwtUtil.getNeedAuth(), "Token not specified!!", HttpServletResponse.SC_UNAUTHORIZED);
            }
            filterChain.doFilter(request, response);
            return;
        }

        //Getting the token
        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

        try {
            //Signature check and build
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            //Getting all claims of the token
            Claims body = claimsJws.getBody();

            //Getting onl the username
            String username = body.getSubject();

            //Getting privileges from the DB
            Collection<? extends GrantedAuthority> authoritySet = securityService.getAuthorities(username);

            //Making and authentication context
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authoritySet
            );

            //Setting the new authentication to the context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            JwtUtil.getInstance().prepareErrorHandler(response, JwtUtil.getNeedAuth(), String.format("Token %s cannot be trusted!!", token), HttpServletResponse.SC_UNAUTHORIZED);
        }

        //To proceed to the next filter or the api
        filterChain.doFilter(request, response);
    }
}