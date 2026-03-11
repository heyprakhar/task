package com.taskflow.taskk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;
import com.taskflow.taskk.service.serviceInterface.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.taskflow.taskk.common.response.BaseApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;
import org.springframework.web.bind.annotation.PatchMapping;
import com.taskflow.taskk.dto.requestDto.TaskStatusUpdateRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;


    // endpoints for task management - 

    // create task - POST /tasks 
    @PostMapping("/create-task")
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto taskResponseDto = taskService.createTask(taskRequestDto);
        BaseApiResponse<TaskResponseDto> response = new BaseApiResponse<>(true, "Task created successfully",
                taskResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    // assign task to a user -
    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> assignTaskToUser(@PathVariable UUID taskId, @PathVariable UUID userId) {
        TaskResponseDto taskResponseDto = taskService.assignTaskToUser(taskId, userId);
        BaseApiResponse<TaskResponseDto> response = new BaseApiResponse<>(true, "Task assigned successfully", taskResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // update task status endpoint -
    @PatchMapping("/{taskId}/update-status")
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> updateTask(@PathVariable UUID taskId,
            @RequestBody TaskStatusUpdateRequestDto taskStatusUpdateRequestDto) {
        TaskResponseDto taskResponseDto = taskService.updateTaskStatus(taskId, taskStatusUpdateRequestDto);
        BaseApiResponse<TaskResponseDto> response = new BaseApiResponse<>(true, "Task status updated successfully", taskResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

// fetch all tasks or filter by status/priority
@GetMapping("/fetch-tasks")
public ResponseEntity<BaseApiResponse<List<TaskResponseDto>>> fetchTasks(
        @RequestParam(required = false) TaskStatus status,
        @RequestParam(required = false) TaskPriority priority) {

    List<TaskResponseDto> tasks = taskService.filterTaskByStatusAndPriority(status, priority);

    BaseApiResponse<List<TaskResponseDto>> response =
            new BaseApiResponse<>(true, "Tasks fetched successfully", tasks);

    return new ResponseEntity<>(response, HttpStatus.OK);
}

    // fetch tasks by user -
    @GetMapping("/fetch-tasks/user/{userId}") 
    public ResponseEntity<BaseApiResponse<List<TaskResponseDto>>> fetchTasksByUserId(@PathVariable UUID userId) {
        List<TaskResponseDto> tasks = taskService.getTasksByUserId(userId);
        BaseApiResponse<List<TaskResponseDto>> response = new BaseApiResponse<>(true, "Tasks fetched successfully for user with id: " + userId, tasks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // fetch task by taskId -
    @GetMapping("/fetch-tasks/{taskId}")
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> fetchTaskById(@PathVariable UUID taskId) {
        TaskResponseDto taskResponseDto = taskService.getTaskByID(taskId);
        BaseApiResponse<TaskResponseDto> response = new BaseApiResponse<>(true, "Task fetched successfully with id: " + taskId, taskResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
