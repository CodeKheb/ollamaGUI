# Ollama GUI
Local Hosted AI User Interface using JavaFX and Jackson

## Maven Dependencies
Jackson (JSON Parser)
``` xml
    <dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.21.1</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.21.1</version>
        </dependency>
```
JavaFX
``` xml
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>25.0.2</version>
        </dependency>
```
build
``` xml        
    <build>
        <plugins>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>ollamaGUI.Main</mainClass>
                    <options>
                        <option>--enable-native-access=javafx.graphics</option>
                    </options>
                </configuration>
            </plugin>
        </plugins>
    </build>
```


## Features
### Local Server Ollama API call
Imports
``` java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
```
HTTP Request
``` java
        HttpClient client = HttpClient.newHttpClient();
        // Default Ollama localhost:11434
        // DO NOT WORRY ABOUT THIS LEAKING, IT'S ONLY LOCALLY HOSTED
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
```
Streaming Response Text
``` java 
        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        // Read the Ollama response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                
                String extractedText = extractText(line);
                if (!extractedText.isEmpty())
                {
                    javafx.application.Platform.runLater(() ->
                            textArea.appendText(extractedText));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
```
### Jackson JSON Parser 
Imports
``` java
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
```
Prompt Handler
``` java
        String model = Main.modelSelector.getValue();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Map.of(
                "model", model,
                "prompt", prompt,
                "stream", true
        ));
```
Response Handler
``` java 
    static String extractText(String line) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(line);


// Finds the response and extracts the contents as Text
        if (node.has("response")) {
            return node.get("response").asText();
        } else {
            return "";
        }
    }
}
```

## JavaFX
Imports
``` java
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;
```
### Current Features
- CSS Styling
- Grid 
- Buttons
- Action "Enter" Key + Click to Send Prompt
 > To be continued, still experimenting with the UI/UX



To do list:
- Ollama Installer using terminal Curl (Process Builder)
- GUI Animations and Themes
