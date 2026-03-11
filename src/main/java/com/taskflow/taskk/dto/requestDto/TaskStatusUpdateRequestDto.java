package com.taskflow.taskk.dto.requestDto;

// Import statements - 
import lombok.Data;
import com.taskflow.taskk.enums.TaskStatus;

@Data
public class TaskStatusUpdateRequestDto {

    private TaskStatus status;
}
