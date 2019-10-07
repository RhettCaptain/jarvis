package com.github.rhettcaptain.jarvis.bookmark.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.rhettcaptain.jarvis.bookmark.dao.BookmarkDao;
import com.github.rhettcaptain.jarvis.bookmark.dto.BookmarkDto;
import com.github.rhettcaptain.jarvis.bookmark.vo.BookmarkVo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {

	@Autowired
	private BookmarkDao bookmarkDao;

	private static final String TAG_SPLITER = ";";

	@Override
	public List<BookmarkVo> getAllBookmark() {
		List<BookmarkDto> bookmarkDtos = bookmarkDao.getAllBookmarks();

		List<BookmarkVo> bookmarkVos = bookmarkDtos.stream()
				.map(dto -> BookmarkVo.builder().uuid(dto.getUuid().toString()).title(dto.getTitle())
						.tags(convertTagListToTags(dto.getTags())).href(dto.getHref()).build())
				.collect(Collectors.toList());

		bookmarkVos.sort(new Comparator<BookmarkVo>() {
			@Override
			public int compare(BookmarkVo o1, BookmarkVo o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}

		});
		return bookmarkVos;
	}

	@Override
	public List<BookmarkVo> getBookmarkWithKeywords(String keywords) {
		final List<String> keywordsList = Arrays.asList(keywords.split(" +")).stream()
				.filter(str -> !StringUtils.isEmpty(str)).collect(Collectors.toList());
		List<BookmarkVo> matchedBookmarks = getAllBookmark().stream()
				.filter(mark -> isKeywordsMatched(mark, keywordsList)).collect(Collectors.toList());
		return matchedBookmarks;
	}

	@Override
	public void insertBookmark(BookmarkVo bookmarkVo) {
		BookmarkDto bookmarkDto = BookmarkDto.builder().uuid(UUID.randomUUID()).title(bookmarkVo.getTitle())
				.tags(convertTagsToTagList(bookmarkVo.getTags())).href(addProxyForHref(bookmarkVo.getHref())).build();
		bookmarkDao.insertBookmark(bookmarkDto);
	}

	@Override
	public void updateBookmark(BookmarkVo bookmarkVo) {
		BookmarkDto bookmarkDto = BookmarkDto.builder().uuid(UUID.fromString(bookmarkVo.getUuid()))
				.title(bookmarkVo.getTitle()).tags(convertTagsToTagList(bookmarkVo.getTags()))
				.href(addProxyForHref(bookmarkVo.getHref())).build();

		bookmarkDao.updateBookmark(bookmarkDto);
	}

	@Override
	public void deleteBookmark(String bookmarkId) {
		bookmarkDao.deleteBookmark(UUID.fromString(bookmarkId));
	}

	private boolean isKeywordsMatched(final BookmarkVo mark, List<String> keywordsList) {
		boolean allMatched = true;
		for (String keywords : keywordsList) {
		    keywords = keywords.toLowerCase();
			boolean oneMatched = false;
			if (mark.getTitle().toLowerCase().contains(keywords)) {
				oneMatched = true;
				continue;
			}
			for (String tag : convertTagsToTagList(mark.getTags())) {
				if (tag.toLowerCase().contains(keywords)) {
					oneMatched = true;
					break;
				}
			}
			if (!oneMatched) {
				allMatched = false;
				break;
			}
		}
		return allMatched;
	}

	private List<String> convertTagsToTagList(String tagsStr) {
		return Arrays.asList(tagsStr.split(TAG_SPLITER));
	}

	private String convertTagListToTags(List<String> tagList) {
		StringBuffer sb = new StringBuffer();
		int tagSize = tagList.size();
		for (int i = 0; i < tagSize - 1; i++) {
			sb.append(tagList.get(i));
			sb.append(TAG_SPLITER);
		}
		if (tagSize > 0) {
			sb.append(tagList.get(tagSize - 1));
		}
		return sb.toString();
	}

	private String addProxyForHref(String href) {
		if (!StringUtils.isEmpty(href) && !href.contains("://")) {
            log.warn("No proxy for {}, add http:// ahead", href);
            return ("http://" + href);
		}
		return href;
	}
}
