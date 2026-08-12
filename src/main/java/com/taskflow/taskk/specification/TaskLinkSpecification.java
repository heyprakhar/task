package com.taskflow.taskk.specification;

import com.taskflow.taskk.entity.TaskLink;
import com.taskflow.taskk.enums.TaskLinkType;
import org.springframework.data.jpa.domain.Specification;

public class TaskLinkSpecification {

    public static Specification<TaskLink> hasSourceTaskId(Long sourceTaskId) {
        return (root, query, cb) -> {
            if (sourceTaskId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("sourceTaskId"), sourceTaskId);
        };
    }

    public static Specification<TaskLink> hasTargetTaskId(Long targetTaskId) {
        return (root, query, cb) -> {
            if (targetTaskId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("targetTaskId"), targetTaskId);
        };
    }

    public static Specification<TaskLink> hasLinkType(TaskLinkType linkType) {
        return (root, query, cb) -> {
            if (linkType == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("linkType"), linkType);
        };
    }

    public static Specification<TaskLink> involvesTask(Long taskId) {
        return (root, query, cb) -> {
            if (taskId == null) {
                return cb.conjunction();
            }

            return cb.or(
                    cb.equal(root.get("sourceTaskId"), taskId),
                    cb.equal(root.get("targetTaskId"), taskId)
            );
        };
    }
}