package com.taskflow.taskk.dto.requestDto;

import lombok.Data;
import com.taskflow.taskk.enums.TaskPriority;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private TaskPriority priority;
}
