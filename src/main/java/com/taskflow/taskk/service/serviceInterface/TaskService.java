package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.TaskDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;

// import statements - 

public interface TaskService {

    ListResponseDTO<TaskDTO> getAllTasks(String userEmail, ParamRequest request);

    ListResponseDTO<TaskDTO> getAllTasks(ParamRequest request);
}
