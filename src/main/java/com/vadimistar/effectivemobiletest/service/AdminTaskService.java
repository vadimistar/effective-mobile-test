package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;

public interface AdminTaskService {

    AdminTaskDto getTask(long taskId);
    AdminTaskDto deleteTask(long taskId);
}
