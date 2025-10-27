package com.example.sato_naoki.controller.form;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class TaskForm {
    private int id;

    @NotBlank(message = "投稿内容を入力してください")
    private String content;

    private int status;

    @NotNull
    private LocalDate limitedDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
