package com.github.rhettcaptain.jarvis.bookmark.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.github.rhettcaptain.jarvis.bookmark.service.BookmarkService;
import com.github.rhettcaptain.jarvis.bookmark.vo.BookmarkVo;


@Controller
@RequestMapping("bookmark")
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService;

	private final static String SEARCH_KEY = "keywords";
	
	@RequestMapping("")
	@ResponseBody
	public String index() {
		return "hello world";
	}
	
	@RequestMapping(value = "getbookmarks",method = RequestMethod.POST)
	@ResponseBody
	public List<BookmarkVo> getBookmarks(@RequestBody Map<String,String> keywords){
		if(StringUtils.isEmpty(keywords.get(SEARCH_KEY))){
			return bookmarkService.getAllBookmark();
		}
		return bookmarkService.getBookmarkWithKeywords(keywords.get(SEARCH_KEY));
	}
	
	@RequestMapping(value = "insertbookmark",method = RequestMethod.POST)
	@ResponseBody
	public void insertBookmark(@RequestBody BookmarkVo bookmarkVo) {
		bookmarkService.insertBookmark(bookmarkVo);
	}
	
	@RequestMapping(value = "updatebookmark",method = RequestMethod.POST)
	@ResponseBody
	public void updateBookmark(@RequestBody BookmarkVo bookmarkVo) {
		bookmarkService.updateBookmark(bookmarkVo);
	}
	
	@RequestMapping(value = "deletebookmark",method = RequestMethod.POST)
	@ResponseBody
	public void deleteBookmark(@RequestBody BookmarkVo bookmarkVo) {
		bookmarkService.deleteBookmark(bookmarkVo.getUuid());
	}
}
