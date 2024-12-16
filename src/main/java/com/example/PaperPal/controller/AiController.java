package com.example.PaperPal.controller;

import com.example.PaperPal.service.AiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController {

    private AiService aiService;

    AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generateStream")
    public ResponseEntity<String> generateStream(@RequestParam("prompt") String prompt) {
       return aiService.getAiResponse(prompt);
    }
}
