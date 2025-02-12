package com.expensetracker.expensetracker.insights;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LlmClient {

    private final RestTemplate restTemplate;
    private final String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";

    @Value("${gemini.api.key}") // Get API key from application.properties
    private String apiKey;

    public LlmClient() {
        this.restTemplate = new RestTemplate();
    }

    public String generateTextFromPrompt(String prompt) {
        try {
            // Corrected JSON request body
            String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + prompt + "\" }] }] }";

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare request
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    geminiApiUrl + "?key=" + apiKey,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // Parse response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

            // Extract AI-generated response text correctly
            if (responseJson.has("candidates") && responseJson.get("candidates").size() > 0) {
                JsonNode contentNode = responseJson.get("candidates").get(0).path("content").path("parts");

                if (contentNode.isArray() && contentNode.size() > 0) {
                    return contentNode.get(0).path("text").asText();
                }
            }
            return "No valid response from Gemini API";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}
