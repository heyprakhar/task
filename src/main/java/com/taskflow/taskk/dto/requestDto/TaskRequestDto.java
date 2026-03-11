package com.taskflow.taskk.dto.requestDto;

import lombok.Data;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;
import java.util.UUID;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UUID assignedTo;
}
