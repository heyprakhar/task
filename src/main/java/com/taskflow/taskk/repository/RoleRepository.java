package com.taskflow.taskk.repository;

import java.util.UUID;

// import statements - 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taskflow.taskk.entity.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
