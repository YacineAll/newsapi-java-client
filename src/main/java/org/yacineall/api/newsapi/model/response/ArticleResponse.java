package org.yacineall.api.newsapi.model.response;

import org.yacineall.api.newsapi.model.Article;

import java.util.List;

public record ArticleResponse (
        String status,
        int totalResults,
        List<Article> articles
) implements INewsAPIResponse { }
