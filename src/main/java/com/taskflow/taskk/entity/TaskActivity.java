package com.taskflow.taskk.entity;

import com.taskflow.taskk.common.entity.BaseEntity;
import com.taskflow.taskk.enums.TaskActivityActorType;
import com.taskflow.taskk.enums.TaskActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_activities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskActivity extends BaseEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private TaskActivityType activityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type", nullable = false)
    private TaskActivityActorType taskActivityActorType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private Long performedBy;

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;
}