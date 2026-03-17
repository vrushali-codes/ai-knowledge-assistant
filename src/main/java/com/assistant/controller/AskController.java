package com.assistant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assistant.model.AskRequest;
import com.assistant.model.AskResponse;
import com.assistant.service.AskService;

@RestController
@RequestMapping("/api")
public class AskController {

    private final AskService askService;

    public AskController(AskService askService) {
        this.askService = askService;
    }

    @PostMapping("/ask")
    public ResponseEntity<AskResponse> ask(@RequestBody AskRequest request) {
        if (request == null || request.getQuestion() == null || request.getQuestion().trim().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new AskResponse("Question must not be empty.", ""));
        }

        AskResponse response = askService.askQuestion(request.getQuestion().trim());
        return ResponseEntity.ok(response);
    }
}
