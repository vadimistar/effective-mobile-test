package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.JwtDto;
import org.springframework.security.core.Authentication;

public interface JwtService {

    JwtDto createToken(Authentication authentication);
    String getEmailFromToken(String token);
    boolean isTokenValid(String token);
}
