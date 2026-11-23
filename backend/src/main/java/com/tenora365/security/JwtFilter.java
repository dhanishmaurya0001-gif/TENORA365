package com.tenora365.security;

import com.tenora365.tenant.TenantContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String uri = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        return (uri.startsWith("/api/auth/") && !uri.equals("/api/auth/me"))
                || uri.equals("/actuator/health")
                || uri.startsWith("/actuator/health/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String authorization =
                    request.getHeader("Authorization");

            if (authorization != null
                    && authorization.startsWith("Bearer ")) {

                String token =
                        authorization.substring(7);

                Claims claims;
                try {
                    claims = jwtService.parse(token);
                } catch (io.jsonwebtoken.JwtException | IllegalArgumentException invalidToken) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Session expired. Please sign in again.\"}");
                    return;
                }

                Long schoolId =
                        claims.get("schoolId", Long.class);

                String role =
                        claims.get("role", String.class);

                String subject =
                        claims.getSubject();

                if (role != null && subject != null) {

                    if (schoolId != null) {
                        TenantContext.set(schoolId);
                    }

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    subject,
                                    null,
                                    List.of(
                                            new SimpleGrantedAuthority(
                                                    "ROLE_" + role
                                            )
                                    )
                            );

                    authentication.setDetails(claims);

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);

        } finally {

            TenantContext.clear();
            SecurityContextHolder.clearContext();
        }
    }
}
