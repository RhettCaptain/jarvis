package com.github.rhettcaptain.jarvis.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemDto {
    private UUID uuid;
    private String title;
    private String detail;
    private LocalDateTime createdTime;
    private LocalDateTime dueTime;
    private LocalDateTime finishedTime;
    private LocalTime costTime;
    // use com.github.rhettcaptain.jarvis.todo.consts.ItemType value
    private String type;

}
