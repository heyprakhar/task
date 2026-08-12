package com.taskflow.taskk.dto;


import com.taskflow.taskk.enums.TaskLinkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLinkDTO {
    private Long id;

    private Long sourceTaskId;

    private Long targetTaskId;

    private TaskLinkType linkType;

    private Long createdBy;

    private LocalDateTime createdAt;
}
