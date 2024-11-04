package org.yacineall.newsapi.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.yacineall.newsapi.exceptions.JSONConversionException;
import org.yacineall.newsapi.exceptions.LoadFromResourcesException;
import org.yacineall.utils.Tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ToolsTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    private static Path tempJsonFile;
    private static final String jsonContent = """
            {
              "name": "Test",
              "age": 25
            }
            """;

    @BeforeAll
    static void setup() throws IOException {
        tempJsonFile = Files.createFile(
                Paths.get(
                        Objects.requireNonNull(ToolsTest.class.getClassLoader().getResource(".")).getPath(),
                "tmpFile.json"
                )
        );
        Files.writeString(tempJsonFile, jsonContent);
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.delete(tempJsonFile);
    }


    @Test
    void testReadJsonFromResource() {
        JsonNode jsonNode = Tools.readJsonFromResource(tempJsonFile.getFileName().toString());
        assertNotNull(jsonNode);
        assertEquals("Test", jsonNode.get("name").asText());
        assertEquals(25, jsonNode.get("age").asInt());
    }

    @Test
    void testReadJsonFromResource_notFound() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> Tools.readJsonFromResource("nonexistent.json"));
        assertTrue(exception.getMessage().contains("Resource not found"));
    }

    @Test
    void testReadJsonAsString() {
        String jsonString = Tools.readJsonAsString(tempJsonFile.getFileName().toString());
        assertEquals(jsonContent.trim(), jsonString.trim());
    }

    @Test
    void testReadJsonAsString_notFound() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> Tools.readJsonAsString("nonexistent.json"));
        assertTrue(exception.getMessage().contains("Resource not found"));
    }

    @Test
    void testReadJsonAs() {
        TestData testData = Tools.readJsonAs(tempJsonFile.getFileName().toString(), TestData.class);
        assertNotNull(testData);
        assertEquals("Test", testData.getName());
        assertEquals(25, testData.getAge());
    }

    @Test
    void testReadJsonAs_invalidJson() {
        LoadFromResourcesException exception = assertThrows(
                LoadFromResourcesException.class,
                () -> Tools.readJsonAs(tempJsonFile.getFileName().toString(), InvalidData.class));
        String exceptionMessage = String.format(
                "Reading source '%s' as '%s' throw an exception",
                tempJsonFile.getFileName().toString(), InvalidData.class
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void testToJson() {
        TestData testData = new TestData("Sample", 30);
        String jsonString = Tools.toJson(testData);
        assertNotNull(jsonString);
        assertTrue(jsonString.contains("\"name\" : \"Sample\""));
        assertTrue(jsonString.contains("\"age\" : 30"));
    }

    @Test
    void testToJson_invalidObject() {
        InvalidData invalidData = new InvalidData();
        JSONConversionException exception = assertThrows(
                JSONConversionException.class,
                () -> Tools.toJson(invalidData)
        );
        String exceptionMessage = String.format("Failed to convert %s to json", invalidData);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    // Inner classes to use for deserialization testing
    private static class TestData {
        private String name;
        private int age;

        public TestData() {
        }

        public TestData(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    // A class that will throw an exception if Jackson tries to serialize it
    private static class InvalidData {
        private final Object invalidField = new Object() {
            @Override
            public String toString() {
                throw new RuntimeException("Cannot serialize this object");
            }
        };
    }
}
