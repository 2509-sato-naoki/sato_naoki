package com.example.sato_naoki.specification;
import com.example.sato_naoki.repository.entity.Task;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    public Specification<Task> containContents(String content) {
        return StringUtils.isEmpty(content) ? null : new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name"), "%" + content + "%");
            }
        };
    }

    public Specification<Task> containStatus(String status) {
        return StringUtils.isEmpty(status) ? null : new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name"), "%" + status + "%");
            }
        };
    }
}
