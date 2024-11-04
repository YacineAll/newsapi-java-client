package org.yacineall.newsapi.service;

import org.yacineall.newsapi.exceptions.APIResponseParsingException;
import org.yacineall.newsapi.model.response.INewsAPIResponse;
import org.yacineall.newsapi.model.response.SourcesResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Service class for interacting with the Sources endpoint of the News API.
 * This service extends the {@link NewsAPIService} and provides specific functionality for the Sources endpoint.
 */
public class SourcesService extends NewsAPIService {

    /**
     * Constructs a SourcesService with a default {@link HttpClient}.
     */
    public SourcesService() {
        super(HttpClient.newHttpClient());
    }

    /**
     * Constructs a SourcesService with the given {@link HttpClient}.
     *
     * @param httpClient The HTTP client to use for making requests.
     */
    protected SourcesService(final HttpClient httpClient) {
        super(httpClient);
    }

    /**
     * Sends an HTTP request to the Sources endpoint and returns the parsed response.
     *
     * @param httpRequest The HTTP request to send.
     * @return The parsed response as an {@link INewsAPIResponse}.
     * @throws APIResponseParsingException if there is an error parsing the response.
     */
    @Override
    public INewsAPIResponse getNewsAPIResponse(final HttpRequest httpRequest) {
        try {
            HttpResponse<String> response = getHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return getObjectMapper().readValue(response.body(), SourcesResponse.class);
        } catch (IOException e) {
            throw new APIResponseParsingException("Get and parse Sources request", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
