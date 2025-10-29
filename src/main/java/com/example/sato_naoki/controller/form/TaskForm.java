package com.example.sato_naoki.controller.form;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
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
//    @Pattern(regexp = "[a-zA-Z0-9\u3040-\u309F\u30A0-\u30FF]*")
    private String content;

    private int status;

    @NotNull
    @FutureOrPresent
    private LocalDate limitedDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
