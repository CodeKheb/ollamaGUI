package ollamaGUI.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import ollamaGUI.service.OllamaClient;
import ollamaGUI.view.components.Loading;
import ollamaGUI.view.components.ModelSelector;
import ollamaGUI.view.components.ResponseArea;
import ollamaGUI.view.components.UserInput;

public class ChatController {

    private final OllamaClient client;
    private final ResponseArea responseArea;
    private final UserInput userInput;
    private final Loading loading;
    private final ModelSelector modelSelector;

    public ChatController(OllamaClient client, ResponseArea responseArea, UserInput userInput,
                          Loading loading, ModelSelector modelSelector) {
        this.client = client;
        this.responseArea = responseArea;
        this.userInput = userInput;
        this.loading = loading;
        this.modelSelector = modelSelector;
    }

    /** Sends the current user input to the selected model. */
    public void sendPrompt() {
        String prompt = userInput.getText();
        if (prompt.isBlank()) {
            System.out.println("No prompt");
            return;
        }
        userInput.clear();
        send(prompt);
    }

    /** Sends the contents of the chosen file to the selected model. */
    public void sendFile(File file) {
        new Thread(() -> {
            try {
                String prompt = Files.readString(file.toPath());
                send(prompt);
            } catch (Exception err) {
                Platform.runLater(() -> responseArea.append(err.getMessage()));
            }
        }).start();
    }

    private void send(String prompt) {
        String model = modelSelector.getValue();
        loading.hide();

        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    responseArea.clear();
                    loading.setText(model + " is thinking...");
                    loading.show();
                });

                AtomicBoolean streamingStarted = new AtomicBoolean(false);
                client.generate(model, prompt, token ->
                        Platform.runLater(() -> {
                            if (streamingStarted.compareAndSet(false, true)) {
                                loading.hide();
                            }
                            responseArea.append(token);
                        }));
            } catch (Exception err) {
                Platform.runLater(() -> {
                    loading.hide();
                    responseArea.append(err.getMessage());
                });
            }
        }).start();
    }
}
