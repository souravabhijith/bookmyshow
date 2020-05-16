package com.example.bookmyshow.auth;

/**
 * Created by aravuri on 27/10/18.
 */

import com.example.bookmyshow.entities.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final String ROLES_JWT_KEY = "roles";

    ObjectMapper objectMapper = new ObjectMapper();
    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices environment, this key would be kept on a config-server.
     */
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<Role> roles,
                              Date now,
                              Date expiry) {

        Claims claims = Jwts.claims().setSubject(username);
        StringBuilder claimsStr = new StringBuilder();
        for (int i=0;i<roles.size();i++) {
            Role role = roles.get(i);
            if (role != null) {
                claimsStr.append(role.getAuthority());
            }
            if (i!=(roles.size()-1)) {
                claimsStr.append(",");
            }
        }
        claims.put(ROLES_JWT_KEY, claimsStr.toString());


        return Jwts.builder()//
                   .setClaims(claims)//
                   .setIssuedAt(now)//
                   .setExpiration(expiry)//
                   .signWith(SignatureAlgorithm.HS256, secretKey)//
                   .compact();

    }

    public Authentication getAuthentication(String token, Jws<Claims> claims)
            throws IOException {

        String[] roles = claims.getBody().get(ROLES_JWT_KEY).toString().split(",");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (int i=0;i<roles.length;i++) {
            authorities.add(new SimpleGrantedAuthority(roles[i]));
        }
        UserDetails userDetails = User.withUsername((getUsername(token)))
                                      .password("")
                                      .authorities(authorities)
                                      .accountExpired(false)
                                      .accountLocked(false)
                                      .credentialsExpired(false)
                                      .disabled(false)//
                                      .build();
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req)
            throws AuthException {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        throw new AuthException("Stalker Alert, Use this site in the way it is meant to be");
    }

    public Jws<Claims> validateAndExtractClaims(String token) throws AuthException {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            throw new AuthException(ex.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException("Invalid JWT token, Message:" + e.getMessage());
        }
    }

}