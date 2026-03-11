package com.taskflow.taskk.mapper;


// import statements -

import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import com.taskflow.taskk.entity.Task;


public class TaskMapper {


    // task entity to task response dto converter method - for better code reusability -
    // static method to avoid creating unnecessary objects of TaskMapper class -
    public static TaskResponseDto toTaskResponseDto(Task task) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDescription(task.getDescription());
        taskResponseDto.setStatus(task.getStatus());
        taskResponseDto.setPriority(task.getPriority());
        taskResponseDto.setAssignedTo(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null);
        taskResponseDto.setCreatedAt(task.getCreatedAt());
        taskResponseDto.setUpdatedAt(task.getUpdatedAt());
        return taskResponseDto;
    }

}
