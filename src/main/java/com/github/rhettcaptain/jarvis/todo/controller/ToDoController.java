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
import java.util.UUID;

@Controller
@RequestMapping("todo")
public class ToDoController {
    private static String TYPE_KEY = "type";
    private static String UUID_KEY = "uuid";

    /**
     *
     * @param ItemType {TYPE_KEY: }
     * @return
     */
    @RequestMapping(value = "getitembytype", method = RequestMethod.POST)
    @ResponseBody
    public List<ToDoItemVo> getItemsByType(@RequestBody Map<String,String> ItemType){
        return Arrays.asList(ToDoItemVo.builder().uuid(UUID.randomUUID().toString()).title("todo1" + ItemType.get(TYPE_KEY)).build(),ToDoItemVo.builder().uuid(UUID.randomUUID().toString()).title("todo2" + ItemType.get(TYPE_KEY)).build());
    }

    /**
     *
     * @param itemVo
     * @return
     */
    @RequestMapping(value = "deleteitem", method = RequestMethod.POST)
    @ResponseBody
    public void deleteItem(@RequestBody ToDoItemVo itemVo){
        System.out.println("delete"+itemVo);
    }

    @RequestMapping(value = "additem", method = RequestMethod.POST)
    @ResponseBody
    public void addItem(@RequestBody ToDoItemVo itemVo){
        System.out.println("add"+itemVo);
    }

    /**
     *
     * @param ItemType  {TYPE_KEY: }
     */
    @RequestMapping(value = "createnewitem", method = RequestMethod.POST)
    @ResponseBody
    public ToDoItemVo createNewItem(@RequestBody Map<String,String> ItemType){
        return ToDoItemVo.builder().uuid(UUID.randomUUID().toString()).type(ItemType.get(TYPE_KEY)).build();
    }
}
