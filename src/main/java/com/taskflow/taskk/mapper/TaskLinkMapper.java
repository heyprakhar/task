package com.taskflow.taskk.mapper;

import com.taskflow.taskk.dto.TaskLinkDTO;
import com.taskflow.taskk.entity.TaskLink;

public class TaskLinkMapper {

    private TaskLinkMapper() {
    }

    public static TaskLinkDTO toDto(TaskLink taskLink) {
        if (taskLink == null) {
            return null;
        }

        return TaskLinkDTO.builder()
                .id(taskLink.getId())
                .sourceTaskId(taskLink.getSourceTaskId())
                .targetTaskId(taskLink.getTargetTaskId())
                .linkType(taskLink.getLinkType())
                .createdAt(taskLink.getCreatedAt())
                .build();
    }

    public static TaskLink toEntity(TaskLinkDTO taskLinkDTO) {
        if (taskLinkDTO == null) {
            return null;
        }

        return TaskLink.builder()
                .sourceTaskId(taskLinkDTO.getSourceTaskId())
                .targetTaskId(taskLinkDTO.getTargetTaskId())
                .linkType(taskLinkDTO.getLinkType())
                .build();
    }
}