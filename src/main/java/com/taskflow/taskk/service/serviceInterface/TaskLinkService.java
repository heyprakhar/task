package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.TaskLinkDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;

public interface TaskLinkService {

    TaskLinkDTO createTaskLink(TaskLinkDTO taskLinkDTO, String userEmail);

    ListResponseDTO<TaskLinkDTO> getAllTaskLinks(ParamRequest request, String userEmail);

    TaskLinkDTO getTaskLinkById(Long taskLinkId, String userEmail);

    void deleteTaskLink(Long taskLinkId, String userEmail);
}
