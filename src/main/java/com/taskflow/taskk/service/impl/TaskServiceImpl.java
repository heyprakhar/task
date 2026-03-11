package com.taskflow.taskk.service.impl;

// import statements -
import com.taskflow.taskk.service.serviceInterface.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.taskflow.taskk.dto.requestDto.TaskRequestDto;
import com.taskflow.taskk.dto.responseDto.TaskResponseDto;
import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.repository.UserRepository;
import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.enums.TaskStatus;
import com.taskflow.taskk.mapper.TaskMapper;
import java.util.UUID;
import com.taskflow.taskk.dto.requestDto.TaskStatusUpdateRequestDto;
import java.util.List;


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

    // set default status
    task.setStatus(TaskStatus.PENDING);

    task.setPriority(taskRequestDto.getPriority());

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

    // update task status - 
    @Override
    public TaskResponseDto updateTaskStatus(UUID taskId, TaskStatusUpdateRequestDto taskStatusUpdateRequestDto) {
        log.info("Updating status of task with id: {} to {}", taskId, taskStatusUpdateRequestDto.getStatus());
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setStatus(taskStatusUpdateRequestDto.getStatus());
        Task updatedTask = taskRepository.save(task);
        log.info("Status of task with id: {} updated successfully to {}", taskId,
                taskStatusUpdateRequestDto.getStatus());
        return TaskMapper.toTaskResponseDto(updatedTask);
    }

    // fetch all tasks -  useful for admins/project managers to see if any task is pending or in progress and take necessary actions- 
   @Override
    public List<TaskResponseDto> getAllTasks() {
        log.info("fetching all tasks: {} to {}", taskRepository.findAll().size());
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskMapper::toTaskResponseDto)
                .toList();
    }

    // fetch tasks by user id - useful for users to see all their assigned tasks and manage them accordingly-
    @Override
    public List<TaskResponseDto> getTasksByUserId(UUID userId) {
        log.info("fetching tasks for user with id: {}", userId);
        List<Task> tasks = taskRepository.findByAssignedToId(userId);
        return tasks.stream()
                .map(TaskMapper::toTaskResponseDto)
                .toList();
    }


    //  fetch task by id - useful for users to see details of a specific task and manage it effectively- 
    @Override
    public TaskResponseDto getTaskByID(UUID taskId) {
        log.info("fetching task with id: {}", taskId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        return TaskMapper.toTaskResponseDto(task);
    }
}
