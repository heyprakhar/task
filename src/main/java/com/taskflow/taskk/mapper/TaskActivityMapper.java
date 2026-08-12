package com.taskflow.taskk.mapper;

import com.taskflow.taskk.dto.TaskActivityDTO;
import com.taskflow.taskk.entity.TaskActivity;

public class TaskActivityMapper {

    private TaskActivityMapper() {
    }

    public static TaskActivityDTO toDto(TaskActivity activity) {

        if (activity == null) {
            return null;
        }

        return TaskActivityDTO.builder()
                .id(activity.getId())
                .taskId(activity.getTaskId())
                .activityType(activity.getActivityType())
                .description(activity.getDescription())
                .taskActivityActorType(activity.getTaskActivityActorType())
                .performedBy(activity.getPerformedBy())
                .oldValue(activity.getOldValue())
                .newValue(activity.getNewValue())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    public static TaskActivity toEntity(TaskActivityDTO activityDTO) {

        if (activityDTO == null) {
            return null;
        }

        return TaskActivity.builder()
                .taskId(activityDTO.getTaskId())
                .activityType(activityDTO.getActivityType())
                .description(activityDTO.getDescription())
                .oldValue(activityDTO.getOldValue())
                .newValue(activityDTO.getNewValue())
                .build();
    }
}