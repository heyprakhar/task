package com.taskflow.taskk.specification;

import com.taskflow.taskk.entity.TaskActivity;
import com.taskflow.taskk.enums.TaskActivityActorType;
import com.taskflow.taskk.enums.TaskActivityType;
import org.springframework.data.jpa.domain.Specification;

public class TaskActivitySpecification {

    private TaskActivitySpecification() {
    }

    public static Specification<TaskActivity> hasTaskId(Long taskId) {

        return (root, query, cb) -> {

            if (taskId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("taskId"), taskId);
        };
    }

    public static Specification<TaskActivity> hasActivityType(TaskActivityType activityType) {

        return (root, query, cb) -> {

            if (activityType == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("activityType"), activityType);
        };
    }

    public static Specification<TaskActivity> hasPerformedBy(Long performedBy) {

        return (root, query, cb) -> {

            if (performedBy == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("performedBy"), performedBy);
        };
    }

    public static Specification<TaskActivity> hasActorType(TaskActivityActorType actorType) {

        return (root, query, cb) -> {

            if (actorType == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("taskActivityActorType"), actorType);
        };

    }
}