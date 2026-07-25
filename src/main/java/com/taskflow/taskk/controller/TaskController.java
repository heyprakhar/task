package com.taskflow.taskk.controller;

import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.TaskDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.taskflow.taskk.common.utils.Constants.HEADER_USERID;

@RestController
@AllArgsConstructor
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
                        .message(response.getRecords().isEmpty()
                                ? "No tasks found"
                                : "Tasks fetched successfully")
                        .data(response)
                        .build()
        );
    }


}
