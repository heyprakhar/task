package com.taskflow.taskk.entity;

import com.taskflow.taskk.common.entity.BaseEntity;
import com.taskflow.taskk.enums.TaskPriority;
import com.taskflow.taskk.enums.TaskStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "assigned_to")
    private Long assignedTo;

    private LocalDateTime dueDate;
}