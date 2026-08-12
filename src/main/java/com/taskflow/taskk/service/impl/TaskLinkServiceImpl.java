package com.taskflow.taskk.service.impl;


import com.taskflow.taskk.dto.TaskLinkDTO;
import com.taskflow.taskk.dto.UserDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.entity.TaskLink;
import com.taskflow.taskk.exceptions.BusinessException;
import com.taskflow.taskk.exceptions.ErrorCode;
import com.taskflow.taskk.mapper.TaskLinkMapper;
import com.taskflow.taskk.repository.TaskLinkRepository;
import com.taskflow.taskk.repository.TaskRepository;
import com.taskflow.taskk.request.ParamRequest;
import com.taskflow.taskk.service.serviceInterface.TaskLinkService;
import com.taskflow.taskk.service.serviceInterface.UserService;
import com.taskflow.taskk.specification.TaskLinkSpecification;
import com.taskflow.taskk.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskLinkServiceImpl implements TaskLinkService {

    private final UserService userService;
    private final TaskLinkRepository taskLinkRepository;
    private final TaskRepository taskRepository;

    @Override
    public TaskLinkDTO createTaskLink(TaskLinkDTO taskLinkDTO, String userEmail){

        validateTaskLink(taskLinkDTO);
        validateTasksExist(taskLinkDTO);
        validateDuplicateLink(taskLinkDTO);

        UserDTO user = userService.getUserByEmailInternal(userEmail);

        TaskLink taskLink = TaskLinkMapper.toEntity(taskLinkDTO);
        taskLink.setCreatedBy(user.getId());

        taskLinkRepository.save(taskLink);

        return TaskLinkMapper.toDto(taskLink);

    }

    @Override
    public ListResponseDTO<TaskLinkDTO> getAllTaskLinks(ParamRequest request, String userEmail){

        userService.getUserByEmailInternal(userEmail);

        List<TaskLinkDTO> taskLinkDTOS = List.of();

        Specification<TaskLink> spec = Specification.allOf(
                TaskLinkSpecification.hasLinkType(request.getTaskLinkType()),
                TaskLinkSpecification.hasSourceTaskId(request.getSourceTaskId()),
                TaskLinkSpecification.hasTargetTaskId(request.getTargetTaskId()),
                TaskLinkSpecification.involvesTask(request.getTaskId())
        );

        if(request.isRecords()){

            request.setSortBy(request.getSortBy() == null ? "createdAt" : request.getSortBy());

            Page<TaskLink> taskLinks  = taskLinkRepository.findAll(spec,CommonUtils.buildPageRequest(request));

            taskLinkDTOS = taskLinks.getContent()
                    .stream()
                    .map(TaskLinkMapper::toDto)
                    .toList();
        }

        return ListResponseDTO.<TaskLinkDTO>builder()
                .total(taskLinkRepository.count(spec))
                .records(taskLinkDTOS)
                .build();
    }

    @Override
    public TaskLinkDTO getTaskLinkById(Long taskLinkId, String userEmail) {

        userService.getUserByEmailInternal(userEmail);

        if (taskLinkId == null || taskLinkId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task link id must be valid.");
        }

        TaskLink taskLink = taskLinkRepository.findById(taskLinkId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task link not found."));

        return TaskLinkMapper.toDto(taskLink);
    }

    @Override
    public void deleteTaskLink(Long taskLinkId, String userEmail) {

        userService.getUserByEmailInternal(userEmail);

        if (taskLinkId == null || taskLinkId <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task link id must be valid.");
        }

        TaskLink taskLink = taskLinkRepository.findById(taskLinkId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Task link not found."));

        taskLinkRepository.delete(taskLink);
    }



    private void validateTaskLink(TaskLinkDTO taskLinkDTO) {

        if (taskLinkDTO == null || taskLinkDTO.getSourceTaskId() == null || taskLinkDTO.getSourceTaskId() <= 0 || taskLinkDTO.getTargetTaskId() == null || taskLinkDTO.getTargetTaskId() <= 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Source task id and target task id must be valid.");
        }

        if (taskLinkDTO.getSourceTaskId().equals(taskLinkDTO.getTargetTaskId())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "A task cannot be linked to itself.");
        }

        if (taskLinkDTO.getLinkType() == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Task link type must not be null.");
        }

    }

    private void validateTasksExist(TaskLinkDTO taskLinkDTO) {

        if (!taskRepository.existsById(taskLinkDTO.getSourceTaskId())) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Source task not found.");
        }

        if (!taskRepository.existsById(taskLinkDTO.getTargetTaskId())) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Target task not found.");
        }
    }

    private void validateDuplicateLink(TaskLinkDTO taskLinkDTO) {

        boolean exists = taskLinkRepository
                .existsBySourceTaskIdAndTargetTaskIdAndLinkType(
                        taskLinkDTO.getSourceTaskId(),
                        taskLinkDTO.getTargetTaskId(),
                        taskLinkDTO.getLinkType()
                );

        if (exists) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "This task relationship already exists.");
        }
    }

}
