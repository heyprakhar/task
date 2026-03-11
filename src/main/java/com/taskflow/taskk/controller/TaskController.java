package com.taskflow.taskk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
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

}
