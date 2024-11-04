package org.yacineall.api.newsapi.model.request;

/**
 * Interface representing a generic NewsAPI request.
 * All specific request classes should implement this interface to ensure a consistent structure.
 */
public interface INewsAPIRequest {
    /**
     * Function that build the url with endpoint and the query params
     *
     * @return String value that contain the url with endpoint and params
     */
    String getURIString();
}
