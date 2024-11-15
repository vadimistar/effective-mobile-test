package com.vadimistar.effectivemobiletest.mapper;

import com.vadimistar.effectivemobiletest.dto.RegisterUserDto;
import com.vadimistar.effectivemobiletest.dto.UserDto;
import com.vadimistar.effectivemobiletest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User mapRegisterUserDtoToUser(RegisterUserDto registerUserDto);
    UserDto mapUserToUserDto(User user);
}
