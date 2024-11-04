package org.yacineall.api.newsapi.utils;

import org.junit.jupiter.api.Test;
import org.yacineall.api.newsapi.builder.RequestBuilder;
import org.yacineall.api.newsapi.exceptions.APIKeyIsNullException;
import org.yacineall.api.newsapi.exceptions.IllegalSearchInValueException;
import org.yacineall.api.newsapi.exceptions.URIException;

import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.*;

class RequestBuilderTest {
    private static final String BASE_URL = "https://newsapi.org/v2";
    private static final String API_KEY = "apiKey";

    @Test
    void sourcesRequestBuilderTest() {
        String category = "category";
        String language = "language";
        String country = "country";
        HttpRequest request = new RequestBuilder.SourceRequestBuilder(API_KEY)
                .category(category)
                .language(language)
                .country(country)
                .build();

        String uri = String.format(
                "%s/top-headlines/sources?category=%s&language=%s&country=%s&apiKey=%s",
                BASE_URL, category, language, country, API_KEY
        );
        assertEquals(uri, request.uri().toString());
        assertEquals("GET", request.method());

        assertThrowsExactly(
                URIException.class,
                () -> new RequestBuilder.SourceRequestBuilder(API_KEY)
                        .category("^IXIC\n")
                        .language(language)
                        .country(country)
                        .build()

        );
    }

    @Test
    void testSourceRequestBuilderWithOptionalParameters() {
        HttpRequest request = new RequestBuilder.SourceRequestBuilder(API_KEY)
                .category("technology")
                .language("en")
                .country("us")
                .build();

        assertNotNull(request);
        String uri = request.uri().toString();
        assertTrue(uri.contains("apiKey=" + API_KEY));
        assertTrue(uri.contains("category=technology"));
        assertTrue(uri.contains("language=en"));
        assertTrue(uri.contains("country=us"));
    }

    @Test
    void testTopHeadlinesRequestBuilderWithParameters() {
        HttpRequest request = new RequestBuilder.TopHeadlinesRequestBuilder(API_KEY)
                .q("query")
                .category("business")
                .sources("sources")
                .country("us")
                .language("fr")
                .pageSize("10")
                .page("1")
                .build();
        assertNotNull(request);
        String uri = request.uri().toString();
        assertTrue(uri.contains("apiKey=" + API_KEY));
        assertTrue(uri.contains("q=query"));
        assertTrue(uri.contains("category=business"));
        assertTrue(uri.contains("sources=sources"));
        assertTrue(uri.contains("country=us"));
        assertTrue(uri.contains("language=fr"));
        assertTrue(uri.contains("pageSize=10"));
        assertTrue(uri.contains("page=1"));
        String expectedURI = "https://newsapi.org/v2/top-headlines?q=query&sources=sources&category=business&language=fr&pageSize=10&country=us&page=1&apiKey=apiKey";
        assertEquals(expectedURI, request.uri().toString());
    }

    @Test
    void testEverythingRequestBuilderWithValidSearchInParameter() {
        HttpRequest request = new RequestBuilder.EverythingRequestBuilder(API_KEY)
                .q("query")
                .sources("sources")
                .domains("business")
                .from("2024-10-11")
                .to("2025-10-31")
                .language("fr")
                .sortBy("popularity")
                .searchIn("title")
                .pageSize("10")
                .page("1")
                .build();

        assertNotNull(request);
        String uri = request.uri().toString();
        assertTrue(uri.contains("apiKey=" + API_KEY));
        assertTrue(uri.contains("q=query"));
        assertTrue(uri.contains("sources=sources"));
        assertTrue(uri.contains("domains=business"));
        assertTrue(uri.contains("from=2024-10-11"));
        assertTrue(uri.contains("to=2025-10-31"));
        assertTrue(uri.contains("language=fr"));
        assertTrue(uri.contains("sortBy=popularity"));
        assertTrue(uri.contains("searchIn=title"));
        assertTrue(uri.contains("pageSize=10"));
        assertTrue(uri.contains("page=1"));
        String expectedURI = "https://newsapi.org/v2/everything?q=query&sources=sources&domains=business&from=2024-10-11&to=2025-10-31&language=fr&sortBy=popularity&pageSize=10&page=1&searchIn=title&apiKey=apiKey";
        assertEquals(expectedURI, request.uri().toString());
    }

    @Test
    void testEverythingRequestBuilderWithInvalidSearchInParameter() {
        RequestBuilder.EverythingRequestBuilder builder = new RequestBuilder.EverythingRequestBuilder(API_KEY)
                .q("technology");

        assertThrows(IllegalSearchInValueException.class, () -> builder.searchIn("invalid_value"));
    }

    @Test
    void testMissingApiKeyThrowsExceptionInSourceRequestBuilder() {
        APIKeyIsNullException exception = assertThrows(
                APIKeyIsNullException.class,
                () -> new RequestBuilder.SourceRequestBuilder(null).build()
        );
        assertEquals("API key is required and cannot be null", exception.getMessage());
    }

    @Test
    void buildURIExceptionsTest() {
        assertThrows(
                URIException.class,
                () -> new RequestBuilder.SourceRequestBuilder(API_KEY).category("^IXIC").build()
        );
        IllegalSearchInValueException exception = assertThrows(
                IllegalSearchInValueException.class,
                () -> new RequestBuilder.EverythingRequestBuilder(API_KEY).searchIn("name").build()
        );
        assertEquals(
                "Invalid value for searchIn: 'name'. Expected 'title', 'description', or 'content'",
                exception.getMessage()
        );
    }



}