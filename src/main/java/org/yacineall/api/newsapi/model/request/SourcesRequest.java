package org.yacineall.api.newsapi.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sources request to communicate with "/v2/top-headlines/sources" endpoint.
 * Request parameters are:
 *
 * @param category ind sources that display news of this category.
 * @param language Find sources that display news in a specific language
 * @param country Find sources that display news in a specific country.
 */
public record SourcesRequest(
        String category,
        String language,
        String country) implements INewsAPIRequest {
    /**
     * Initial capacity for array list.
     */
    private static final int INITIAL_CAPACITY = 3;

    @Override
    public String getURIString() {
        List<String> params = new ArrayList<>(INITIAL_CAPACITY);
        if (category != null) {
            params.add("category=" + category);
        }
        if (language != null) {
            params.add("language=" + language);
        }

        if (country != null) { params.add("country=" + country); }

        return params.stream().collect(Collectors.joining("&", "?", ""));

    }

}

