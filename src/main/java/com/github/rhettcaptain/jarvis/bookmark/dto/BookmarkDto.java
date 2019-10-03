package com.github.rhettcaptain.jarvis.bookmark.dto;

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
public class BookmarkDto {
	private UUID uuid;
	private String title;
	private List<String> tags;
	private String href;
}
