package com.taskflow.taskk.repository;

import com.taskflow.taskk.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRoleIdAndActiveTrue(Long roleId);
    boolean existsByRoleIdAndPermissionIdAndActiveTrue(Long roleId, Long permissionId);

}