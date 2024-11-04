package org.yacineall.newsapi.service;

import java.net.http.HttpClient;

/**
 * Service class for interacting with the Everything endpoint of the News API.
 * This service extends the {@link NewsAPIService}
 * and provides specific functionality for the Everything endpoint.
 */
public class EverythingService extends NewsAPIService {

    /**
     * Constructs an EverythingService with a default {@link HttpClient}.
     */
    public EverythingService() {
        super(HttpClient.newHttpClient());
    }

    /**
     * Constructs an EverythingService with the given {@link HttpClient}.
     *
     * @param httpClient The HTTP client to use for making requests.
     */
    protected EverythingService(final HttpClient httpClient) {
        super(httpClient);
    }
}
