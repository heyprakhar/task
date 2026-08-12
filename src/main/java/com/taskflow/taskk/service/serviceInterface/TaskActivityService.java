package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.TaskActivityDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.enums.TaskActivityType;
import com.taskflow.taskk.request.ParamRequest;

public interface TaskActivityService {

    void createActivityByUser(TaskActivityDTO taskActivityDTO, String userEmail);
    void createActivityBySystem(TaskActivityDTO activityDTO);
    ListResponseDTO<TaskActivityDTO> getTaskActivities(Long taskId, ParamRequest request, String userEmail);
}
