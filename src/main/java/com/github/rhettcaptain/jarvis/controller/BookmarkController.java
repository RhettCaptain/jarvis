package com.github.rhettcaptain.jarvis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.github.rhettcaptain.jarvis.service.BookmarkService;
import com.github.rhettcaptain.jarvis.vo.BookmarkVo;

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
}
