package com.assistant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DocumentChunk {
    private String content;
    private int score;

}
