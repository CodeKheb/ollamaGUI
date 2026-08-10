package ollamaGUI.Read;

import java.io.File;
import java.nio.file.Files;

import javafx.application.Platform;
import ollamaGUI.GUI.Loading;
import ollamaGUI.GUI.ModelSelector;
import ollamaGUI.GUI.ResponseArea;
import ollamaGUI.Server.LocalHost;

public class FileManager {

    private static ModelSelector modelSelector = new ModelSelector();

    public static void read(File file) {
        new Thread(() ->
        {
            try {
                String prompt = Files.readString(file.toPath());
                System.out.println(prompt);
                Loading.hide(true);

                Platform.runLater(() ->
                {
                    ResponseArea.clear();
                    Loading.setText(modelSelector.getValue() + " is thinking...");
                    Loading.hide(false);
                });

                LocalHost.OllamaParsedJson(prompt, ResponseArea.get(),
                        () -> Platform.runLater(() ->
                                Loading.hide(true)));
            } catch (Exception err) {
                Platform.runLater(() ->
                        ResponseArea.setResponse(err.getMessage()));
            }
        }).start();
    }

    
}
