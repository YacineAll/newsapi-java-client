package org.yacineall.api.newsapi.exceptions;

public class APIKeyIsNullException extends RuntimeException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public APIKeyIsNullException() {
        super("API key is required and cannot be null");
    }
}
