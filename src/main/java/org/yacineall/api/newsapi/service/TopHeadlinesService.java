package org.yacineall.api.newsapi.service;

import java.net.http.HttpClient;

/**
 * Service class for interacting with the Top Headlines endpoint of the News API.
 * This service extends the {@link NewsAPIService} and provides specific functionality for the Top Headlines endpoint.
 */
public class TopHeadlinesService extends NewsAPIService {

    /**
     * Constructs a TopHeadlinesService with a default {@link HttpClient}.
     */
    public TopHeadlinesService() {
        super(HttpClient.newHttpClient());
    }

    /**
     * Constructs a TopHeadlinesService with the given {@link HttpClient}.
     *
     * @param httpClient The HTTP client to use for making requests.
     */
    protected TopHeadlinesService(final HttpClient httpClient) {
        super(httpClient);
    }
}
