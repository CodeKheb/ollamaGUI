package ollamaGUI.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OllamaClient {

    private static final URI OLLAMA_URL = URI.create("http://localhost:11434/api/generate");

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Streams a completion from the local Ollama server.
     * Blocks the calling thread; each response token is passed to {@code onChunk}.
     */
    public void generate(String model, String prompt, Consumer<String> onChunk)
            throws IOException, InterruptedException {

        String json = mapper.writeValueAsString(Map.of(
                "model", model,
                "prompt", prompt,
                "stream", true));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(OLLAMA_URL)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String token = extractText(line);
                if (!token.isEmpty()) {
                    onChunk.accept(token);
                }
            }
        }
    }

    private String extractText(String line) throws IOException {
        JsonNode node = mapper.readTree(line);
        if (node.has("response")) {
            return node.get("response").asText();
        }
        return "";
    }
}
