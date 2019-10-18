package com.github.rhettcaptain.jarvis.todo.controller;

import com.github.rhettcaptain.jarvis.todo.vo.ToDoItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("todo")
public class ToDoController {
    @RequestMapping(value = "getitembytype", method = RequestMethod.POST)
    @ResponseBody
    public List<ToDoItemVo> getItemsByType(@RequestBody Map<String,String> ItemType){
        return Arrays.asList(ToDoItemVo.builder().title("todo1").build(),ToDoItemVo.builder().title("todo2").build());
    }
}
