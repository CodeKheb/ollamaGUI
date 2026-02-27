package ollamaGUI;

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

public class Main extends Application {

    public static ComboBox<String> modelSelector;

    private TextArea responseArea;
    private TextField userInput;


    @Override
    public void start(Stage stage) {

        /* ComboBox<String> modelSelector add models in a ComboBox
        set the default value to qwen2.5-coder:0.5b
        the static ComboBox will then get called in LocalHost and change the model value
         */
        modelSelector = new ComboBox<>();
        modelSelector.getItems().addAll(
                "qwen2.5-coder:0.5b",
                "phi",
                "MeetSolanki/MeetAISmall",
                "dolphin-mistral",
                "llama3"
        );
        modelSelector.setValue("qwen2.5-coder:0.5b");


        responseArea = new TextArea();
        responseArea.setEditable(false);
        responseArea.setWrapText(true);
        responseArea.setMaxWidth(Double.MAX_VALUE);
        responseArea.setMaxHeight(Double.MAX_VALUE);

        userInput = new TextField();
        userInput.setPromptText("What's on your mind today?");


        Button sendPrompt = new Button("Send");
        //Make it the Default Button so javaFX can know what to click if you pressed enter
        sendPrompt.setDefaultButton(true);

        Button chooseFile = new Button("Choose File");



        /* sendPrompt Button Listeners
        setOnAction click, if clicked call method
        setOnKeyPressed enter, if entered, call method
         */
        sendPrompt.setOnAction(click -> getMessage());
        sendPrompt.setOnKeyPressed(enter -> {
            if (enter.getCode() == KeyCode.ENTER) {
                getMessage();
            }
        });

        /* chooseFile Button listener
        setOnAction choose, if clicked -> Open fileChooser
        if file is not null
        Print the path (Error checking)
        readFile the file
        else (ERROR CHECKING)
        */
        chooseFile.setOnAction(choose ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select A File");

            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                readFile(file);
            } else {
                System.out.println("ERRORRROROROROR");
            }
        });


        GridPane grid = new GridPane();
        GridPane.setHalignment(chooseFile, HPos.RIGHT);

        grid.add(sendPrompt, 0, 2);
        grid.add(chooseFile, 2, 2);
        grid.add(modelSelector, 1, 2);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(column0, column1, column2);



        VBox root = new VBox(10, responseArea, userInput, grid);


        VBox.setVgrow(responseArea, Priority.ALWAYS);
        root.setPadding(new Insets(10));
        Scene mainScene = new Scene(root, 800, 600);

        // CSS getter in /resources/style.css requires it to be non null
        mainScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm()
        );

        stage.setScene(mainScene);
        stage.setTitle("Ollama GUI using javaFX");
        stage.show();
    }

    // getMessage to get ollama response from the Local Host class, JSON parsed by Jackson
    private void getMessage() {
        String prompt = userInput.getText();
        if (!prompt.isBlank())
        {
            userInput.clear();
            new Thread(() ->
            {
                try {
                    responseArea.clear();
                    LocalHost.OllamaParsedJson(prompt, responseArea);
                } catch (Exception err) {
                    err.printStackTrace();
                    Platform.runLater(() ->
                            responseArea.appendText(err.getMessage()));
                }
            }).start();
        }
    }

    // readFile to readFile from FileChooser chooseFile
    private void readFile(File file) {
        new Thread(() ->
        {
            try {
                String prompt = Files.readString(file.toPath());
                System.out.println(prompt);
                System.out.println(prompt.length());


                Platform.runLater(() ->
                {
                    responseArea.clear();
                    responseArea.appendText("Reading file: " + file.getName() + "\n");
                });

                LocalHost.OllamaParsedJson(prompt, responseArea);
            } catch (Exception err) {
                err.printStackTrace();
                Platform.runLater(() ->
                        responseArea.appendText(err.getMessage()));
            }
        }).start();
    }
}