package com.github.rhettcaptain.jarvis.todo.vo;

import com.github.rhettcaptain.jarvis.todo.consts.TodoConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String title;
    private String detail;

    // use format com.github.rhettcaptain.jarvis.todo.consts.TodoConst.DATETIME_FORMAT;
    private String createdTime;
    private String dueTime;
    private String finishedTime;

    // use format com.github.rhettcaptain.jarvis.todo.consts.TodoConst.TIME_FORMAT;
    private String costTime;
    // use com.github.rhettcaptain.jarvis.todo.consts.ItemType value
    private String type;

}
