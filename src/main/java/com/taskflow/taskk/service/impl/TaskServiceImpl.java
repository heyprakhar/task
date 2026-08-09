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
    public ListResponseDTO<TaskDTO> getAllTasks(String userEmail, ParamRequest request) {

        Specification<Task> spec = Specification.allOf(
                TaskSpecification.entityStateIn(request.getEntityState())
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
    public ListResponseDTO<TaskDTO> getAllTasks(ParamRequest request) {

        Specification<Task> spec = Specification.allOf(
                TaskSpecification.entityStateIn(request.getEntityState())
        );

        List<TaskDTO> taskDTOS = List.of();

        if(request.isRecords()){
            request.setSortBy(request.getSortBy() == null ? "name" :request.getSortBy());

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
        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);
        Task task = TaskMapper.toEntity(taskDTO);
        task.setCreatedBy(userDTO.getId());
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    @Override
    public TaskDTO getTaskById(Long taskId, String userEmail) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST, "A task with the given identifier couldn't be found."));

        return TaskMapper.toDto(task);
    }


    @Override
    public TaskDTO updateTask(String userEmail, TaskDTO taskDTO, Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found."));

        TaskMapper.updateEntity(task, taskDTO);
        taskRepository.save(task);

        return TaskMapper.toDto(task);
    }

    @Override
    public void deleteTaskById(Long taskId, String userEmail) {

        if (taskId == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must not be null.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found with the given identifier."));

        taskRepository.delete(task);
    }

}