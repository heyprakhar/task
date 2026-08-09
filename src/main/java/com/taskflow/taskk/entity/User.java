package com.taskflow.taskk.entity;

import com.taskflow.taskk.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Long roleId;

    @Builder.Default
    @Column(name = "is_active")
    private boolean active=true;

    private Long createdBy;
    
}
