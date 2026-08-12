package com.taskflow.taskk.mapper;

import com.taskflow.taskk.dto.TaskCommentDTO;
import com.taskflow.taskk.entity.TaskComment;

public class TaskCommentMapper {

    private TaskCommentMapper() {
    }

    public static TaskCommentDTO toDto(TaskComment taskComment) {

        if (taskComment == null) {
            return null;
        }

        return TaskCommentDTO.builder()
                .id(taskComment.getId())
                .taskId(taskComment.getTaskId())
                .content(taskComment.getContent())
                .createdBy(taskComment.getCreatedBy())
                .updatedBy(taskComment.getUpdatedBy())
                .deletedBy(taskComment.getDeletedBy())
                .createdAt(taskComment.getCreatedAt())
                .updatedAt(taskComment.getUpdatedAt())
                .build();
    }

    public static TaskComment toEntity(TaskCommentDTO taskCommentDTO) {

        if (taskCommentDTO == null) {
            return null;
        }

        return TaskComment.builder()
                .taskId(taskCommentDTO.getTaskId())
                .content(taskCommentDTO.getContent())
                .build();
    }

    public static void updateEntity(
            TaskComment taskComment,
            TaskCommentDTO taskCommentDTO
    ) {

        if (taskCommentDTO.getContent() != null) {
            taskComment.setContent(taskCommentDTO.getContent());
        }
    }
}