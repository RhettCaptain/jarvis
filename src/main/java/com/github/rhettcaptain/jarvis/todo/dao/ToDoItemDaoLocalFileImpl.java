package com.github.rhettcaptain.jarvis.todo.dao;

import com.github.rhettcaptain.jarvis.todo.consts.ItemType;
import com.github.rhettcaptain.jarvis.todo.consts.ToDoConst;
import com.github.rhettcaptain.jarvis.todo.dto.ToDoItemDto;
import com.github.rhettcaptain.jarvis.util.ExceptionUtil;
import com.github.rhettcaptain.jarvis.util.jackson.JarvisObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ToDoItemDaoLocalFileImpl implements ToDoItemDao {
    private final static String JARVIS_DB_PATH = "jarvis_db";
    private final static String DONE_ARCHIVE_PATH = "jarvis_db/done_archive";
    private final static String BACKUP_PATH = "jarvis_db/todo_backup";
    @Autowired
    private JarvisObjectMapper mapper;

    @Override
    public List<ToDoItemDto> getItemsByType(ItemType type) {
        List<ToDoItemDto> toDoItemDtoList = new ArrayList<>();
        File dir = new File(JARVIS_DB_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(getTablePathByItem(type));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("IOException while create file {}", getItemsByType(type));
                log.error(ExceptionUtil.getStackTrace(e));
            }
        }
        if (file.length() == 0) {
            return toDoItemDtoList;
        }
        try {
//            new BufferedReader(new InputStreamReader(new FileInputStream(file))).lines().forEach(System.out::println);
            toDoItemDtoList = mapper.readValue(file, mapper.getTypeFactory().constructParametricType(ArrayList.class, ToDoItemDto.class));
        } catch (IOException e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
        return toDoItemDtoList;
    }

    @Override
    public List<ToDoItemDto> getArchiveByDate(LocalDate date) {
        List<ToDoItemDto> archive = new ArrayList<>();
        File dir = new File(JARVIS_DB_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(DONE_ARCHIVE_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(DONE_ARCHIVE_PATH + "/" + date.format(DateTimeFormatter.ofPattern(ToDoConst.DATE_FORMAT)));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("IOException while create file {}", file.getName());
                log.error(ExceptionUtil.getStackTrace(e));
            }
        }
        if (file.length() == 0) {
            return archive;
        }
        try {
             archive = mapper.readValue(file, mapper.getTypeFactory().constructParametricType(ArrayList.class, ToDoItemDto.class));
        } catch (IOException e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
        return archive;
    }

    @Override
    public void deleteItem(ToDoItemDto itemDto) {
        List<ToDoItemDto> allItems = getItemsByType(ItemType.valueOf(itemDto.getType()));
        List<ToDoItemDto> nohitItems = allItems.stream().filter(item -> !item.getUuid().equals(itemDto.getUuid()))
                .collect(Collectors.toList());
        if (nohitItems.size() == allItems.size()) {
            log.warn("No item with UUID {}, delete nothing", itemDto.getUuid());
        }
        if ((allItems.size() - nohitItems.size()) > 1) {
            log.warn("There are more than one item with UUID {}, delete all", itemDto.getUuid());
        }
        try {
            mapper.writeValue(new File(getTablePathByItem(itemDto.getType())), nohitItems);
        } catch (IOException e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }

    }

    @Override
    public void addItem(ToDoItemDto itemDto) {
        List<ToDoItemDto> allItems = getItemsByType(ItemType.valueOf(itemDto.getType()));
        if (itemDto.getUuid() == null) {
            itemDto.setUuid(UUID.randomUUID());
            log.warn("UUID in bookmarkDto {} is null, set random UUID {}", itemDto, itemDto.getUuid());
        }
        allItems.add(itemDto);
        try {
            mapper.writeValue(new File(getTablePathByItem(itemDto.getType())), allItems);
        } catch (IOException e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    @Override
    public void updateItem(ToDoItemDto itemDto) {
        List<ToDoItemDto> allItems = getItemsByType(ItemType.valueOf(itemDto.getType()));
        List<ToDoItemDto> nohitItems = allItems.stream()
                .filter(bookmark -> !bookmark.getUuid().equals(itemDto.getUuid())).collect(Collectors.toList());
        if (nohitItems.size() == allItems.size()) {
            log.warn("No item with UUID {}, insert this record", itemDto.getUuid());
            return;
        }
        if ((allItems.size() - nohitItems.size()) > 1) {
            log.warn("There are more than one items with UUID {}, update one, delete others",
                    itemDto.getUuid());
        }
        nohitItems.add(itemDto);
        try {
            mapper.writeValue(new File(getTablePathByItem(itemDto.getType())), nohitItems);
        } catch (IOException e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }

    @Override
    public void archiveDoneItems(String archiveName) {
        File archDir = new File(DONE_ARCHIVE_PATH);
        if (!archDir.exists()) {
            archDir.mkdir();
        }
        File doneFile = new File(getTablePathByItem(ItemType.DONE));
        File archFile = new File(DONE_ARCHIVE_PATH + "/" + archiveName);
        if (!doneFile.exists()) {
            try {
                doneFile.createNewFile();
            } catch (IOException e) {
                log.error("IOException while create file {}", getTablePathByItem(ItemType.DONE));
                log.error(ExceptionUtil.getStackTrace(e));
            }
        }
        try{
            FileCopyUtils.copy(doneFile, archFile);
            doneFile.delete();
        }catch (IOException e){
            log.warn(ExceptionUtil.getStackTrace(e));
        }

        backup();
    }

    private String getTablePathByItem(String type){
        return JARVIS_DB_PATH + "/" + type.toLowerCase();
    }

    private String getTablePathByItem(ItemType type){
        return getTablePathByItem(type.name());
    }

    private void backup(){
        File archDir = new File(BACKUP_PATH);
        if (!archDir.exists()) {
            archDir.mkdir();
        }
        Arrays.asList("future","template","doing","pending","todo").stream().forEach(type -> {
            File doneFile = new File(JARVIS_DB_PATH +  "/" + type);
            File archFile = new File(BACKUP_PATH + "/" + type);
            if (doneFile.exists()) {
                try{
                    FileCopyUtils.copy(doneFile, archFile);
                }catch (IOException e){
                    log.warn(ExceptionUtil.getStackTrace(e));
                }
            }
        });
    }
}
