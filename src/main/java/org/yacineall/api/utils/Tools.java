package org.yacineall.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.yacineall.api.newsapi.exceptions.JSONConversionException;
import org.yacineall.api.newsapi.exceptions.LoadFromResourcesException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Tools {
    private static final String COMMON_MESSAGE = "Resource not found: %s";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Tools() {
        super();
    }

    public static JsonNode readJsonFromResource(String resourcePath) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = Tools.class.getClassLoader().getResourceAsStream(resourcePath)) {
            Objects.requireNonNull(inputStream, String.format(COMMON_MESSAGE, resourcePath));
            return mapper.readTree(inputStream);
        } catch (IOException e) {
            throw new LoadFromResourcesException(
                    String.format("Reading source '%s' as JsonNode throw an exception", resourcePath), e);
        }
    }

    public static  String readJsonAsString(String resourcePath) {
        try (InputStream is = Tools.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            Objects.requireNonNull(is, String.format(COMMON_MESSAGE, resourcePath));
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new LoadFromResourcesException(
                    String.format("Reading source '%s' as String throw an exception", resourcePath), e);
        }
    }
    public static <T> T readJsonAs(String resourcePath, Class<T> tClass) {
        try (InputStream is = Tools.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return objectMapper.readValue(content, tClass);
        } catch (Exception e) {
            throw new LoadFromResourcesException(
                    String.format("Reading source '%s' as '%s' throw an exception", resourcePath, tClass), e);
        }
    }

    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JSONConversionException(String.format("Failed to convert %s to json", object), e);
        }
    }
}
