package com.taskflow.taskk.mapper;

import com.taskflow.taskk.dto.TaskDTO;
import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.entity.User;

public class TaskMapper {

    private TaskMapper() {
    }

    public static TaskDTO toDto(Task task) {
        if (task == null) {
            return null;
        }

        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .taskType(task.getTaskType())
                .assignedTo(task.getAssignedTo())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .createdBy(task.getCreatedBy())
                .updatedBy(task.getUpdatedBy())
                .build();
    }

    public static Task toEntity(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }

        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(taskDTO.getStatus())
                .priority(taskDTO.getPriority())
                .taskType(taskDTO.getTaskType())
                .assignedTo(taskDTO.getAssignedTo())
                .dueDate(taskDTO.getDueDate())
                .build();

        return task;
    }

    public static void updateEntity(Task task, TaskDTO taskDTO) {

        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }

        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }

        if (taskDTO.getStatus() != null) {
            task.setStatus(taskDTO.getStatus());
        }

        if (taskDTO.getPriority() != null) {
            task.setPriority(taskDTO.getPriority());
        }

        if (taskDTO.getTaskType() != null) {
            task.setTaskType(taskDTO.getTaskType());
        }

        if (taskDTO.getAssignedTo() != null) {
            task.setAssignedTo(taskDTO.getAssignedTo());
        }

        if (taskDTO.getDueDate() != null) {
            task.setDueDate(taskDTO.getDueDate());
        }
    }
}