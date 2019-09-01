package com.github.rhettcaptain.jarvis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.rhettcaptain.jarvis.vo.BookmarkVo;
import org.springframework.util.StringUtils;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    public List<BookmarkVo> getAllBookmark() {
        List<BookmarkVo> bookmarks = new ArrayList<BookmarkVo>();
        BookmarkVo bm1 = BookmarkVo.builder().title("bm1").tags(Arrays.asList("a", "d")).href("https://www.baidu.com")
                .build();
        BookmarkVo bm2 = BookmarkVo.builder().title("bm2").tags(Arrays.asList("b")).href("https://www.google.com")
                .build();
        bookmarks.add(bm1);
        bookmarks.add(bm2);
        return bookmarks;
    }

    public List<BookmarkVo> getBookmarkWithKeywords(String keywords) {
        final List<String> keywordsList = Arrays.asList(keywords.split(" *")).stream()
                .filter(str -> !StringUtils.isEmpty(str)).collect(
                        Collectors.toList());
        List<BookmarkVo> matchedBookmarks = getAllBookmark().stream()
                .filter(mark -> isKeywordsMatched(mark, keywordsList)).collect(
                        Collectors.toList());
        return matchedBookmarks;
    }

    private boolean isKeywordsMatched(final BookmarkVo mark, List<String> keywordsList) {
        for (String keywords : keywordsList) {
            if (mark.getTitle().contains(keywords)) {
                return true;
            }
            for (String tag : mark.getTags()) {
                if (tag.contains(keywords)) {
                    return true;
                }
            }
        }
        return false;
    }
}
