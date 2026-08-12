package com.taskflow.taskk.entity;


import com.taskflow.taskk.common.entity.BaseEntity;
import com.taskflow.taskk.enums.TaskLinkType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_links",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_task_link", columnNames = {"source_task_id", "target_task_id", "link_type"})
        }

)@Getter
@Setter
@Builder
public class TaskLink extends BaseEntity {

    @Column(nullable = false)
    private Long sourceTaskId;

    @Column(nullable = false)
    private Long targetTaskId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskLinkType linkType;

    private Long createdBy;

    private Long updatedBy;
}
