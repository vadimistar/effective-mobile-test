package com.vadimistar.effectivemobiletest.mapper;

import com.vadimistar.effectivemobiletest.dto.AdminCreateTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminTaskMapper {

    AdminTaskDto mapTaskToAdminTaskDto(Task task);
    Task mapAdminCreateTaskDtoToTask(AdminCreateTaskDto adminCreateTaskDto);
}
