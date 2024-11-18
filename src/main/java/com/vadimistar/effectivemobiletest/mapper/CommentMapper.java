package com.vadimistar.effectivemobiletest.mapper;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    CommentDto mapCommentToCommentDto(Comment comment);
    Comment mapCreateCommentDtoToComment(CreateCommentDto createCommentDto);
}
