package org.yacineall.api.newsapi.model.response;

import org.yacineall.api.newsapi.model.Source;

import java.util.List;

public record SourcesResponse(
        String status,
        List<Source> sources
) implements INewsAPIResponse { }


