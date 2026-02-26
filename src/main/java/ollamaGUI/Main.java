package ollamaGUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private TextArea responseArea;
    private TextField userInput;
    private Button sendPrompt;


    public static void main(String[] a) throws IOException, InterruptedException {
        launch(a);
    }

    @Override
    public void start(Stage stage) throws Exception {

        responseArea = new TextArea();
        responseArea.setEditable(false);
        responseArea.setWrapText(true);
        responseArea.setMaxWidth(Double.MAX_VALUE);
        responseArea.setMaxHeight(Double.MAX_VALUE);

        userInput = new TextField();
        userInput.setPromptText("What's on your mind today?");

        sendPrompt = new Button("Send");
        sendPrompt.setOnAction(e -> {
           String prompt = userInput.getText();
           if (!prompt.isBlank()) {
               userInput.clear();
               new Thread(() -> {
                   try {
                       responseArea.clear();
                       LocalHost.OllamaParsedJson(prompt, responseArea);
                   }
                   catch (Exception err) {
                      err.printStackTrace();
                      Platform.runLater(() ->
                              responseArea.appendText(err.getMessage()));
                   }
               }).start();
           }
        });


        VBox root = new VBox(10, responseArea, userInput, sendPrompt);

        VBox.setVgrow(responseArea, Priority.ALWAYS);
        root.setPadding(new Insets(10));
        Scene mainScene = new Scene(root, 800, 600);
        mainScene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
        stage.setScene(mainScene);
        stage.setTitle("Ollama GUI using javaFX");
        stage.show();



    }
}