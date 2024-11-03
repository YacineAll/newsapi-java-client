package org.yacineall.api.newsapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yacineall.api.newsapi.exceptions.APIResponseParsingException;
import org.yacineall.api.newsapi.exceptions.HTTPSendInterruptedException;
import org.yacineall.api.newsapi.model.response.ArticleResponse;
import org.yacineall.api.newsapi.model.response.INewsAPIResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsAPIService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public NewsAPIService() {
        this(HttpClient.newHttpClient());
    }
    protected NewsAPIService(HttpClient httpClient) {
        super();
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public INewsAPIResponse getNewsAPIResponse(HttpRequest request) {
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
