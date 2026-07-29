package com.taskflow.taskk.repository;

// import statements -

import com.taskflow.taskk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
 public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
     Optional<User> findByEmail(String email);
     Optional<User> findById(Long id);
    boolean existsByEmail(String email);

    @Query("""
    SELECT u.id
    FROM User u
    WHERE u.active = true
      AND u.roleId = :roleId
""")
    List<Long> findActiveUserIdsByRoleId(@Param("roleId") Long roleId);
}
