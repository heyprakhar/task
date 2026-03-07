package com.taskflow.taskk.repository;

// import statements -
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taskflow.taskk.entity.User;


@Repository
 public interface UserRepository extends JpaRepository<User, UUID> {
     Optional<User> findByEmail(String email);
}
