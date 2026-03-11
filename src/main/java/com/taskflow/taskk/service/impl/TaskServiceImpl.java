package com.taskflow.taskk.service.impl;


import com.taskflow.taskk.service.serviceInterface.TaskService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.repository.UserRepository;
import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.mapper.TaskMapper;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // create task - 
    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {

        log.info("Creating task with title: {}", taskRequestDto.getTitle());

        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setStatus(taskRequestDto.getStatus());
        task.setPriority(taskRequestDto.getPriority());

        if (taskRequestDto.getAssignedTo() != null) {
            userRepository.findById(taskRequestDto.getAssignedTo())
                    .ifPresent(user -> task.setAssignedTo(user));
        }

        Task savedTask = taskRepository.save(task);

        log.info("Task created successfully with id: {}", savedTask.getId());

        return TaskMapper.toTaskResponseDto(savedTask);
    }

    // assign task to a user - 
    public TaskResponseDto assignTaskToUser(UUID taskId, UUID userId) {
        log.info("Assigning task with id: {} to user with id: {}", taskId, userId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        userRepository.findById(userId)
                .ifPresentOrElse(
                        user -> {
                            task.setAssignedTo(user);
                            taskRepository.save(task);
                            log.info("Task with id: {} assigned to user with id: {}", taskId, userId);
                        },
                        () -> log.warn("User not found with id: {}", userId)
                );

        return TaskMapper.toTaskResponseDto(task);    
    }

}
