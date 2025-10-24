package com.example.sato_naoki.controller.form;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskForm {
    private int id;
    private String content;
    private int status;
    private LocalDateTime limitedDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
