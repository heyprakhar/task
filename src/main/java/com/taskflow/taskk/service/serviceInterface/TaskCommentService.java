package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.TaskCommentDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;

public interface TaskCommentService {

    TaskCommentDTO createComment(Long taskId, TaskCommentDTO taskCommentDTO, String userEmail);

    ListResponseDTO<TaskCommentDTO> getAllComments(Long taskId, ParamRequest request, String userEmail);

    TaskCommentDTO getCommentById(Long commentId, String userEmail);

    TaskCommentDTO updateComment(Long commentId, TaskCommentDTO taskCommentDTO, String userEmail);

    void deleteComment(Long commentId, String userEmail);
}