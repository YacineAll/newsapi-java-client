package org.yacineall.newsapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.yacineall.newsapi.exceptions.JSONConversionException;
import org.yacineall.newsapi.exceptions.LoadFromResourcesException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Utility class providing methods for JSON processing and resource loading.
 */
public final class Tools {
    /**
     * Duplicated exception message.
     */
    private static final String COMMON_MESSAGE = "Resource not found: %s";
    /**
     * The object mapper used to map objects.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Tools() {
        super();
    }

    /**
     * Reads a JSON resource from the classpath and returns it as a {@link JsonNode}.
     *
     * @param resourcePath The path to the JSON resource.
     * @return The JSON content as a {@link JsonNode}.
     * @throws LoadFromResourcesException if the resource cannot be found or read.
     */
    public static JsonNode readJsonFromResource(final String resourcePath) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = Tools.class.getClassLoader().getResourceAsStream(resourcePath)) {
            Objects.requireNonNull(inputStream, String.format(COMMON_MESSAGE, resourcePath));
            return mapper.readTree(inputStream);
        } catch (IOException e) {
            throw new LoadFromResourcesException(
                    String.format("Reading source '%s' as JsonNode throw an exception", resourcePath), e);
        }
    }

    /**
     * Reads a JSON resource from the classpath and returns it as a {@link String}.
     *
     * @param resourcePath The path to the JSON resource.
     * @return The JSON content as a {@link String}.
     * @throws LoadFromResourcesException if the resource cannot be found or read.
     */
    public static String readJsonAsString(final String resourcePath) {
        try (InputStream is = Tools.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            Objects.requireNonNull(is, String.format(COMMON_MESSAGE, resourcePath));
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new LoadFromResourcesException(
                    String.format("Reading source '%s' as String throw an exception", resourcePath), e);
        }
    }

    /**
     * Reads a JSON resource from the classpath and returns it as an instance of the specified class.
     *
     * @param resourcePath The path to the JSON resource.
     * @param tClass The class to which the JSON content should be deserialized.
     * @param <T> The type of the class.
     * @return The deserialized instance of the specified class.
     * @throws LoadFromResourcesException if the resource cannot be found or read.
     */
    public static <T> T readJsonAs(final String resourcePath, final Class<T> tClass) {
        try (InputStream is = Tools.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return OBJECT_MAPPER.readValue(content, tClass);
        } catch (Exception e) {
            throw new LoadFromResourcesException(
                    String.format("Reading source '%s' as '%s' throw an exception", resourcePath, tClass), e);
        }
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to convert.
     * @return The JSON string representation of the object.
     * @throws JSONConversionException if the object cannot be converted to JSON.
     */
    public static String toJson(final Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JSONConversionException(String.format("Failed to convert %s to json", object), e);
        }
    }
}
