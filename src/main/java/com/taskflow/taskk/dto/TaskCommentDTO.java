package com.taskflow.taskk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCommentDTO {

    private Long id;

    private Long taskId;

    private String content;

    private Long createdBy;

    private Long updatedBy;

    private Long deletedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}