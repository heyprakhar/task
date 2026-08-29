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
@Table(name = "role_permissions")
public class RolePermission extends BaseEntity {

    @Column(nullable = false)
    private Long roleId;

    @Column(nullable = false)
    private Long permissionId;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    private Long createdBy;

    private Long updatedBy;
}