package com.vadimistar.effectivemobiletest.filter;

import com.vadimistar.effectivemobiletest.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = extractToken(request);

        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtService.isTokenValid(token.get())) {
            log.error("Auth failed: token is not valid");
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.getEmailFromToken(token.get());
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("Successfully authenticated user: {}", email);

        filterChain.doFilter(request, response);
    }

    private static Optional<String> extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        return Optional.of(token);
    }

    private static final String BEARER_PREFIX = "Bearer ";
}
