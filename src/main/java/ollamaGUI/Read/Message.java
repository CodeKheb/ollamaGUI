package ollamaGUI.Read;

import javafx.application.Platform;
import ollamaGUI.GUI.UserInput;
import ollamaGUI.GUI.Loading;
import ollamaGUI.GUI.ModelSelector;
import ollamaGUI.GUI.ResponseArea; 
import ollamaGUI.Server.LocalHost;

public class Message {
    private static ModelSelector modelSelector = new ModelSelector();

    public static void get() {
        String prompt = UserInput.getText();
        if (!prompt.isBlank())
        {
            UserInput.clear();
            Loading.hide(true);
            new Thread(() ->
            {
                try {
                    Platform.runLater(() ->
                    {
                        ResponseArea.clear();
                        Loading.setText(modelSelector.getValue() + " is thinking...");
                        System.out.println("Message Sent");
                        Loading.hide(false);
                    });
                    LocalHost.OllamaParsedJson(prompt, ResponseArea.get(),
                            () -> Platform.runLater(() ->
                                    Loading.hide(true)));
                        System.out.println("Got Message");
                } catch (Exception err) {
                    Platform.runLater(() ->
                            ResponseArea.setResponse(err.getMessage()));
                }
            }).start();
        } else if (prompt.isBlank()) {
            System.out.println("No prompt");

        }
    }
}
