package org.yacineall.newsapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.yacineall.newsapi.model.response.ArticleResponse;
import org.yacineall.newsapi.model.response.INewsAPIResponse;
import org.yacineall.newsapi.utils.Tools;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class EverythingServiceTest {

    private static final String EXPECTED_RESPONSE_CONTENT_FILE = "news-api-responses-examples/everything.json";
    private static HttpResponse<String> mockHttpResponse;
    private static NewsAPIService newsAPIService;
    private static HttpClient httpClient;
    private static JsonNode expectedResponse;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeAll
    public static void setUp() {
        String response = Tools.readJsonAsString(EXPECTED_RESPONSE_CONTENT_FILE);
        expectedResponse = Tools.readJsonFromResource(EXPECTED_RESPONSE_CONTENT_FILE);
        mockHttpResponse = Mockito.mock(HttpResponse.class);
        httpClient = Mockito.mock(HttpClient.class);
        Mockito.when(mockHttpResponse.statusCode()).thenReturn(200);
        Mockito.when(mockHttpResponse.body()).thenReturn(response);
        newsAPIService = new EverythingService(httpClient);
    }

    @Test
    void fetchEverythingTest() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(
                Mockito.any(HttpRequest.class),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
        )).thenReturn(mockHttpResponse);
        HttpRequest request = Mockito.mock(HttpRequest.class);
        INewsAPIResponse response = newsAPIService.getNewsAPIResponse(request);
        assertInstanceOf(ArticleResponse.class, response);
        ArticleResponse articleResponse = (ArticleResponse) response;
        assertEquals(2, articleResponse.articles().size());
        JsonNode actual = OBJECT_MAPPER.readTree(Tools.toJson(expectedResponse));
        assertEquals(expectedResponse, actual);
    }

}
