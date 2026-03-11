package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;

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

    // fetch all tasks -
    List<TaskResponseDto> getAllTasks();

    // fetch tasks by user - useful for users to see all their assigned tasks and manage them effectively- 
    List<TaskResponseDto> getTasksByUserId(UUID userId);

    // fetch task by id - useful for users to see details of a specific task and manage it effectively-
    TaskResponseDto getTaskByID(UUID taskId);
}
