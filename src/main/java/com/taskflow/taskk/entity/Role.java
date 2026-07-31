package com.taskflow.taskk.entity;


import com.taskflow.taskk.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Builder.Default
    private boolean active = true;

    private Long createdBy;

    private Long deactivatedBy;

    private Long activatedBy;

    private LocalDateTime reactivatedTime;

    private LocalDateTime deactivatedTime;

    private Long updatedBy;

}
