package com.assistant.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.assistant.model.DocumentChunk;

import jakarta.annotation.PostConstruct;

@Service
public class RetrievalService {

    private List<String> documentLines = new ArrayList<>();

    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "the", "is", "are", "am", "was", "were",
            "what", "when", "where", "why", "how",
            "to", "for", "of", "in", "on", "at", "by", "with",
            "and", "or", "do", "does", "did", "be", "this", "that"
    );

    @PostConstruct
    public void loadDocuments() throws Exception {
        ClassPathResource resource = new ClassPathResource("docs.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            documentLines = reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .collect(Collectors.toList());
        }
    }

    public List<DocumentChunk> retrieveRelevantChunks(String question) {
        Set<String> queryWords = Arrays.stream(question.toLowerCase().split("\\W+"))
                .filter(word -> !word.isBlank())
                .collect(Collectors.toSet());

        return documentLines.stream()
                .map(line -> new DocumentChunk(line, calculateScore(line, queryWords)))
                .filter(chunk -> chunk.getScore() > 0)
                .sorted(Comparator.comparingInt(DocumentChunk::getScore).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private int calculateScore(String line, Set<String> queryWords) {
        Set<String> lineWords = extractMeaningfulWords(line);

        int score = 0;
        for (String word : queryWords) {
            if (lineWords.contains(word)) {
                score++;
            }
        }
        return score;
    }

    private Set<String> extractMeaningfulWords(String text) {
        return Arrays.stream(text.toLowerCase().split("\\W+"))
                .filter(word -> !word.isBlank())
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toSet());
    }

}
