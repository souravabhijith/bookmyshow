package com.example.bookmyshow;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by aravuri on 25/03/19.
 */
public class RequestLogger extends CommonsRequestLoggingFilter {


    public RequestLogger() {
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return this.logger.isDebugEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        this.logger.debug(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        //SKIP
    }
}
