package com.github.rhettcaptain.jarvis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.rhettcaptain.jarvis.vo.BookmarkVo;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	public List<BookmarkVo> getAllBookmark() {
		List<BookmarkVo> bookmarks = new ArrayList<BookmarkVo>();
		BookmarkVo bm1 = BookmarkVo.builder().title("bm1").tags(Arrays.asList("a","d")).build();
		BookmarkVo bm2 = BookmarkVo.builder().title("bm2").tags(Arrays.asList("b")).build();
		bookmarks.add(bm1);
		bookmarks.add(bm2);
		return bookmarks;
	}
}
