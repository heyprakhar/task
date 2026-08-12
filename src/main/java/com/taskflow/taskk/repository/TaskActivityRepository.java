package com.taskflow.taskk.repository;

import com.taskflow.taskk.entity.TaskActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskActivityRepository extends JpaRepository<TaskActivity, Long>, JpaSpecificationExecutor<TaskActivity> {
}