package com.taskflow.taskk.dto.requestDto;

// Import statements - 

import com.taskflow.taskk.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskStatusUpdateRequestDto {

    private TaskStatus status;
}
