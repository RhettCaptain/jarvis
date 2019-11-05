package com.github.rhettcaptain.jarvis.todo.dao;

import com.github.rhettcaptain.jarvis.todo.consts.ItemType;
import com.github.rhettcaptain.jarvis.todo.dto.ToDoItemDto;
import com.github.rhettcaptain.jarvis.todo.vo.ToDoItemVo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ToDoItemDao {
    List<ToDoItemDto> getItemsByType(ItemType type);

    List<ToDoItemDto> getArchiveByDate(LocalDate date);

    void deleteItem(ToDoItemDto itemDto);

    void addItem(ToDoItemDto itemDto);

    void updateItem(ToDoItemDto itemDto);

    void archiveDoneItems(String archiveName);
}
