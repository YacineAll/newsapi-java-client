package org.yacineall.newsapi.model.response;

import org.yacineall.newsapi.model.Source;

import java.util.List;

/**
 * Represents the response model for sources from the News API.
 *
 * @param status The status of the API response.
 * @param sources A list of {@link Source} objects representing the sources returned by the API.
 */
public record SourcesResponse(
        String status,
        List<Source> sources
) implements INewsAPIResponse { }

