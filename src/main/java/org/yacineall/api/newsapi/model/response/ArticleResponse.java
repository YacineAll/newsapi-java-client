package org.yacineall.api.newsapi.model.response;

import org.yacineall.api.newsapi.model.Article;

import java.util.List;

/**
 * Represents the response model for articles from the News API.
 *
 * @param status The status of the API response.
 * @param totalResults The total number of results returned by the API.
 * @param articles A list of {@link Article} objects representing the articles returned by the API.
 */
public record ArticleResponse(
        String status,
        int totalResults,
        List<Article> articles
) implements INewsAPIResponse { }
