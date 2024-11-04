package org.yacineall.newsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yacineall.newsapi.exceptions.APIResponseParsingException;
import org.yacineall.newsapi.exceptions.HTTPSendInterruptedException;
import org.yacineall.newsapi.model.response.ArticleResponse;
import org.yacineall.newsapi.model.response.INewsAPIResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Base service class for interacting with the News API.
 * This class provides common functionality for making HTTP requests and parsing responses.
 */
public class NewsAPIService {

    /**
     * The HTTP client to use for making requests.
     */
    private final HttpClient httpClient;
    /**
     * The object mapper to convert http response.
     */
    private final ObjectMapper objectMapper;

    /**
     * Constructs a NewsAPIService with a default {@link HttpClient}.
     */
    public NewsAPIService() {
        this(HttpClient.newHttpClient());
    }

    /**
     * Constructs a NewsAPIService with the given {@link HttpClient}.
     *
     * @param client The HTTP client to use for making requests.
     */
    protected NewsAPIService(final HttpClient client) {
        super();
        this.httpClient = client;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Gets the HTTP client used by this service.
     *
     * @return The HTTP client.
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Gets the ObjectMapper used by this service.
     *
     * @return The ObjectMapper.
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Sends an HTTP request and returns the parsed response.
     *
     * @param request The HTTP request to send.
     * @return The parsed response as an {@link INewsAPIResponse}.
     * @throws APIResponseParsingException if there is an error parsing the response.
     * @throws HTTPSendInterruptedException if the HTTP request is interrupted.
     */
    public INewsAPIResponse getNewsAPIResponse(final HttpRequest request) {
        try {
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return getObjectMapper().readValue(response.body(), ArticleResponse.class);
        } catch (IOException e) {
            throw new APIResponseParsingException("Get and parse Sources request", e);
        } catch (InterruptedException e) {
            throw new HTTPSendInterruptedException("Sending the http request interrupted", e);
        }
    }
}
