package com.assistant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AskResponse {

    private String answer;
    private String context;
    
}