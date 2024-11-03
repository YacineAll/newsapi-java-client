package org.yacineall.api.newsapi.service;

import java.net.http.HttpClient;

public class EverythingService extends NewsAPIService{

    public EverythingService() {
        super(HttpClient.newHttpClient());
    }

    protected EverythingService(HttpClient httpClient) {
        super(httpClient);
    }


}
