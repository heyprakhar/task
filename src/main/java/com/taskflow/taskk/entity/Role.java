package com.taskflow.taskk.entity;

// import statements - 
import com.taskflow.taskk.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Data
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
