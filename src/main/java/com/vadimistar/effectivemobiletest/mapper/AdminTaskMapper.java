package com.vadimistar.effectivemobiletest.mapper;

import com.vadimistar.effectivemobiletest.dto.AdminCreateTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminUpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminTaskMapper {

    AdminTaskDto mapTaskToAdminTaskDto(Task task);
    Task mapAdminCreateTaskDtoToTask(AdminCreateTaskDto adminCreateTaskDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTask(@MappingTarget Task task, AdminUpdateTaskDto updateTaskDto);
}
