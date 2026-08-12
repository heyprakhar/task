package com.taskflow.taskk.controller;

import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.TaskLinkDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskLinkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.taskflow.taskk.common.utils.Constants.HEADER_USERID;

@RestController
@AllArgsConstructor
@RequestMapping("/task-link")
public class TaskLinkController {

    private final TaskLinkService taskLinkService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<TaskLinkDTO>> createTaskLink(@RequestBody TaskLinkDTO taskLinkDTO, @RequestHeader(HEADER_USERID) String userEmail){

        TaskLinkDTO response = taskLinkService.createTaskLink(taskLinkDTO, userEmail);

        return ResponseEntity.ok(
                BaseApiResponse.<TaskLinkDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("TaskLink created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse<ListResponseDTO<TaskLinkDTO>>> getAllTaskLinks(@RequestHeader(HEADER_USERID) String userEmail, ParamRequest request){

        ListResponseDTO<TaskLinkDTO>  response = taskLinkService.getAllTaskLinks(request, userEmail);

        return ResponseEntity.ok(
                BaseApiResponse.<ListResponseDTO<TaskLinkDTO>>builder()
                         .status(HttpStatus.OK.value())
                         .data(response)
                         .message("TaskLinks fetched successfully.")
                         .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TaskLinkDTO>> getTaskLinkById(@PathVariable Long id, @RequestHeader(HEADER_USERID) String userEmail){

        TaskLinkDTO response = taskLinkService.getTaskLinkById(id, userEmail);

        return ResponseEntity.ok(
                BaseApiResponse.<TaskLinkDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("TaskLink fetched successfully.")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Void>> deleteTaskLinkById(@PathVariable Long id, @RequestHeader(HEADER_USERID) String userEmail){

       taskLinkService.deleteTaskLink(id, userEmail);

       return ResponseEntity.ok(
               BaseApiResponse.<Void>builder()
                       .status(HttpStatus.OK.value())
                       .message("TaskLink deleted successfully.")
                       .data(null)
                       .build()
       );
    }


}
