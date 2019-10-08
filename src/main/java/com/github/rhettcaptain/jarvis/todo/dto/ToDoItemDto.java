package com.github.rhettcaptain.jarvis.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemDto {
    private UUID uuid;
    private String title;
    private String detail;
    private long createdTime;
    private long dueTime;
    private long costTime;

    enum Status {
        IMP_URT,
        IMP_NOTURT,
        NOTIMP_URT,
        NOTIMP_NOTURT,
        DOING,
        DONE,
        PENDING
    }
}
