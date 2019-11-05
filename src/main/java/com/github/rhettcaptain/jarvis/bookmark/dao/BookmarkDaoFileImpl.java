package com.github.rhettcaptain.jarvis.bookmark.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.rhettcaptain.jarvis.bookmark.dto.BookmarkDto;
import com.github.rhettcaptain.jarvis.util.ExceptionUtil;
import com.github.rhettcaptain.jarvis.util.jackson.JarvisObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("bookmarkDaoForFile")
public class BookmarkDaoFileImpl implements BookmarkDao {

	private final static String JARVIS_DB_PATH = "jarvis_db";
	private final static String BOOKMARK_FILE_PATH = JARVIS_DB_PATH + "/bookmark_tb";
	@Autowired
	private JarvisObjectMapper mapper;

	@Override
	public List<BookmarkDto> getAllBookmarks() {
		List<BookmarkDto> allBookmarks = new ArrayList<BookmarkDto>();
		File dir = new File(JARVIS_DB_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(BOOKMARK_FILE_PATH);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error("IOException while create file {}", BOOKMARK_FILE_PATH);
				log.error(ExceptionUtil.getStackTrace(e));
			}
		}
		if (file.length() == 0) {
			return allBookmarks;
		}
		try {
			allBookmarks = mapper.readValue(file, mapper.getTypeFactory().constructParametricType(ArrayList.class, BookmarkDto.class));
		} catch (IOException e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}
		return allBookmarks;
	}

	@Override
	public synchronized void insertBookmark(BookmarkDto bookmarkDto) {
		List<BookmarkDto> allBookmarks = getAllBookmarks();
		if (bookmarkDto.getUuid() == null) {
			bookmarkDto.setUuid(UUID.randomUUID());
			log.warn("UUID in bookmarkDto {} is null, set random UUID {}", bookmarkDto, bookmarkDto.getUuid());
		}
		allBookmarks.add(bookmarkDto);
		try {
			mapper.writeValue(new File(BOOKMARK_FILE_PATH), allBookmarks);
		} catch (IOException e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}
	}

	@Override
	public void updateBookmark(BookmarkDto bookmarkDto) {
		List<BookmarkDto> allBookmarks = getAllBookmarks();
		List<BookmarkDto> nohitBookmarks = allBookmarks.stream()
				.filter(bookmark -> !bookmark.getUuid().equals(bookmarkDto.getUuid())).collect(Collectors.toList());
		if (nohitBookmarks.size() == allBookmarks.size()) {
			log.warn("No bookmark with UUID {}, insert this record", bookmarkDto.getUuid());
			return;
		}
		if ((allBookmarks.size() - nohitBookmarks.size()) > 1) {
			log.warn("There are more than one bookmarks with UUID {}, update one, delete others",
					bookmarkDto.getUuid());
		}
		nohitBookmarks.add(bookmarkDto);
		try {
			mapper.writeValue(new File(BOOKMARK_FILE_PATH), nohitBookmarks);
		} catch (IOException e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}

	}

	@Override
	public void deleteBookmark(UUID uuid) {
		List<BookmarkDto> allBookmarks = getAllBookmarks();
		List<BookmarkDto> nohitBookmarks = allBookmarks.stream().filter(bookmark -> !bookmark.getUuid().equals(uuid))
				.collect(Collectors.toList());
		if (nohitBookmarks.size() == allBookmarks.size()) {
			log.warn("No bookmark with UUID {}, delete nothing", uuid);
		}
		if ((allBookmarks.size() - nohitBookmarks.size()) > 1) {
			log.warn("There are more than one bookmarks with UUID {}, delete all", uuid);
		}
		try {
			mapper.writeValue(new File(BOOKMARK_FILE_PATH), nohitBookmarks);
		} catch (IOException e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}

	}
}
