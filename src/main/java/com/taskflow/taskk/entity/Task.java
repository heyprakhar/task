package com.taskflow.taskk.entity;

import com.taskflow.taskk.common.entity.BaseEntity;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
}