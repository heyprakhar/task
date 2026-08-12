package com.taskflow.taskk.dto;

import com.taskflow.taskk.enums.TaskActivityActorType;
import com.taskflow.taskk.enums.TaskActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskActivityDTO {

    private Long id;

    private Long taskId;

    private TaskActivityType activityType;

    private TaskActivityActorType taskActivityActorType;

    private String description;

    private Long performedBy;

    private String oldValue;

    private String newValue;

    private LocalDateTime createdAt;
}