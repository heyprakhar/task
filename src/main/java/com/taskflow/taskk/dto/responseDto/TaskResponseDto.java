package com.taskflow.taskk.dto.responseDto;

import lombok.Data;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskResponseDto {

    private UUID id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private UUID assignedTo;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}