package com.taskflow.taskk.specification;

import com.taskflow.taskk.entity.TaskComment;
import org.springframework.data.jpa.domain.Specification;

public class TaskCommentSpecification {

    public static Specification<TaskComment> hasTaskId(Long taskId) {
        return (root, query, cb) -> {

            if (taskId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("taskId"), taskId);
        };
    }

    public static Specification<TaskComment> hasCreatedBy(Long createdBy) {
        return (root, query, cb) -> {

            if (createdBy == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("createdBy"), createdBy);
        };
    }

    public static Specification<TaskComment> search(String search) {
        return (root, query, cb) -> {

            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }

            String keyword = "%" + search.trim().toLowerCase() + "%";

            return cb.like(
                    cb.lower(root.get("content")), keyword
            );
        };
    }
}