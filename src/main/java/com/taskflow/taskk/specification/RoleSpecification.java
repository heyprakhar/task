package com.taskflow.taskk.specification;

import com.taskflow.taskk.entity.Role;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {

    public static Specification<Role> hasActiveStatus(Boolean status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("active"), status);
        };
    }

    public static Specification<Role> search(String search) {
        return (root, query, cb) -> {

            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }

            String keyword = "%" + search.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), keyword),
                    cb.like(cb.lower(root.get("description")), keyword)
            );
        };
    }
}
