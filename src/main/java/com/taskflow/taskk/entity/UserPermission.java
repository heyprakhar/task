package com.taskflow.taskk.entity;

import com.taskflow.taskk.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_permissions")
public class UserPermission extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long permissionId;

    @Column(nullable = false)
    private boolean granted;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    private Long createdBy;

    private Long updatedBy;
}