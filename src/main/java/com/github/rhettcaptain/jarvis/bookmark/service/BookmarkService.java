package com.github.rhettcaptain.jarvis.bookmark.service;

import java.util.List;
import java.util.UUID;

import com.github.rhettcaptain.jarvis.bookmark.vo.BookmarkVo;

public interface BookmarkService {
	public List<BookmarkVo> getAllBookmark();
	public List<BookmarkVo> getBookmarkWithKeywords(String keywords);
	public void insertBookmark(BookmarkVo bookmarkVo);
	public void updateBookmark(BookmarkVo bookmarkVo);
	public void deleteBookmark(String bookmarkId);
}
