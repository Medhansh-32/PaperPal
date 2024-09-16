package com.example.PaperPal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController {

    private final OllamaChatModel chatModel;

    @Autowired
    public AiController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }


    @GetMapping("/generateStream")
    public String generateStream(@RequestParam("prompt") String prompt) {
        log.info("Prompt : "+prompt);
        String response= chatModel.call(prompt);
        log.info("Response : "+response);
        return response;
    }
}
