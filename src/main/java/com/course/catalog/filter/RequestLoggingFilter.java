package com.course.catalog.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class RequestLoggingFilter implements Filter {

    private static final long SLOW_THRESHOLD_MS = 50;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long start = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;
        System.out.println(req.getMethod() + " " + req.getRequestURI() + " - " + duration + "ms");

        if (duration > SLOW_THRESHOLD_MS) {
            System.out.println("WARNING: slow request (" + duration + "ms > "
                    + SLOW_THRESHOLD_MS + "ms): " + req.getRequestURI());
        }
    }
}