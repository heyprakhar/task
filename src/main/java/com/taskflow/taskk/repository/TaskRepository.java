package com.taskflow.taskk.repository;

// import statements -

import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>,JpaSpecificationExecutor<Task> {
     List<Task> findByAssignedToId(Long userId);

     List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);
     
     List<Task> findByStatus(TaskStatus status);

     List<Task> findByPriority(TaskPriority priority);
}
