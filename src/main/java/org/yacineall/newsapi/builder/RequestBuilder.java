package org.yacineall.newsapi.builder;

import org.yacineall.newsapi.exceptions.APIKeyIsNullException;
import org.yacineall.newsapi.exceptions.IllegalSearchInValueException;
import org.yacineall.newsapi.exceptions.URIException;
import org.yacineall.newsapi.model.request.EverythingRequest;
import org.yacineall.newsapi.model.request.INewsAPIRequest;
import org.yacineall.newsapi.model.request.SourcesRequest;
import org.yacineall.newsapi.model.request.TopHeadlinesRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

/**
 * Base class for building requests to the News API.
 * This class provides the foundation for creating specific request builders for different endpoints.
 */
public class RequestBuilder {
    private static final String BASE_URL = "https://newsapi.org/v2";
    private final String apiKey;

    /**
     * Constructs a RequestBuilder with the given API key.
     *
     * @param apiKey The API key for the News API.
     * @throws APIKeyIsNullException if the API key is null.
     */
    protected RequestBuilder(final String apiKey) {
        if (apiKey == null) {
            throw new APIKeyIsNullException();
        }
        this.apiKey = apiKey;
    }

    /**
     * Builder for creating requests to the Top Headlines endpoint of the News API.
     */
    public static class TopHeadlinesRequestBuilder extends RequestBuilder {
        private String q;
        private String category;
        private String sources;
        private String country;
        private String language;
        private String pageSize;
        private String page;

        /**
         * Constructs a TopHeadlinesRequestBuilder with the given API key.
         *
         * @param apiKey The API key for the News API.
         */
        public TopHeadlinesRequestBuilder(final String apiKey) {
            super(apiKey);
        }

        /**
         * Sets the query parameter for the request.
         *
         * @param q The query string.
         * @return This builder instance.
         */
        public TopHeadlinesRequestBuilder q(final String q) {
            this.q = q;
            return this;
        }

        /**
         * Sets the category parameter for the request.
         *
         * @param category The category of the news.
         * @return This builder instance.
         */
        public TopHeadlinesRequestBuilder category(final String category) {
            this.category = category;
            return this;
        }

        /**
         * Sets the sources parameter for the request.
         *
         * @param sources The sources of the news.
         * @return This builder instance.
         */
        public TopHeadlinesRequestBuilder sources(final String sources) {
            this.sources = sources;
            return this;
        }

        /**
         * Sets the country parameter for the request.
         *
         * @param country The country of the news.
         * @return This builder instance.
         */
        public TopHeadlinesRequestBuilder country(final String country) {
            this.country = country;
            return this;
        }

        /**
         * Sets the language parameter for the request.
         *
         * @param language The language of the news.
         * @return This builder instance.
         */
        public TopHeadlinesRequestBuilder language(final String language) {
            this.language = language;
            return this;
        }

        /**
         * Sets the page size parameter for the request.
         *
         * @param pageSize The page size.
         * @return This builder instance.
         */
        public TopHeadlinesRequestBuilder pageSize(final String pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        /**
         * Sets the page parameter for the request.
         *
         * @param page The page number.
         * @return This builder instance.
         */
        public TopHeadlinesRequestBuilder page(final String page) {
            this.page = page;
            return this;
        }

        /**
         * Builds the HTTP request for the Top Headlines endpoint.
         *
         * @return The constructed {@link HttpRequest}.
         */
        public HttpRequest build() {
            INewsAPIRequest request = new TopHeadlinesRequest(category, sources, q, pageSize, page, country, language);
            return buildHttpRequest(request, "top-headlines");
        }
    }

    /**
     * Builder for creating requests to the Everything endpoint of the News API.
     */
    public static class EverythingRequestBuilder extends RequestBuilder {
        private static final String TITLE = "title";
        private static final String DESCRIPTION = "description";
        private static final String CONTENT = "content";

        private String q;
        private String sources;
        private String domains;
        private String from;
        private String to;
        private String language;
        private String sortBy;
        private String searchIn;
        private String pageSize;
        private String page;

        /**
         * Constructs an EverythingRequestBuilder with the given API key.
         *
         * @param apiKey The API key for the News API.
         */
        public EverythingRequestBuilder(final String apiKey) {
            super(apiKey);
        }

        /**
         * Sets the query parameter for the request.
         *
         * @param q The query string.
         * @return This builder instance.
         */
        public EverythingRequestBuilder q(final String q) {
            this.q = q;
            return this;
        }

        /**
         * Sets the sources parameter for the request.
         *
         * @param sources The sources of the news.
         * @return This builder instance.
         */
        public EverythingRequestBuilder sources(final String sources) {
            this.sources = sources;
            return this;
        }

