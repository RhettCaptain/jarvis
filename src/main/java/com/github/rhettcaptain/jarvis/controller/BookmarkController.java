package com.github.rhettcaptain.jarvis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.rhettcaptain.jarvis.service.BookmarkService;
import com.github.rhettcaptain.jarvis.vo.BookmarkVo;

@Controller
@RequestMapping("bookmark")
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService; 
	
	@RequestMapping("")
	@ResponseBody
	public String index() {
		return "hello world";
	}
	
	@RequestMapping(value = "getallbookmark",method = RequestMethod.POST)
	@ResponseBody
	public List<BookmarkVo> getAllBookmark(){
		return bookmarkService.getAllBookmark();
	}
}
