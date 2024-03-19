package com.seulah.appdesign.filter;

import com.seulah.appdesign.exceptions.CustomAuthenticationException;
import com.seulah.appdesign.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
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
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/api/v1/cms/sms")) {
            filterChain.doFilter(request, response);
        }
        if (requestURI.equals("/api/v1/cms/esign/emad")) {
            filterChain.doFilter(request, response);
        }
        if(requestURI.equals("/api/v1/cms/screenFlow/getAppFlow")){
            filterChain.doFilter(request, response);
        }
        if(requestURI.equals("/api/v1/cms/brandSplashScreen/")){
            filterChain.doFilter(request, response);
        }
        if (jwt != null) {
            if("ROLE_USER".equals(extractRoleFromToken(jwt))){
                filterChain.doFilter(request, response);
            } else if("ROLE_ADMIN".equals(extractRoleFromToken(jwt))){
                filterChain.doFilter(request, response);
            }
            else {
                throw new CustomAuthenticationException("Insufficient permissions");
            }
        }else {
            throw  new CustomAuthenticationException("Authentication failed");
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
          // Logger logger = LoggerFactory.getLogger(getClass());
          // logger.error("Error parsing JWT: {}", e.getMessage());
            return null;
        }// Assuming the role is stored in a claim named 'role'
    }
}
