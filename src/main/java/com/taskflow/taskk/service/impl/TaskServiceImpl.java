package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.TaskDTO;
import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.exceptions.BusinessException;
import com.taskflow.taskk.exceptions.ErrorCode;
import com.taskflow.taskk.mapper.TaskMapper;
import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskService;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.specification.TaskSpecification;
import com.taskflow.taskk.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public ListResponseDTO<TaskDTO> getAllTasks(ParamRequest request, String userEmail) {

        userService.getUserByEmailInternal(userEmail);

        Specification<Task> spec = Specification.allOf(
                TaskSpecification.entityStateIn(request.getEntityState()),
                TaskSpecification.search(request.getSearch())
        );

        List<TaskDTO> taskDTOS = List.of();

        if(request.isRecords()){
            request.setSortBy(request.getSortBy() == null ? "title" :request.getSortBy());

            Page<Task> tasks = taskRepository.findAll(spec, CommonUtils.buildPageRequest(request));

            taskDTOS = tasks.getContent()
                    .stream()
                    .map(TaskMapper ::toDto)
                    .toList();
        }

        return ListResponseDTO.<TaskDTO>builder()
                .total(taskRepository.count(spec))
                .records(taskDTOS)
                .build();
    }


    @Override
    public TaskDTO createTask(TaskDTO taskDTO, String userEmail) {
        validateTask(taskDTO);
        validateAssignee(taskDTO.getAssignedTo());
        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);
        Task task = TaskMapper.toEntity(taskDTO);
        task.setCreatedBy(userDTO.getId());
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    @Override
    public TaskDTO getTaskById(Long taskId, String userEmail) {

        if (taskId == null || taskId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        userService.getUserByEmailInternal(userEmail);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "A task with the given identifier couldn't be found."));

        return TaskMapper.toDto(task);
    }


    @Override
    public TaskDTO updateTask(String userEmail, TaskDTO taskDTO, Long taskId) {

        if (taskId == null || taskId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        validateTaskUpdate(taskDTO);
        validateAssignee(taskDTO.getAssignedTo());

        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found."));

        TaskMapper.updateEntity(task, taskDTO);
        task.setUpdatedBy(userDTO.getId());
        taskRepository.save(task);

        return TaskMapper.toDto(task);
    }

    @Override
    public void deleteTaskById(Long taskId, String userEmail) {
        UserDTO user = userService.getUserByEmailInternal(userEmail);

        if (taskId == null || taskId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found with the given identifier."));

        task.setDeletedBy(user.getId());
        taskRepository.delete(task);
    }

    @Override
    public void unassignTask(Long taskId, String userEmail) {

        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);

        if (taskId == null || taskId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found."));

        task.setAssignedTo(null);
        task.setUpdatedBy(userDTO.getId());

        taskRepository.save(task);
    }

    private void validateTask(TaskDTO taskDTO) {

        if (taskDTO == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task must not be null.");
        }

        if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task title must not be empty.");
        }

        if (taskDTO.getStatus() == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task status must not be null.");
        }

        if (taskDTO.getPriority() == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task priority must not be null.");
        }

        if (taskDTO.getTaskType() == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task type must not be null.");
        }
    }

    private void validateTaskUpdate(TaskDTO taskDTO) {

        if (taskDTO == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task update request must not be null.");
        }

        if (taskDTO.getTitle() != null && taskDTO.getTitle().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task title must not be empty.");
        }

        if (taskDTO.getAssignedTo() != null && taskDTO.getAssignedTo() <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Assigned user id must be valid.");
        }
    }

    private void validateAssignee(Long assignedTo) {

        if (assignedTo == null) {
            return;
        }

        userService.getUserByIdInternal(assignedTo);
    }

}