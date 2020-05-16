package com.example.bookmyshow.auth;

/**
 * Created by aravuri on 27/10/18.
 */
import com.example.bookmyshow.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Value("${disableauth}")
    private boolean disableauth;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
            .antMatchers("/users/signin").permitAll()//
            .antMatchers("/users/signup").permitAll()//
            .antMatchers("/ping").hasAuthority(Role.ROLE_ADMIN.getAuthority())//
            .antMatchers("/api/**/**").permitAll()
            // Disallow everything else..
            .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        //http.exceptionHandling().accessDeniedPage("/login");

        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider, disableauth));

        // Optional, if you want to test the API from a browser
        // http.httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
           .antMatchers("/swagger-resources/**")//
           .antMatchers("/swagger-ui.html")//
           .antMatchers("/configuration/**")//
           .antMatchers("/webjars/**")//
           .antMatchers("/public")
           // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
           .and()
           .ignoring()
           .antMatchers(HttpMethod.OPTIONS)
           .antMatchers("/login/**")
           .antMatchers("/tests/**")
           .antMatchers("/error/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}