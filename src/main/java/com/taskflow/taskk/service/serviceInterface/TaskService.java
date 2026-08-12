package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.TaskDTO;
import com.taskflow.taskk.dto.responseDto.ListResponseDTO;
import com.taskflow.taskk.request.ParamRequest;

// import statements - 

public interface TaskService {

    ListResponseDTO<TaskDTO> getAllTasks(ParamRequest request,String userEmail);

    TaskDTO createTask(TaskDTO taskDTO, String userEmail);

    TaskDTO getTaskById(Long taskId, String userEmail);

    TaskDTO updateTask(String userEmail, TaskDTO taskDTO, Long taskId);

    void deleteTaskById(Long taskId, String userEmail);

    void unassignTask(Long taskId, String userEmail);
}
