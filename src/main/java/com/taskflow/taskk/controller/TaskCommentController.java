package com.taskflow.taskk.controller;

import com.taskflow.taskk.common.response.BaseApiResponse;
import com.taskflow.taskk.dto.TaskCommentDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.taskflow.taskk.common.utils.Constants.HEADER_USERID;

@RestController
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<BaseApiResponse<TaskCommentDTO>> createComment(@PathVariable Long taskId, @RequestBody TaskCommentDTO taskCommentDTO, @RequestHeader(HEADER_USERID) String userEmail) {

        TaskCommentDTO response = taskCommentService.createComment(taskId, taskCommentDTO, userEmail);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        BaseApiResponse.<TaskCommentDTO>builder()
                                .status(HttpStatus.CREATED.value())
                                .message("Comment created successfully")
                                .data(response)
                                .build()
                );
    }

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<BaseApiResponse<ListResponseDTO<TaskCommentDTO>>> getComments(@PathVariable Long taskId, ParamRequest request, @RequestHeader(HEADER_USERID) String userEmail) {

        ListResponseDTO<TaskCommentDTO> response = taskCommentService.getAllComments(taskId, request, userEmail);

        return ResponseEntity.ok(
                BaseApiResponse.<ListResponseDTO<TaskCommentDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Comments fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<BaseApiResponse<TaskCommentDTO>> getCommentById(@PathVariable Long commentId, @RequestHeader(HEADER_USERID) String userEmail) {

        TaskCommentDTO response = taskCommentService.getCommentById(commentId, userEmail);

        return ResponseEntity.ok(
                BaseApiResponse.<TaskCommentDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Comment fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<BaseApiResponse<TaskCommentDTO>> updateComment(@PathVariable Long commentId, @RequestBody TaskCommentDTO taskCommentDTO, @RequestHeader(HEADER_USERID) String userEmail) {

        TaskCommentDTO response = taskCommentService.updateComment(commentId, taskCommentDTO, userEmail);

        return ResponseEntity.ok(
                BaseApiResponse.<TaskCommentDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Comment updated successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<BaseApiResponse<Void>> deleteComment(@PathVariable Long commentId, @RequestHeader(HEADER_USERID) String userEmail) {

        taskCommentService.deleteComment(commentId, userEmail);

        return ResponseEntity.ok(
                BaseApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Comment deleted successfully")
                        .data(null)
                        .build()
        );
    }
}