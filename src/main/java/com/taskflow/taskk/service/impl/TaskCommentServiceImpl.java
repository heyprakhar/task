package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.TaskCommentDTO;
import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.entity.TaskComment;
import com.taskflow.taskk.exceptions.BusinessException;
import com.taskflow.taskk.exceptions.ErrorCode;
import com.taskflow.taskk.mapper.TaskCommentMapper;
import com.taskflow.taskk.repository.TaskCommentRepository;
import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskCommentService;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.specification.TaskCommentSpecification;
import com.taskflow.taskk.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskCommentDTO createComment(Long taskId, TaskCommentDTO taskCommentDTO, String userEmail) {

        if (taskId == null || taskId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        validateComment(taskCommentDTO);

        if (!taskRepository.existsById(taskId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found.");
        }

        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);

        TaskComment taskComment = TaskCommentMapper.toEntity(taskCommentDTO);

        taskComment.setTaskId(taskId);
        taskComment.setCreatedBy(userDTO.getId());

        taskCommentRepository.save(taskComment);

        return TaskCommentMapper.toDto(taskComment);
    }

    @Override
    public ListResponseDTO<TaskCommentDTO> getAllComments(Long taskId, ParamRequest request, String userEmail) {

        userService.getUserByEmailInternal(userEmail);

        if (taskId == null || taskId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        if (!taskRepository.existsById(taskId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found.");
        }

        Specification<TaskComment> spec = Specification.allOf(
                TaskCommentSpecification.hasTaskId(taskId),
                TaskCommentSpecification.search(request.getSearch())
        );

        List<TaskCommentDTO> comments = List.of();

        if (request.isRecords()) {

            request.setSortBy(request.getSortBy() == null ? "createdAt" : request.getSortBy());

            Page<TaskComment> taskComments = taskCommentRepository.findAll(spec, CommonUtils.buildPageRequest(request));

            comments = taskComments.getContent()
                    .stream()
                    .map(TaskCommentMapper::toDto)
                    .toList();
        }

        return ListResponseDTO.<TaskCommentDTO>builder()
                .total(taskCommentRepository.count(spec))
                .records(comments)
                .build();
    }

    @Override
    public TaskCommentDTO getCommentById(Long commentId, String userEmail) {

        if (commentId == null || commentId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment id must be valid.");
        }

        userService.getUserByEmailInternal(userEmail);

        TaskComment taskComment = taskCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Comment not found."));

        return TaskCommentMapper.toDto(taskComment);
    }

    @Override
    public TaskCommentDTO updateComment(Long commentId, TaskCommentDTO taskCommentDTO, String userEmail) {

        if (commentId == null || commentId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment id must be valid.");
        }

        validateCommentUpdate(taskCommentDTO);

        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);

        TaskComment taskComment = taskCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Comment not found."));

        TaskCommentMapper.updateEntity(taskComment, taskCommentDTO);

        taskComment.setUpdatedBy(userDTO.getId());

        taskCommentRepository.save(taskComment);

        return TaskCommentMapper.toDto(taskComment);
    }

    @Override
    public void deleteComment(Long commentId, String userEmail) {

        if (commentId == null || commentId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment id must be valid.");
        }

        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);

        TaskComment taskComment = taskCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Comment not found."));

        taskComment.setDeletedBy(userDTO.getId());
        taskCommentRepository.delete(taskComment);
    }


    private void validateComment(TaskCommentDTO taskCommentDTO) {

        if (taskCommentDTO == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment must not be null.");
        }

        if (taskCommentDTO.getContent() == null || taskCommentDTO.getContent().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment content must not be empty.");
        }

        if (taskCommentDTO.getContent().length() > 65535) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment content is too long.");
        }
    }

    private void validateCommentUpdate(TaskCommentDTO taskCommentDTO) {

        if (taskCommentDTO == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment update request must not be null.");
        }

        if (taskCommentDTO.getContent() == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment content must be provided.");
        }

        if (taskCommentDTO.getContent().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment content must not be empty.");
        }

        if (taskCommentDTO.getContent().length() > 65535) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Comment content is too long.");
        }
    }
}