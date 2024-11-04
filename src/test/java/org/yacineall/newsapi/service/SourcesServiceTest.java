package org.yacineall.newsapi.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.yacineall.newsapi.exceptions.APIResponseParsingException;
import org.yacineall.newsapi.model.Source;
import org.yacineall.newsapi.model.response.INewsAPIResponse;
import org.yacineall.newsapi.model.response.SourcesResponse;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class SourcesServiceTest {
    private static NewsAPIService newsAPIService;
    private static final String SOURCES_RESPONSE = """
            {
              "status": "ok",
              "sources": [
                {
                  "id": "argaam",
                  "name": "Argaam",
                  "description": "description 1",
                  "url": "https://www.argaam.com",
                  "category": "business",
                  "language": "ar",
                  "country": "sa"
                },
                {
                  "id": "australian-financial-review",
                  "name": "Australian Financial Review",
                  "description": "description 2",
                  "url": "http://www.afr.com",
                  "category": "business",
                  "language": "en",
                  "country": "au"
                }
              ]
            }
            
            """;
    public static HttpResponse<String> mockHttpResponse;
    public static HttpClient httpClient;

    @BeforeAll
    public static void setUp() {
        // Mock both HttpResponse and HttpClient
        mockHttpResponse = Mockito.mock(HttpResponse.class);
        httpClient = Mockito.mock(HttpClient.class);

        // Setup mockHttpResponse behavior
        Mockito.when(mockHttpResponse.statusCode()).thenReturn(200);
        Mockito.when(mockHttpResponse.body()).thenReturn(SOURCES_RESPONSE);
        newsAPIService = new SourcesService(httpClient);


    }

    @Test
    void fetchSourceTest() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(
                Mockito.any(HttpRequest.class),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
        )).thenReturn(mockHttpResponse);
        HttpRequest request = Mockito.mock(HttpRequest.class);
        INewsAPIResponse response = newsAPIService.getNewsAPIResponse(request);
        assertInstanceOf(SourcesResponse.class, response);
        SourcesResponse sourcesResponse = (SourcesResponse) response;
        assertEquals("ok", sourcesResponse.status());
        assertEquals(2, sourcesResponse.sources().size());
        Source source1 = new Source(
                "argaam", "Argaam", "description 1",
                "https://www.argaam.com", "business",
                "ar", "sa"
        );
        assertEquals(source1, sourcesResponse.sources().get(0));
        Source source2 = new Source(
                "australian-financial-review", "Australian Financial Review", "description 2",
                "http://www.afr.com", "business",
                "en", "au"
        );
        assertEquals(source2, sourcesResponse.sources().get(1));
    }

    @Test
    void throwExceptionTest() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(
                Mockito.any(HttpRequest.class),
                Mockito.eq(HttpResponse.BodyHandlers.ofString())
        )).thenThrow(new IOException("Failed to connect"));
        APIResponseParsingException exception = assertThrows(
                APIResponseParsingException.class,
                () -> newsAPIService.getNewsAPIResponse(Mockito.mock(HttpRequest.class))
        );
        assertEquals("Get and parse Sources request", exception.getMessage());


    }
}