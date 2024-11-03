package org.yacineall.api.newsapi.model;

public record Source(
    String id,
    String name,
    String description,
    String url,
    String category,
    String language,
    String country
) { }
