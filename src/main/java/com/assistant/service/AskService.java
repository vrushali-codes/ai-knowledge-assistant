package com.assistant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.assistant.model.AskResponse;
import com.assistant.model.DocumentChunk;

@Service
public class AskService {

    private static final Logger log = LoggerFactory.getLogger(AskService.class);
    
    private final RetrievalService retrievalService;
    private final LlmService llmService;

    public AskService(RetrievalService retrievalService, LlmService llmService) {
        this.retrievalService = retrievalService;
        this.llmService = llmService;
    }

    public AskResponse askQuestion(String question) {
        long startTime = System.currentTimeMillis();

        log.info("Received question: {}", question);

        List<DocumentChunk> chunks = retrievalService.retrieveRelevantChunks(question);
        log.info("Retrieved {} relevant chunks", chunks.size());

        for (DocumentChunk chunk : chunks) {
            log.info("Chunk score={} content={}", chunk.getScore(), chunk.getContent());
        }

        String context = chunks.stream()
                .map(DocumentChunk::getContent)
                .collect(Collectors.joining("\n"));

        if (context.isBlank()) {
            log.warn("No relevant context found for question: {}", question);
            return new AskResponse(
                    "I could not find relevant information in the provided documents.",
                    ""
            );
        }

        long llmStart = System.currentTimeMillis();
        String answer = llmService.generateAnswer(question, context);
        long llmEnd = System.currentTimeMillis();

        log.info("LLM response generated in {} ms", (llmEnd - llmStart));

        long endTime = System.currentTimeMillis();
        log.info("Total request completed in {} ms", (endTime - startTime));

        return new AskResponse(answer, context);
    }

}
