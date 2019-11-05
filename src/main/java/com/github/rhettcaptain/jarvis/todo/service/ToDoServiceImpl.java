package com.github.rhettcaptain.jarvis.todo.service;

import com.github.rhettcaptain.jarvis.todo.consts.ItemType;
import com.github.rhettcaptain.jarvis.todo.consts.ToDoConst;
import com.github.rhettcaptain.jarvis.todo.dao.ToDoItemDao;
import com.github.rhettcaptain.jarvis.todo.dto.ToDoItemDto;
import com.github.rhettcaptain.jarvis.todo.vo.ToDoItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class ToDoServiceImpl implements ToDoService {
    @Autowired
    private ToDoItemDao itemDao;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ToDoConst.DATETIME_FORMAT);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ToDoConst.TIME_FORMAT);

    @Override
    public List<ToDoItemVo> getItemsByType(ItemType type) {
        return itemDao.getItemsByType(type).stream().map(this::convertItemDtoToVo).collect(Collectors.toList());
    }

    @Override
    public void deleteItem(ToDoItemVo itemVo) {
        itemDao.deleteItem(convertItemVoToDto(itemVo));
    }

    @Override
    public void addItem(ToDoItemVo itemVo) {
        itemDao.addItem(convertItemVoToDto(itemVo));
    }

    @Override
    public void updateItem(ToDoItemVo itemVo) {
        itemDao.updateItem(convertItemVoToDto(itemVo));
    }

    @Override
    public List<ToDoItemVo> getArchive(LocalDate startDate, LocalDate endDate) {
        List<ToDoItemVo> allArchived = new ArrayList<>();
        if(startDate == null){
            return allArchived;
        }
        if(endDate == null){
            return itemDao.getArchiveByDate(startDate).stream().map(dto -> convertItemDtoToVo(dto)).collect(Collectors.toList());
        }
        Calendar formerCalendar = Calendar.getInstance();
        formerCalendar.set(startDate.getYear(),startDate.getMonthValue(),startDate.getDayOfMonth());

        Calendar latterCalendar = Calendar.getInstance();
        latterCalendar.set(endDate.getYear(),endDate.getMonthValue(),endDate.getDayOfMonth());

        while (!formerCalendar.after(latterCalendar)) {
            List<ToDoItemVo> oneDayVos = itemDao.getArchiveByDate(LocalDateTime.ofInstant(formerCalendar.toInstant(),
                    ZoneId.systemDefault()).toLocalDate()).stream().map(this::convertItemDtoToVo).collect(Collectors.toList());
            allArchived.addAll(oneDayVos);
        }
        return allArchived;
    }

    @Scheduled(cron = "0 59 23 * * ?")
    private void autoArchive() {
        String archiveName = LocalDate.now().format(DateTimeFormatter.ofPattern(ToDoConst.DATE_FORMAT));
        itemDao.archiveDoneItems(archiveName);
    }

    private ToDoItemVo convertItemDtoToVo(ToDoItemDto itemDto) {
        return ToDoItemVo.builder().type(itemDto.getType()).uuid(itemDto.getUuid().toString())
                .title(itemDto.getTitle()).detail(itemDto.getDetail())
                .costTime(itemDto.getCostTime() == null ? null : itemDto.getCostTime().format(timeFormatter))
                .createdTime(itemDto.getCreatedTime() == null ? null : itemDto.getCreatedTime().format(dateTimeFormatter))
                .dueTime(itemDto.getDueTime() == null ? null : itemDto.getDueTime().format(dateTimeFormatter))
                .finishedTime(itemDto.getFinishedTime() == null ? null : itemDto.getFinishedTime().format(dateTimeFormatter))
                .build();
    }

    private ToDoItemDto convertItemVoToDto(ToDoItemVo itemVo) {
        return ToDoItemDto.builder().type(itemVo.getType())
                .uuid(StringUtils.isEmpty(itemVo.getUuid()) ? null : UUID.fromString(itemVo.getUuid()))
                .costTime(StringUtils.isEmpty(itemVo.getCostTime()) ? null : LocalTime.parse(itemVo.getCostTime(), timeFormatter))
                .createdTime(StringUtils.isEmpty(itemVo.getCreatedTime()) ? null : LocalDateTime.parse(itemVo.getCreatedTime(), dateTimeFormatter))
                .title(itemVo.getTitle()).detail(itemVo.getDetail())
                .dueTime(StringUtils.isEmpty(itemVo.getDueTime()) ? null : LocalDateTime.parse(itemVo.getDueTime(), dateTimeFormatter))
                .finishedTime(StringUtils.isEmpty(itemVo.getFinishedTime()) ? null : LocalDateTime.parse(itemVo.getFinishedTime(), dateTimeFormatter))
                .build();
    }
}
