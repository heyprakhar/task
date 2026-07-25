package com.taskflow.taskk.dto.responseDto;

import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDto {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private Long assignedTo;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}