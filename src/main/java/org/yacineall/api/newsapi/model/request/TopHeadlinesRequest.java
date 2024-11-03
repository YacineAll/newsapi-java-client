package org.yacineall.api.newsapi.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record TopHeadlinesRequest(
        String category,
        String sources,
        String q,
        String pageSize,
        String page,
        String country,
        String language
) implements INewsAPIRequest {
    @Override
    public String getURIString() {
        List<String> params = new ArrayList<>(6);
        if (q != null) { params.add("q=" + q); }
        if (sources != null) { params.add("sources=" + sources); }
        if (category != null) { params.add("category=" + category); }
        if (language != null) { params.add("language=" + language); }
        if (pageSize != null) { params.add("pageSize=" + pageSize); }
        if (country != null) { params.add("country=" + country); }
        if (page != null) { params.add("page=" + page); }
        return params.stream().collect(Collectors.joining("&", "?", ""));
    }
}
