package com.vadimistar.effectivemobiletest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtDto {

    private String token;
    private int expiresInSeconds;
}
