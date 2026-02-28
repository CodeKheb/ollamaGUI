package ollamaGUI;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;

public class LocalHost {

    public static void OllamaParsedJson(String prompt,
                                        javafx.scene.control.TextArea textArea,
                                        Runnable done)
            throws IOException, InterruptedException {

        String model = Main.modelSelector.getValue();
        // Jackson Parser for inputting prompts to send to Ollama
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Map.of(
                "model", model,
                "prompt", prompt,
                "stream", true
        ));

        HttpClient client = HttpClient.newHttpClient();

        // API request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // InputStream for Streaming text
        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());


        // Read the Ollama response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // for debugging JsonNode + ObjectMapper reader
                // System.out.println(line);
                JsonNode node = mapper.readTree(line);
                if (line.isBlank()) continue;


                String extractedText = extractText(line);
                if (!extractedText.isEmpty())
                {
                    javafx.application.Platform.runLater(() ->
                            textArea.appendText(extractedText));
                }

                // loadingOverlay set visible = false, after the first token
                if (!node.isEmpty())
                {
                    Platform.runLater(done);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Another JSON parser for the response of ollama
    static String extractText(String line) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(line);

        if (node.has("response")) {
            return node.get("response").asText();
        } else {
            return "";
        }
    }
}
