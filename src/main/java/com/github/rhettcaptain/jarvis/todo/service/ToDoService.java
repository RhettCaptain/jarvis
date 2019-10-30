package com.github.rhettcaptain.jarvis.todo.service;

import com.github.rhettcaptain.jarvis.todo.consts.ItemType;
import com.github.rhettcaptain.jarvis.todo.vo.ToDoItemVo;

import java.util.List;
import java.util.UUID;

public interface ToDoService {
    List<ToDoItemVo> getItemsByType(ItemType type);

    void deleteItem(UUID uuid);

    void addItem(ToDoItemVo itemVo);

    void updateItem(ToDoItemVo itemVo);
}
