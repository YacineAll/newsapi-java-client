package org.yacineall.api.newsapi.utils;

import org.yacineall.api.newsapi.exceptions.APIKeyIsNullException;
import org.yacineall.api.newsapi.exceptions.IllegalSearchInValueException;
import org.yacineall.api.newsapi.exceptions.URIException;
import org.yacineall.api.newsapi.model.request.EverythingRequest;
import org.yacineall.api.newsapi.model.request.INewsAPIRequest;
import org.yacineall.api.newsapi.model.request.SourcesRequest;
import org.yacineall.api.newsapi.model.request.TopHeadlinesRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class RequestBuilder {
    private static final String BASE_URL = "https://newsapi.org/v2";
    private final String apiKey;

    protected RequestBuilder(String apiKey) {
        if (apiKey == null){
            throw new APIKeyIsNullException();
        }
        this.apiKey = apiKey;
    }

    public static class TopHeadlinesRequestBuilder extends RequestBuilder {
        private String q;
        private String category;
        private String sources;
        private String country;
        private String language;
        private String pageSize;
        private String page;

        public TopHeadlinesRequestBuilder(String apiKey) {
            super(apiKey);
        }

        public TopHeadlinesRequestBuilder q(String q) {
            this.q = q;
            return this;
        }

        public TopHeadlinesRequestBuilder category(String category) {
            this.category = category;
            return this;
        }

        public TopHeadlinesRequestBuilder sources(String sources) {
            this.sources = sources;
            return this;
        }

        public TopHeadlinesRequestBuilder country(String country) {
            this.country = country;
            return this;
        }

        public TopHeadlinesRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public TopHeadlinesRequestBuilder pageSize(String pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public TopHeadlinesRequestBuilder page(String page) {
            this.page = page;
            return this;
        }

        public HttpRequest build() {
            INewsAPIRequest request = new TopHeadlinesRequest(category, sources, q, pageSize, page, country, language);
            return buildHttpRequest(request, "top-headlines");
        }
    }

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

        public EverythingRequestBuilder(String apiKey) {
            super(apiKey);
        }

        public EverythingRequestBuilder q(String q) {
            this.q = q;
            return this;
        }

        public EverythingRequestBuilder sources(String sources) {
            this.sources = sources;
            return this;
        }

        public EverythingRequestBuilder domains(String domains) {
            this.domains = domains;
            return this;
        }

        public EverythingRequestBuilder from(String from) {
            this.from = from;
            return this;
        }

        public EverythingRequestBuilder to(String to) {
            this.to = to;
            return this;
        }

        public EverythingRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public EverythingRequestBuilder sortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public EverythingRequestBuilder pageSize(String pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public EverythingRequestBuilder page(String page) {
            this.page = page;
            return this;
        }

        public EverythingRequestBuilder searchIn(String searchIn) {
            if (!TITLE.equals(searchIn) && !DESCRIPTION.equals(searchIn) && !CONTENT.equals(searchIn)) {
                throw new IllegalSearchInValueException(
                        String.format("Invalid value for searchIn: '%s'. Expected 'title', 'description', or 'content'"
                                , searchIn
                        )
                );
            }
            this.searchIn = searchIn;
            return this;
        }

        public HttpRequest build() {
            INewsAPIRequest request = new EverythingRequest(q, sources, domains, from, to, language, sortBy, pageSize, page, searchIn);
            return buildHttpRequest(request, "everything");
        }
    }

    public static class SourceRequestBuilder extends RequestBuilder {
        private String category, language, country;

        public SourceRequestBuilder(String apiKey) {
            super(apiKey);
        }

        public SourceRequestBuilder category(String category) {
            this.category = category;
            return this;
        }

        public SourceRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public SourceRequestBuilder country(String country) {
            this.country = country;
            return this;
        }

        public HttpRequest build() {
            INewsAPIRequest request = new SourcesRequest(category, language, country);
            return buildHttpRequest(request, "top-headlines/sources");
        }
    }

    protected HttpRequest buildHttpRequest(INewsAPIRequest request, String endpoint) {
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
