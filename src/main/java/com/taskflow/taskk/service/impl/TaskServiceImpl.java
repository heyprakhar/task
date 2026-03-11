package com.taskflow.taskk.service.impl;

// import statements
import com.taskflow.taskk.service.serviceInterface.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.requestDto.TaskStatusUpdateRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;

import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.repository.UserRepository;

import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.entity.User;

import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;

import com.taskflow.taskk.mapper.TaskMapper;

import java.util.UUID;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // create task
    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {

        log.info("Creating task with title={}", taskRequestDto.getTitle());

        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());

        // default status
        task.setStatus(TaskStatus.PENDING);

        task.setPriority(taskRequestDto.getPriority());

        Task savedTask = taskRepository.save(task);

        log.info("Task created successfully with id={}", savedTask.getId());

        return TaskMapper.toTaskResponseDto(savedTask);
    }

    // assign task to user
    @Override
    public TaskResponseDto assignTaskToUser(UUID taskId, UUID userId) {

        log.info("Assigning taskId={} to userId={}", taskId, userId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        task.setAssignedTo(user);

        Task updatedTask = taskRepository.save(task);

        log.info("Task assigned successfully taskId={} userId={}", taskId, userId);

        return TaskMapper.toTaskResponseDto(updatedTask);
    }

    // update task status
    @Override
    public TaskResponseDto updateTaskStatus(UUID taskId, TaskStatusUpdateRequestDto taskStatusUpdateRequestDto) {

        log.info("Updating task status taskId={} newStatus={}", taskId, taskStatusUpdateRequestDto.getStatus());

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        task.setStatus(taskStatusUpdateRequestDto.getStatus());

        Task updatedTask = taskRepository.save(task);

        log.info("Task status updated successfully taskId={} status={}",
                taskId, taskStatusUpdateRequestDto.getStatus());

        return TaskMapper.toTaskResponseDto(updatedTask);
    }

    // fetch tasks by user id
    @Override
    public List<TaskResponseDto> getTasksByUserId(UUID userId) {

        log.info("Fetching tasks for userId={}", userId);

        List<Task> tasks = taskRepository.findByAssignedToId(userId);

        return tasks.stream()
                .map(TaskMapper::toTaskResponseDto)
                .toList();
    }

    // fetch task by id
    @Override
    public TaskResponseDto getTaskByID(UUID taskId) {

        log.info("Fetching task with taskId={}", taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        return TaskMapper.toTaskResponseDto(task);
    }

    // filter tasks by status and priority
    @Override
    public List<TaskResponseDto> filterTaskByStatusAndPriority(TaskStatus status, TaskPriority priority) {

        log.info("Filtering tasks status={} priority={}", status, priority);

        List<Task> tasks;

        if (status != null && priority != null) {
            tasks = taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority);
        } else {
            tasks = taskRepository.findAll();
        }

        log.info("Filtered tasks count={}", tasks.size());

        return tasks.stream()
                .map(TaskMapper::toTaskResponseDto)
                .toList();
    }

    // delete task by id - 
    @Override
    public void deleteTaskById(UUID taskId) {
        log.info("Deleting task with taskId={}", taskId);

        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }

        taskRepository.deleteById(taskId);

        log.info("Task deleted successfully with taskId={}", taskId); 

    }

}