        /**
         * Sets the domains parameter for the request.
         *
         * @param domains The domains of the news.
         * @return This builder instance.
         */
        public EverythingRequestBuilder domains(final String domains) {
            this.domains = domains;
            return this;
        }

        /**
         * Sets the from parameter for the request.
         *
         * @param from The start date for the news.
         * @return This builder instance.
         */
        public EverythingRequestBuilder from(final String from) {
            this.from = from;
            return this;
        }

        /**
         * Sets the to parameter for the request.
         *
         * @param to The end date for the news.
         * @return This builder instance.
         */
        public EverythingRequestBuilder to(final String to) {
            this.to = to;
            return this;
        }

        /**
         * Sets the language parameter for the request.
         *
         * @param language The language of the news.
         * @return This builder instance.
         */
        public EverythingRequestBuilder language(final String language) {
            this.language = language;
            return this;
        }

        /**
         * Sets the sortBy parameter for the request.
         *
         * @param sortBy The sorting criteria for the news.
         * @return This builder instance.
         */
        public EverythingRequestBuilder sortBy(final String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        /**
         * Sets the page size parameter for the request.
         *
         * @param pageSize The page size.
         * @return This builder instance.
         */
        public EverythingRequestBuilder pageSize(final String pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        /**
         * Sets the page parameter for the request.
         *
         * @param page The page number.
         * @return This builder instance.
         */
        public EverythingRequestBuilder page(final String page) {
            this.page = page;
            return this;
        }

        /**
         * Sets the searchIn parameter for the request.
         *
         * @param inSearch The field to search in.
         * @return This builder instance.
         * @throws IllegalSearchInValueException if the searchIn value is invalid.
         */
        public EverythingRequestBuilder searchIn(final String inSearch) {
            if (!TITLE.equals(inSearch) && !DESCRIPTION.equals(inSearch) && !CONTENT.equals(inSearch)) {
                String message = String.format(
                        "Invalid value for searchIn: '%s'. Expected 'title', 'description', or 'content'",
                        inSearch);
                throw new IllegalSearchInValueException(message);
            }
            this.searchIn = inSearch;
            return this;
        }

        /**
         * Builds the HTTP request for the Everything endpoint.
         *
         * @return The constructed {@link HttpRequest}.
         */
        public HttpRequest build() {
            INewsAPIRequest request = new EverythingRequest(
                    q,
                    sources,
                    domains,
                    from,
                    to,
                    language,
                    sortBy,
                    pageSize,
                    page,
                    searchIn
            );
            return buildHttpRequest(request, "everything");
        }
    }

    /**
     * Builder for creating requests to the Sources endpoint of the News API.
     */
    public static class SourceRequestBuilder extends RequestBuilder {
        /**
         * Category of the source.
         */
        private String category;
        /**
         * Language of the needed sources.
         */
        private String language;
        /**
         * The target country.
         */
        private String country;

        /**
         * Constructs a SourceRequestBuilder with the given API key.
         *
         * @param apiKey The API key for the News API.
         */
        public SourceRequestBuilder(final String apiKey) {
            super(apiKey);
        }

        /**
         * Sets the category parameter for the request.
         *
         * @param category The category of the sources.
         * @return This builder instance.
         */
        public SourceRequestBuilder category(final String category) {
            this.category = category;
            return this;
        }

        /**
         * Sets the language parameter for the request.
         *
         * @param language The language of the sources.
         * @return This builder instance.
         */
        public SourceRequestBuilder language(final String language) {
            this.language = language;
            return this;
        }

        /**
         * Sets the country parameter for the request.
         *
         * @param country The country of the sources.
         * @return This builder instance.
         */
        public SourceRequestBuilder country(final String country) {
            this.country = country;
            return this;
        }

        /**
         * Builds the HTTP request for the Sources endpoint.
         *
         * @return The constructed {@link HttpRequest}.
         */
        public HttpRequest build() {
            INewsAPIRequest request = new SourcesRequest(category, language, country);
            return buildHttpRequest(request, "top-headlines/sources");
        }
    }

    /**
     * Builds an HTTP request for the given endpoint and request parameters.
     *
     * @param request The request parameters.
     * @param endpoint The endpoint of the News API.
     * @return The constructed {@link HttpRequest}.
     * @throws URIException if there is an error constructing the URI.
     */
    protected HttpRequest buildHttpRequest(final INewsAPIRequest request, final String endpoint) {
        String url = String.format("%s/%s%s&apiKey=%s", BASE_URL, endpoint, request.getURIString(), apiKey);
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new URIException(e);
        }
    }
}
