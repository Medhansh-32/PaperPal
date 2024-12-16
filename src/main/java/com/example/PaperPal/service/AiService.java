package com.example.PaperPal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final String apiKey;
    private RestTemplate restTemplate;

    @Autowired
    public AiService(@Value("${apiKey}") String apiKey, RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
        this.apiKey = apiKey;
    }
    public ResponseEntity<String> getAiResponse(String prompt){
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
        HttpEntity<String> entity = getStringHttpEntity(prompt);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {});

        if (response != null && response.getBody().containsKey("candidates")) {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            if (!candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                if (content != null && content.containsKey("parts")) {
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                    if (!parts.isEmpty()) {
                        return new ResponseEntity<>((String) parts.get(0).get("text"), HttpStatus.OK);  // Extract the text
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private static HttpEntity<String> getStringHttpEntity(String prompt) {
        String requestBody = "{\n" +
                "  \"contents\": [{\n" +
                "    \"parts\": [{\n" +
                "      \"text\": \"" + prompt + "\"\n" +
                "    }]\n" +
                "  }]\n" +
                "}";

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the HTTP entity with the request body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return entity;
    }
}
