package com.vadimistar.effectivemobiletest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    private long id;
    private String text;
}
