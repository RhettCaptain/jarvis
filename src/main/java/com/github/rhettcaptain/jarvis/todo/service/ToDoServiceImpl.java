package com.github.rhettcaptain.jarvis.todo.service;

import com.github.rhettcaptain.jarvis.todo.consts.ItemType;
import com.github.rhettcaptain.jarvis.todo.vo.ToDoItemVo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ToDoServiceImpl implements ToDoService {
    @Override public List<ToDoItemVo> getItemsByType(ItemType type) {
        return Arrays.asList(ToDoItemVo.builder().uuid(UUID.randomUUID().toString()).title(type.name()).type(type.name()).build());
    }

    @Override public void deleteItem(UUID uuid) {
        System.out.println("delete " + uuid);
    }

    @Override public void addItem(ToDoItemVo itemVo) {
        System.out.println("add " + itemVo);
    }

    @Override public void updateItem(ToDoItemVo itemVo) {
        System.out.println("update " + itemVo);
    }
}
