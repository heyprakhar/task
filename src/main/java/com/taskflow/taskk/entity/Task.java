package com.taskflow.taskk.entity;

// import statements -
import com.taskflow.taskk.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import com.taskflow.taskk.enums.TaskStatus;
import com.taskflow.taskk.enums.TaskPriority;


@Entity
@Data
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "priority")
    private TaskPriority priority;

    @Column(name = "assigned_to", nullable = true) // by default, a task can be unassigned, so we allow null values for assignedTo
    private User assignedTo;
}
