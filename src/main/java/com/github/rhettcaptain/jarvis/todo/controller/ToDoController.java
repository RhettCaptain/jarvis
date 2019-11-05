package com.github.rhettcaptain.jarvis.todo.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.rhettcaptain.jarvis.todo.consts.ItemType;
import com.github.rhettcaptain.jarvis.todo.consts.ToDoConst;
import com.github.rhettcaptain.jarvis.todo.service.ToDoService;
import com.github.rhettcaptain.jarvis.todo.vo.ToDoItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String todo() {
        return "todo.html";
    }

    /**
     * @param itemType {TYPE_KEY: }
     * @return
     */
    @RequestMapping(value = "getitembytype", method = RequestMethod.POST)
    @ResponseBody
    public List<ToDoItemVo> getItemsByType(@RequestBody Map<String, String> itemType) {
        String typeValue = itemType.get(TYPE_KEY).toUpperCase();
        ItemType type;
        try {
            type = ItemType.valueOf(typeValue);
        } catch (IllegalArgumentException e) {
            log.warn("invalid itemType {} to getItems", typeValue);
            return new ArrayList<>();
        }
        return toDoService.getItemsByType(type);
    }

    /**
     * @param itemVo
     * @return
     */
    @RequestMapping(value = "deleteitem", method = RequestMethod.POST)
    @ResponseBody
    public void deleteItem(@RequestBody ToDoItemVo itemVo) {
        toDoService.deleteItem(itemVo);
    }

    /**
     * @param itemVo
     */
    @RequestMapping(value = "additem", method = RequestMethod.POST)
    @ResponseBody
    public void addItem(@RequestBody ToDoItemVo itemVo) {
        toDoService.addItem(itemVo);
    }

    /**
     * @param itemVo
     */
    @RequestMapping(value = "updateitem", method = RequestMethod.POST)
    @ResponseBody
    public void updateItem(@RequestBody ToDoItemVo itemVo) {
        toDoService.updateItem(itemVo);
    }

    /**
     * @param itemType {TYPE_KEY: }
     */
    @RequestMapping(value = "createnewitem", method = RequestMethod.POST)
    @ResponseBody
    public ToDoItemVo createNewItem(@RequestBody Map<String, String> itemType) {
        String typeValue = itemType.get(TYPE_KEY).toUpperCase();
        ItemType type;
        try {
            type = ItemType.valueOf(typeValue);
        } catch (IllegalArgumentException e) {
            log.warn("invalid itemType {} to getItems", typeValue);
            return null;
        }
        ToDoItemVo newItemVo = ToDoItemVo.builder().uuid(UUID.randomUUID().toString()).type(itemType.get(TYPE_KEY))
                .createdTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(ToDoConst.DATETIME_FORMAT)))
                .costTime("00:00:00").build();
        toDoService.addItem(newItemVo);
        return newItemVo;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "getarchive", method = RequestMethod.GET)
    @ResponseBody
    public List<ToDoItemVo> getArchive(@RequestParam(value = "startDate") String startDateParam, @RequestParam(value = "endDate") String endDateParam) {
        LocalDate startDate = null;
        LocalDate endDate = null;
        try{
            startDate = LocalDate.parse(startDateParam, DateTimeFormatter.ofPattern(ToDoConst.DATE_FORMAT));
        } catch (IllegalArgumentException e){
            log.warn("illegal startDate {}, use yyyy-MM-dd format", startDateParam);
            return new ArrayList<>();
        }
        try{
            endDate = LocalDate.parse(endDateParam, DateTimeFormatter.ofPattern(ToDoConst.DATE_FORMAT));
        } catch (IllegalArgumentException e){
            log.info("illegal endDate {}, seen as null", startDateParam);
        }

        return toDoService.getArchive(startDate, endDate);
    }

}
