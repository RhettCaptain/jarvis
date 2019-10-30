package com.github.rhettcaptain.jarvis.todo.controller;

import com.github.rhettcaptain.jarvis.todo.consts.ItemType;
import com.github.rhettcaptain.jarvis.todo.service.ToDoService;
import com.github.rhettcaptain.jarvis.todo.vo.ToDoItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("todo")
@Slf4j
public class ToDoController {
    private static String TYPE_KEY = "type";
    private static String UUID_KEY = "uuid";

    @Autowired
    private ToDoService toDoService;

    /**
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String todo(){
        return "todo.html";
    }

    /**
     *
     * @param itemType {TYPE_KEY: }
     * @return
     */
    @RequestMapping(value = "getitembytype", method = RequestMethod.POST)
    @ResponseBody
    public List<ToDoItemVo> getItemsByType(@RequestBody Map<String,String> itemType){
        String typeValue = itemType.get(TYPE_KEY).toUpperCase();
        ItemType type;
        try{
            type = ItemType.valueOf(typeValue);
        }catch (IllegalArgumentException e){
            log.warn("invalid itemType {} to getItems", typeValue);
            return new ArrayList<>();
        }
        return toDoService.getItemsByType(type);
    }

    /**
     *
     * @param itemVo
     * @return
     */
    @RequestMapping(value = "deleteitem", method = RequestMethod.POST)
    @ResponseBody
    public void deleteItem(@RequestBody ToDoItemVo itemVo){
        UUID itemId;
        try{
            itemId = UUID.fromString(itemVo.getUuid());
        }catch (IllegalArgumentException | NullPointerException e){
            log.warn("invalid uuid {} to delete", itemVo.getUuid());
            return;
        }
        toDoService.deleteItem(itemId);
    }

    public static void main(String[] args){
        UUID.fromString("dd");
    }

    /**
     *
     * @param itemVo
     */
    @RequestMapping(value = "additem", method = RequestMethod.POST)
    @ResponseBody
    public void addItem(@RequestBody ToDoItemVo itemVo){
        toDoService.addItem(itemVo);
    }

    /**
     *
     * @param itemVo
     */
    @RequestMapping(value = "updateitem", method = RequestMethod.POST)
    @ResponseBody
    public void updateItem(@RequestBody ToDoItemVo itemVo){
        toDoService.updateItem(itemVo);
    }

    /**
     *
     * @param itemType  {TYPE_KEY: }
     */
    @RequestMapping(value = "createnewitem", method = RequestMethod.POST)
    @ResponseBody
    public ToDoItemVo createNewItem(@RequestBody Map<String,String> itemType){
        String typeValue = itemType.get(TYPE_KEY).toUpperCase();
        ItemType type;
        try{
            type = ItemType.valueOf(typeValue);
        }catch (IllegalArgumentException e){
            log.warn("invalid itemType {} to getItems", typeValue);
            return null;
        }
        ToDoItemVo newItemVo = ToDoItemVo.builder().uuid(UUID.randomUUID().toString()).type(itemType.get(TYPE_KEY)).build();
        toDoService.addItem(newItemVo);
        return newItemVo;
    }
}
