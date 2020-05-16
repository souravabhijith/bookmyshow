package com.example.bookmyshow.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthFilter extends GenericFilterBean {

    private boolean disableauth;

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider,
                         Boolean disableauth) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.disableauth = disableauth;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        if (!disableauth) {
            try {
                String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
                if (token != null) {
                    Jws<Claims> claimsJws = jwtTokenProvider.validateAndExtractClaims(token);
                    if (claimsJws != null) {
                        Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token,
                                                                                                 claimsJws) : null;
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        throw new RuntimeException("JWT Token parsing error, claims cant be null");
                    }
                }
            } catch (Exception ex) {
                HttpServletResponse response = (HttpServletResponse) res;
                response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

}