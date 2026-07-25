package com.taskflow.taskk.controller;

import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.requestDto.TaskStatusUpdateRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;
import com.taskflow.taskk.service.serviceInterface.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        TaskResponseDto taskResponseDto = taskService.assignTaskToUser(taskId, userId);
        BaseApiResponse<TaskResponseDto> response = new BaseApiResponse<>(true, "Task assigned successfully", taskResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // update task status endpoint -
    @PatchMapping("/{taskId}/update-status")
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> updateTask(@PathVariable Long taskId,
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
    public ResponseEntity<BaseApiResponse<List<TaskResponseDto>>> fetchTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDto> tasks = taskService.getTasksByUserId(userId);
        BaseApiResponse<List<TaskResponseDto>> response = new BaseApiResponse<>(true, "Tasks fetched successfully for user with id: " + userId, tasks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // fetch task by taskId -
    @GetMapping("/fetch-tasks/{taskId}")
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> fetchTaskById(@PathVariable Long taskId) {
        TaskResponseDto taskResponseDto = taskService.getTaskByID(taskId);
        BaseApiResponse<TaskResponseDto> response = new BaseApiResponse<>(true, "Task fetched successfully with id: " + taskId, taskResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // delete task by id -
    @DeleteMapping("/delete-task/{taskId}")
    public ResponseEntity<BaseApiResponse<Void>> deleteTaskById(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        BaseApiResponse<Void> response = new BaseApiResponse<>(true, "Task deleted successfully with id: " + taskId, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // update task details -
    @PutMapping("/update-task/{taskId}")
    public ResponseEntity<BaseApiResponse<TaskResponseDto>> updateTaskDetails(@PathVariable Long taskId,
            @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto taskResponseDto = taskService.updateTaskDetails(taskId, taskRequestDto);
        BaseApiResponse<TaskResponseDto> response = new BaseApiResponse<>(true,
                "Task details updated successfully with id: " + taskId, taskResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
