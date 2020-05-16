package com.example.bookmyshow.auth;


import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by aravuri on 27/10/18.
 */
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;
    private boolean disableauth;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider, boolean disableauth) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.disableauth = disableauth;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtAuthFilter customFilter = new JwtAuthFilter(jwtTokenProvider, disableauth);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}