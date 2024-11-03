package org.yacineall.api.newsapi.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record SourcesRequest(
        String category,
        String language,
        String country) implements INewsAPIRequest{
    @Override
    public String getURIString() {
        List<String> params = new ArrayList<>(10);
        if (category != null) { params.add("category=" + category); }
        if (language != null) { params.add("language=" + language); }
        if (country != null) { params.add("country=" + country); }
        return params.stream().collect(Collectors.joining("&", "?", ""));
    }
}
