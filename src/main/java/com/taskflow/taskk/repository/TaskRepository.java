package com.taskflow.taskk.repository;

// import statements -
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taskflow.taskk.entity.Task;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
     List<Task> findByAssignedToId(UUID userId);
}
