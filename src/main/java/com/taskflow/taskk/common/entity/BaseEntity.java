package com.taskflow.taskk.common.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(updatable = false) // createdAt should not be allowed to be updated after creation
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}