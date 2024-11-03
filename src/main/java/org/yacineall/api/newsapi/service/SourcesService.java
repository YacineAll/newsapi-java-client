package org.yacineall.api.newsapi.service;

import org.yacineall.api.newsapi.exceptions.APIResponseParsingException;
import org.yacineall.api.newsapi.model.response.INewsAPIResponse;
import org.yacineall.api.newsapi.model.response.SourcesResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SourcesService extends NewsAPIService {

    public SourcesService() {
        super(HttpClient.newHttpClient());
    }

    protected SourcesService(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public INewsAPIResponse getNewsAPIResponse(HttpRequest httpRequest) {
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
