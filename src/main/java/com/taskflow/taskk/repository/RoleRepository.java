package com.taskflow.taskk.repository;

import com.taskflow.taskk.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    List<Role> findByActive(Boolean active);

    Optional<Role> findByIdAndActive(Long id, Boolean active);

    List<Role> findByNameContainingIgnoreCase(String name);
}