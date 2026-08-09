package com.taskflow.taskk.specification;

import com.taskflow.taskk.entity.Task;
import com.taskflow.taskk.enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TaskSpecification {

    public static Specification<Task> entityStateIn(List<String> statuses) {
        return (root, query, cb) -> {
            if (statuses == null || statuses.isEmpty()) {
                return cb.conjunction();
            }

            List<TaskStatus> taskStatuses = statuses.stream()
                    .map(TaskStatus::valueOf)
                    .toList();

            return root.get("status").in(taskStatuses);
        };
    }
}
