package com.taskflow.taskk.repository;

import com.taskflow.taskk.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    List<UserPermission> findByUserIdAndActiveTrue(Long userId);
    Optional<UserPermission> findByUserIdAndPermissionIdAndActiveTrue(Long userId, Long permissionId);
    boolean existsByUserIdAndPermissionIdAndActiveTrue(Long userId, Long permissionId);

}