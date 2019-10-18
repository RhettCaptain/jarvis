package com.github.rhettcaptain.jarvis.todo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String title;
    private String detail;
    private long createdTime;
    private long dueTime;
    private long finishedTime;
    private long costTime;
    private String type;
}
