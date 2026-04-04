package com.example.productcomparison.presentation.controller;

import com.example.productcomparison.ProductComparisonApiApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = ProductComparisonApiApplication.class)
@AutoConfigureMockMvc
@DisplayName("HTTP JSON Integration Tests")
class HttpJsonDynamicIntegrationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String TESTCASES_DIR = "src/test/resources/testcases";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @TestFactory
    @DisplayName("Scenario files")
    Collection<DynamicTest> scenarioTests() throws IOException {
        List<String> jsonFiles = Files.list(Paths.get(TESTCASES_DIR))
                .filter(Files::isRegularFile)
                .map(p -> p.getFileName().toString())
                .filter(name -> name.endsWith(".json"))
                .sorted()
                .toList();

        List<DynamicTest> tests = new ArrayList<>();

        for (String filename : jsonFiles) {
            List<String> lines = readLines(filename);

            for (int i = 0; i < lines.size(); i++) {
                final String line = lines.get(i);
                final int lineNumber = i + 1;
                final String testName = filename + " - request " + lineNumber;
                tests.add(dynamicTest(testName, () -> runScenario(line)));
            }
        }

        return tests;
    }

    private void runScenario(String jsonLine) throws Exception {
        JsonNode scenario = MAPPER.readTree(jsonLine);
        JsonNode request = scenario.get("request");
        JsonNode response = scenario.get("response");

        String method = request.get("method").asText();
        String url = request.get("url").asText();
        String body = request.get("body").toString();
        int expectedStatus = response.get("status_code").asInt();

        MockHttpServletResponse actual = executeRequest(method, url, body);

        assertThat(actual.getStatus())
                .as("Status code for %s %s", method, url)
                .isEqualTo(expectedStatus);

        if (expectedStatus == 200) {
            JsonNode expectedBody = response.get("body");
            if (expectedBody != null && !expectedBody.isNull()) {
                JsonNode actualBody = MAPPER.readTree(actual.getContentAsString(StandardCharsets.UTF_8));
                validateBody(method, url, expectedBody, actualBody);
            }
        }
    }

    private MockHttpServletResponse executeRequest(String method, String url, String body) throws Exception {
        return switch (method) {
            case "GET" -> mockMvc.perform(get(url)).andReturn().getResponse();
            case "POST" -> mockMvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andReturn().getResponse();
            case "PUT" -> mockMvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andReturn().getResponse();
            case "DELETE" -> mockMvc.perform(delete(url)).andReturn().getResponse();
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
    }

    private void validateBody(String method, String url, JsonNode expected, JsonNode actual) throws IOException {
        if (expected.isArray()) {
            List<JsonNode> expectedList = MAPPER.readValue(expected.toString(), new TypeReference<>() {
            });
            List<JsonNode> actualList = MAPPER.readValue(actual.toString(), new TypeReference<>() {
            });

            assertThat(actualList)
                    .as("Response array size for %s %s", method, url)
                    .hasSameSizeAs(expectedList);

            for (int i = 0; i < expectedList.size(); i++) {
                assertThat(actualList.get(i))
                        .as("Response element [%d] for %s %s", i, method, url)
                        .isEqualTo(expectedList.get(i));
            }
        } else {
            assertThat(actual)
                    .as("Response body for %s %s", method, url)
                    .isEqualTo(expected);
        }
    }

    private List<String> readLines(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("testcases/" + filename);
        try (InputStream is = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines()
                    .filter(line -> !line.isBlank())
                    .collect(Collectors.toList());
        }
    }
}
