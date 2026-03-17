package com.assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assistant.model.DocumentChunk;
import com.assistant.service.RetrievalService;

@RestController
public class TestController {

    private final RetrievalService retrievalService;

    public TestController(RetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello AI Assistant!";
    }

    @GetMapping("/test-retrieval")
    public List<DocumentChunk> testRetrieval(@RequestParam String question) {
        return retrievalService.retrieveRelevantChunks(question);
    }

}
