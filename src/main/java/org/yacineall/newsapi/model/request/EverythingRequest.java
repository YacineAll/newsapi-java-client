package org.yacineall.newsapi.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record EverythingRequest(
        String q,
        String sources,
        String domains,
        String from,
        String to,
        String language,
        String sortBy,
        String pageSize,
        String page,
        String searchIn
) implements INewsAPIRequest{
    @Override
    public String getURIString() {
        List<String> params = new ArrayList<>(10);
        if (q != null) { params.add("q=" + q); }
        if (sources != null) { params.add("sources=" + sources); }
        if (domains != null) { params.add("domains=" + domains); }
        if (from != null) { params.add("from=" + from); }
        if (to != null) { params.add("to=" + to); }
        if (language != null) { params.add("language=" + language); }
        if (sortBy != null) { params.add("sortBy=" + sortBy); }
        if (pageSize != null) { params.add("pageSize=" + pageSize); }
        if (page != null) { params.add("page=" + page); }
        if (searchIn != null) { params.add("searchIn=" + searchIn); }
        return params.stream().collect(Collectors.joining("&", "?", ""));
    }
}
