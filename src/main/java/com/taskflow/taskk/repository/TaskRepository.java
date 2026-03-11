package com.taskflow.taskk.repository;

// import statements -
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.enums.TaskStatus;
import com.taskflow.taskk.enums.TaskPriority;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
     List<Task> findByAssignedToId(UUID userId);

     List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);
     
     List<Task> findByStatus(TaskStatus status);

     List<Task> findByPriority(TaskPriority priority);
}
