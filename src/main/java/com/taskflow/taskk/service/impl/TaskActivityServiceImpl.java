package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.TaskActivityDTO;
import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.entity.TaskActivity;
import com.taskflow.taskk.enums.TaskActivityActorType;
import com.taskflow.taskk.exceptions.BusinessException;
import com.taskflow.taskk.exceptions.ErrorCode;
import com.taskflow.taskk.mapper.TaskActivityMapper;
import com.taskflow.taskk.repository.TaskActivityRepository;
import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskActivityService;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.specification.TaskActivitySpecification;
import com.taskflow.taskk.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskActivityServiceImpl implements TaskActivityService {

    private final TaskActivityRepository taskActivityRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public void createActivityByUser(TaskActivityDTO activityDTO, String userEmail) {

        if (!taskRepository.existsById(activityDTO.getTaskId())) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found.");
        }

        validateActivity(activityDTO);
        UserDTO userDTO = userService.getUserByEmailInternal(userEmail);
        TaskActivity taskActivity = TaskActivityMapper.toEntity(activityDTO);
        taskActivity.setTaskActivityActorType(TaskActivityActorType.USER);
        taskActivity.setPerformedBy(userDTO.getId());
        taskActivityRepository.save(taskActivity);
    }

    @Override
    public void createActivityBySystem(TaskActivityDTO activityDTO) {

        if (!taskRepository.existsById(activityDTO.getTaskId())) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found.");
        }

        validateActivity(activityDTO);
        TaskActivity taskActivity = TaskActivityMapper.toEntity(activityDTO);
        taskActivity.setTaskActivityActorType(TaskActivityActorType.SYSTEM);
        taskActivity.setPerformedBy(null);
        taskActivityRepository.save(taskActivity);
    }



    @Override
    public ListResponseDTO<TaskActivityDTO> getTaskActivities(Long taskId, ParamRequest request, String userEmail) {

        if (taskId == null || taskId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        if (!taskRepository.existsById(taskId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task not found.");
        }

        userService.getUserByEmailInternal(userEmail);

        Specification<TaskActivity> spec = Specification.allOf(
                TaskActivitySpecification.hasTaskId(taskId),
                TaskActivitySpecification.hasActivityType(request.getTaskActivityType()),
                TaskActivitySpecification.hasPerformedBy(request.getTaskActivityPerformedBy()),
                TaskActivitySpecification.hasActorType(request.getTaskActivityActorType())
        );

        List<TaskActivityDTO> activities = List.of();

        if (request.isRecords()) {

            request.setSortBy(request.getSortBy() == null ? "createdAt" : request.getSortBy());

            Page<TaskActivity> taskActivities = taskActivityRepository.findAll(spec, CommonUtils.buildPageRequest(request));

            activities = taskActivities.getContent()
                    .stream()
                    .map(TaskActivityMapper::toDto)
                    .toList();
        }

        return ListResponseDTO.<TaskActivityDTO>builder()
                .total(taskActivityRepository.count(spec))
                .records(activities)
                .build();
    }

    private void validateActivity(TaskActivityDTO activityDTO) {

        if (activityDTO == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task activity must not be null.");
        }

        if (activityDTO.getTaskId() == null || activityDTO.getTaskId() <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task id must be valid.");
        }

        if (activityDTO.getActivityType() == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Activity type must not be null.");
        }

        if (activityDTO.getDescription() == null || activityDTO.getDescription().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Activity description must not be empty.");
        }
    }
}