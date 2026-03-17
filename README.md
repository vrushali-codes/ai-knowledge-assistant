# AI Knowledge Assistant (RAG-based)

## Overview

This project is a Spring Boot-based AI knowledge assistant that implements a lightweight Retrieval-Augmented Generation (RAG) pipeline to enable semantic querying across technical documents.

The system retrieves relevant document context based on user queries and integrates with an LLM API to generate grounded, context-aware responses.

It simulates how modern backend services integrate AI capabilities into production systems.

## Architecture

User → REST API → Retrieval Layer → Context Construction → LLM API → Response

- Controller layer handles incoming requests
- Retrieval layer fetches relevant document chunks
- Context orchestration combines retrieved data
- LLM service generates responses using external AI APIs
- Response is returned via REST endpoint

## Request Flow

1. User sends a query via `/api/ask`
2. Backend extracts meaningful keywords
3. Retrieval service finds relevant document chunks
4. Context is constructed from top matches
5. Context + query is sent to LLM API
6. AI-generated response is returned to the user

## Features

- REST API built with Spring Boot
- Retrieval-Augmented Generation (RAG) pipeline
- Context-aware AI responses using LLM APIs
- Swagger UI for API testing and documentation
- Stop-word filtering for improved retrieval accuracy
- Logging and latency tracking for observability
- Input validation and graceful error handling

## Tech Stack

- Java 17
- Spring Boot
- REST APIs
- Swagger (OpenAPI)
- Groq / LLM API (OpenAI-compatible)
- Maven

## Sample Request

POST /api/ask

{
  "question": "What is Kafka consumer lag?"
}

## Sample Response

{
  "answer": "Kafka consumer lag occurs when consumers process messages slower than producers.",
  "context": "Kafka consumer lag occurs when consumers process messages slower than producers."
}

## Future Improvements

- Replace keyword-based retrieval with embeddings and vector database
- Add caching for frequent queries
- Support multiple document sources
- Add authentication and rate limiting