/**
 * This package contains custom exception classes used throughout the application.
 * These exceptions are designed to handle specific error conditions and provide
 * meaningful error messages to the user.
 *
 * <p>The custom exceptions in this package extend the standard Java exception
 * classes and can be used to signal specific error conditions that are not
 * adequately represented by the standard exceptions.</p>
 *
 * <p>Exceptions include:</p>
 * <ul>
 *     <li>{@link org.yacineall.api.newsapi.exceptions.HTTPSendInterruptedException}</li>
 *     <li>{@link org.yacineall.api.newsapi.exceptions.IllegalSearchInValueException}}</li>
 *     <li>{@link org.yacineall.api.newsapi.exceptions.JSONConversionException}</li>
 *     <li>{@link org.yacineall.api.newsapi.exceptions.APIKeyIsNullException}</li>
 *     <li>{@link org.yacineall.api.newsapi.exceptions.APIResponseParsingException}</li>
 *     <li>{@link org.yacineall.api.newsapi.exceptions.URIException}</li>
 *     <li>{@link org.yacineall.api.newsapi.exceptions.LoadFromResourcesException}</li>
 * </ul>
 *
 * <p>Each custom exception class provides a detailed message and can include
 * additional information about the error condition.</p>
 *
 * @since 1.0
 */
package org.yacineall.api.newsapi.exceptions;
