package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.TaskDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.mapper.TaskMapper;
import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskService;
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

}