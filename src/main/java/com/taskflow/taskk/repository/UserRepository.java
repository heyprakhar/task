package com.taskflow.taskk.repository;

// import statements -

import com.taskflow.taskk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
 public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
     Optional<User> findByEmail(String email);
     Optional<User> findById(Long id);
    boolean existsByEmail(String email);
}
