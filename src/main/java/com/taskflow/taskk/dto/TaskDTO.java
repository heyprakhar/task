package com.taskflow.taskk.dto;

import com.taskflow.taskk.entity.User;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskDTO {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private Long assignedTo;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long createdBy;
}