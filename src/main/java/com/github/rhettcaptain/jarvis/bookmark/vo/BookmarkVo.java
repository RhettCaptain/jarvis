package com.github.rhettcaptain.jarvis.bookmark.vo;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String title;
	private String tags;        
	private String href;
}
