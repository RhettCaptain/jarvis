package com.github.rhettcaptain.jarvis.bookmark.dao;

import java.util.List;
import java.util.UUID;

import com.github.rhettcaptain.jarvis.bookmark.dto.BookmarkDto;


public interface BookmarkDao {
	public List<BookmarkDto> getAllBookmarks();
	
	public void insertBookmark(BookmarkDto bookmarkDto);
	
	public void updateBookmark(BookmarkDto bookmarkDto);
	
	public void deleteBookmark(UUID uuid);
	
}
