package com.github.rhettcaptain.jarvis.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookmarkVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String title;
	private List<String> tags;
	private String href;
}
