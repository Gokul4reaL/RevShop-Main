package com.revshop.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JavaAuthenticationFilter {
    private JWTUtil jwtUtil = new JWTUtil();

    // Method to validate JWT
    public boolean validateToken(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            String username = jwtUtil.extractUserId(token);

            if (username != null && jwtUtil.validateToken(token, username)) {
                return true; // Token is valid
            }
        }

        // If validation fails, send an unauthorized response
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Unauthorized");
        return false; // Token is invalid
    }
}
