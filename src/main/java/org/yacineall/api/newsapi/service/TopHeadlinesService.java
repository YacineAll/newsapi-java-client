package org.yacineall.api.newsapi.service;

import java.net.http.HttpClient;

public class TopHeadlinesService extends NewsAPIService{
    public TopHeadlinesService() {
        super(HttpClient.newHttpClient());
    }

    protected TopHeadlinesService(HttpClient httpClient) {
        super(httpClient);
    }


}
