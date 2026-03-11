package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;

import java.util.List;
import java.util.UUID;
import com.taskflow.taskk.dto.requestDto.TaskStatusUpdateRequestDto;

// import statements - 

public interface TaskService {

    // create task - 
    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    // assign task to a user - 
    TaskResponseDto assignTaskToUser(UUID taskId, UUID userId);

    // update task status - 
    TaskResponseDto updateTaskStatus(UUID taskID, TaskStatusUpdateRequestDto taskStatusUpdateRequestDto);

    // fetch tasks by user - useful for users to see all their assigned tasks and manage them effectively- 
    List<TaskResponseDto> getTasksByUserId(UUID userId);

    // fetch task by id - useful for users to see details of a specific task and manage it effectively-
    TaskResponseDto getTaskByID(UUID taskId);

    // filter tasks by status and priority - useful for users to filter their tasks based on status and priority and manage them effectively-
    List<TaskResponseDto> filterTaskByStatusAndPriority(TaskStatus status, TaskPriority priority);
}
