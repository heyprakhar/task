package com.taskflow.taskk.controller;

import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.TaskDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.taskflow.taskk.common.utils.Constants.HEADER_USERID;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<BaseApiResponse<ListResponseDTO<TaskDTO>>> getTasks(@RequestHeader(HEADER_USERID) String userEmail, ParamRequest request) {

        ListResponseDTO<TaskDTO> response = taskService.getAllTasks(userEmail, request);

        return ResponseEntity.ok(
                BaseApiResponse.<ListResponseDTO<TaskDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Tasks fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/test")
    public ResponseEntity<BaseApiResponse<ListResponseDTO<TaskDTO>>> getTasks(ParamRequest request) {

        ListResponseDTO<TaskDTO> response = taskService.getAllTasks(request);

        return ResponseEntity.ok(
                BaseApiResponse.<ListResponseDTO<TaskDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Tasks fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<BaseApiResponse<TaskDTO>> createTask(@RequestBody TaskDTO taskDTO, @RequestHeader(HEADER_USERID) String userEmail) {

        TaskDTO response = taskService.createTask(taskDTO,userEmail );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        BaseApiResponse.<TaskDTO>builder()
                                .status(HttpStatus.CREATED.value())
                                .message("Task created successfully")
                                .data(response)
                                .build()
                );
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<BaseApiResponse<TaskDTO>> getTaskById(@RequestHeader(HEADER_USERID) String userEmail, @PathVariable Long id) {
        TaskDTO response = taskService.getTaskById(id, userEmail);
        return ResponseEntity.ok(
                        BaseApiResponse.<TaskDTO>builder()
                                .status(HttpStatus.OK.value())
                                .message("Task fetched successfully")
                                .data(response)
                                .build()
                );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TaskDTO>> updateTask(@RequestHeader(HEADER_USERID) String userEmail, @RequestBody TaskDTO taskDTO, @PathVariable Long id) {

        TaskDTO response = taskService.updateTask(userEmail, taskDTO, id);
        return ResponseEntity.ok(
                BaseApiResponse.<TaskDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Task updated successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Void>> deleteTaskById(@RequestHeader(HEADER_USERID) String userEmail, @PathVariable Long id) {
        taskService.deleteTaskById(id, userEmail);
        return ResponseEntity.ok(
                BaseApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Task has been deleted successfully.")
                        .data(null)
                        .build()
        );
    }


}
