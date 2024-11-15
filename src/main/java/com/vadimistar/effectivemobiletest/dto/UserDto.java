package com.vadimistar.effectivemobiletest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private long id;
    private String email;
}
