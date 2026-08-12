package com.taskflow.taskk.entity;

import com.taskflow.taskk.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task_comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskComment extends BaseEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Long createdBy;

    private Long updatedBy;

    private Long deletedBy;
}