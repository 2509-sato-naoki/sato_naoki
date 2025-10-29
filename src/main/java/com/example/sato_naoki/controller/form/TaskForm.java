package com.example.sato_naoki.controller.form;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class TaskForm {
    private int id;

    @NotBlank(message = "タスクを入力してください")
    @Length(max = 140, message = "タスクは140文字以内で入力してください")
    // @Pattern(regexp = "[a-zA-Z0-9\u3040-\u309F\u30A0-\u30FF]*")
    // asserttrueは全角があるかないかをチェックするだけ contentが全角1文字だけかどうかを確認する　equalsメソッド
    private String content;

    @AssertTrue(message = "タスクを入力してください")
    public boolean isContentValidated() {
        if (content.equals("　")) {
            return false;
        } else {
            return true;
        }
    }

    private int status;

    @NotNull(message = "期限を設定してください")
    @FutureOrPresent(message = "無効な日付です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitedDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

//遅れが出た際は、遅れてる事実と遅れた原因、状況を詳しく報告する、その遅れを解消する方法、手段があるのであればそれを報告する
//手段、方法がないときは遅れを取り戻せないのでどう対応すればいいのか相談したいという　遅れが解消できないのであれば相談する