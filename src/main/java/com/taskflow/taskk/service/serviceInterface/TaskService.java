package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.requestDto.TaskStatusUpdateRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;

import java.util.List;

// import statements - 

public interface TaskService {

    // create task - 
    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    // assign task to a user - 
    TaskResponseDto assignTaskToUser(Long taskId, Long userId);

    // update task status - 
    TaskResponseDto updateTaskStatus(Long taskID, TaskStatusUpdateRequestDto taskStatusUpdateRequestDto);

    // fetch tasks by user - useful for users to see all their assigned tasks and manage them effectively- 
    List<TaskResponseDto> getTasksByUserId(Long userId);

    // fetch task by id - useful for users to see details of a specific task and manage it effectively-
    TaskResponseDto getTaskByID(Long taskId);

    // filter tasks by status and priority - useful for users to filter their tasks based on status and priority and manage them effectively-
    List<TaskResponseDto> filterTaskByStatusAndPriority(TaskStatus status, TaskPriority priority);

    // delete task by id - useful for users to delete a task that is no longer needed or relevant-
    void deleteTaskById(Long taskId);

    // update task details - useful for users to update the details of a task such as title, description, and priority to keep the task information accurate and up-to-date-
    TaskResponseDto updateTaskDetails(Long taskId, TaskRequestDto taskRequestDto);
}
