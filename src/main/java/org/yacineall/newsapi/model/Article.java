package org.yacineall.newsapi.model;

/**
 * Represents an article from the News API.
 *
 * @param source The source of the article.
 * @param author The author of the article.
 * @param title The title of the article.
 * @param description A brief description of the article.
 * @param url The URL to the full article.
 * @param urlToImage The URL to the image associated with the article.
 * @param publishedAt The date and time when the article was published.
 * @param content The content of the article.
 */
public record Article(
        Source source,
        String author,
        String title,
        String description,
        String url,
        String urlToImage,
        String publishedAt,
        String content
) { }
