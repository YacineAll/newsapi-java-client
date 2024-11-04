package org.yacineall.api.newsapi.model;

/**
 * Represents a source from the News API.
 *
 * @param id The unique identifier of the source.
 * @param name The name of the source.
 * @param description A brief description of the source.
 * @param url The URL of the source.
 * @param category The category of the source.
 * @param language The language of the source.
 * @param country The country of the source.
 */
public record Source(
        String id,
        String name,
        String description,
        String url,
        String category,
        String language,
        String country
) { }
