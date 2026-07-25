package com.taskflow.taskk.dto.requestDto;

import com.taskflow.taskk.enums.TaskPriority;
import lombok.Data;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private TaskPriority priority;
}
