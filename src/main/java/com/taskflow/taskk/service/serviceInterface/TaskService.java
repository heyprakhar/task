package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import java.util.UUID;

// import statements - 

public interface TaskService {

    // create task - 
    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    // assign task to a user - 
    TaskResponseDto assignTaskToUser(UUID taskId, UUID userId);
}
