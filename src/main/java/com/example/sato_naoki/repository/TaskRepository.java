package com.example.sato_naoki.repository;

import com.example.sato_naoki.repository.entity.Task;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
    List<Task> findBylimitedDateBetweenAndContentAndStatusOrderByLimitedDateAsc(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String content,
            Integer status);
    List<Task> findByLimitedDateBetweenOrderByLimitedDateAsc(LocalDateTime startDateTime,
                                                             LocalDateTime endDateTime);

    List<Task> findByLimitedDateBetweenAndContentOrderByLimitedDateAsc(LocalDateTime startDateTime,
                                                                       LocalDateTime endDateTime,
                                                                       String content);
    List<Task> findByLimitedDateBetweenAndStatusOrderByLimitedDateAsc(LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime,
                                                                      Integer status);

}
