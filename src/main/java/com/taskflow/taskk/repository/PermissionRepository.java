package com.taskflow.taskk.repository;

import com.taskflow.taskk.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
    List<Permission> findByIdInAndActiveTrue(Collection<Long> ids);

}