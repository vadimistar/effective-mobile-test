package com.vadimistar.effectivemobiletest.mapper;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    TaskDto mapTaskToTaskDto(Task task);
}
