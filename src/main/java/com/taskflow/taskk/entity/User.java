package com.taskflow.taskk.entity;

import com.taskflow.taskk.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;


@Table(name = "users")
@Entity
@Data
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

   @Column(name = "is_active")
   private boolean isActive = true; // default value is true, meaning the user is active when created which is obvious.
    
}
