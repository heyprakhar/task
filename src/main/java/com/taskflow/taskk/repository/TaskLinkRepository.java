package com.taskflow.taskk.repository;


import com.taskflow.taskk.entity.TaskLink;
import com.taskflow.taskk.enums.TaskLinkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLinkRepository extends JpaRepository<TaskLink, Long>, JpaSpecificationExecutor<TaskLink> {

    boolean existsBySourceTaskIdAndTargetTaskIdAndLinkType(
            Long sourceTaskId,
            Long targetTaskId,
            TaskLinkType linkType
    );

}
