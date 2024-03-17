package com.seulah.appdesign.filter;

import com.seulah.appdesign.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {


    private final JwtUtils jwtUtils;
    @Value("${tns.app.jwtSecret}")
    private String jwtSecret;
    public AuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt != null) {
            if(extractRoleFromToken(jwt).equals("ROLE_USER")){
                filterChain.doFilter(request,response);
            }else if(extractRoleFromToken(jwt).equals("ROLE_ADMIN")){
                filterChain.doFilter(request,response);
            }
            else {
                throw new ServletException("Insufficient permissions");
            }
        }else {
            throw new ServletException("Invalid token: "+request.getHeader("Authorization"));
        }
    }

    private String extractRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("roles", String.class); // Ensure the key "roles" is correct
        } catch (JwtException e) {
            // Log the exception for debugging
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error("Error parsing JWT: {}", e.getMessage());
            return null;
        }// Assuming the role is stored in a claim named 'role'
    }
}
