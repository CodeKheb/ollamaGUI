# Ollama GUI

A desktop chat interface for interacting with locally-hosted AI models (Ollama), built with JavaFX.

> **Early development.** The project architecture is still being designed and will change. Everything below is subject to change.

## Stack

- Java 25
- JavaFX 25
- Maven
- Jackson (JSON)
- Ollama (local server)

## Prerequisites

- JDK 25
- Maven
- [Ollama](https://ollama.com) installed and running locally (defaults to `http://localhost:11434`)

## Getting Started

```bash
mvn clean javafx:run
```

## Features (in progress)

- Chat with locally-hosted Ollama models
- Select which model to talk to
- Stream responses as they are generated
- Send prompts from a file
- Loading indicator while the model is thinking

## Status / Roadmap

- The architecture is actively being restructured
- Planned: Ollama installer, themes, animations

## Project Layout

- `view` — JavaFX user interface (scenes, components, buttons)
- `controller` — wiring between the view and the services
- `service` — communication with the local Ollama API

> Note: The layout is still settling and may change.
