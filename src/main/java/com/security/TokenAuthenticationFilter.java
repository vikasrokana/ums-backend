package com.security;

import com.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    Long id;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    AppProperties appProperties;
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String role = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims = Jwts.parser()
                        .setSigningKey(appProperties.getAuth().getTokenSecret())
                        .parseClaimsJws(token)
                        .getBody();
                role = (String) claims.get("role");
                if(role.equalsIgnoreCase("admin")) {
                    if (request.getRequestURI().contains("/admin/") ||
                            request.getRequestURI().contains("/faculty/") ||
                            request.getRequestURI().contains("/student/") ) {
                        if (StringUtils.hasText(token) && tokenProvider.validateToken(token, role)) {
                            Long userId = tokenProvider.getUserIdFromToken(token, role);
                            id = userId;
                            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }else if(role.equalsIgnoreCase("faculty")) {
                    if (request.getRequestURI().contains("/faculty/") ||
                            request.getRequestURI().contains("/student/")) {
                        if (StringUtils.hasText(token) && tokenProvider.validateToken(token, role)) {
                            Long userId = tokenProvider.getUserIdFromToken(token, role);
                            id = userId;
                            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }else if(role.equalsIgnoreCase("student")) {
                    if (request.getRequestURI().contains("/student/")) {
                        if (StringUtils.hasText(token) && tokenProvider.validateToken(token, role)) {
                            Long userId = tokenProvider.getUserIdFromToken(token, role);
                            id = userId;
                            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }


            }
        }catch (ExpiredJwtException e){
            logger.error("Expired JWT token", e);
        }
        catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    public Long getUserId(){
        return id;
    }
}
