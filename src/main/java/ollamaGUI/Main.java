package ollamaGUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

import static java.lang.Double.MAX_VALUE;

public class Main extends Application {

    public static ComboBox<String> modelSelector;
    public static final VBox loadingOverlay = new VBox(20);
    public static Label loadingMessage = new Label();

    private TextArea responseArea;
    private TextField userInput;


    @Override
    public void start(Stage stage) {

        /* ComboBox<String> modelSelector add models in a ComboBox
        set the default value to qwen2.5-coder:0.5b
        the static ComboBox will then get called in LocalHost,
        and then it changes the String "model" value
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
        responseArea.setMaxWidth(MAX_VALUE);
        responseArea.setMaxHeight(MAX_VALUE);

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
                System.out.println("ERROR");
            }
        });


        /* GridPane grid
        Adds the button sendPrompt and chooseFile + modelSelector in the same row
        but different columns
        ColumnConstraints setPercentWidth to determine their relative position from each other
        setHalignment for column1 modelSelector HPos.LEFT so it is close to column0 sendPrompt
        */
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



        /* VBox root for Vertical Layout add responseArea, userInput and grid
        setVgrow for responseArea to be always Prioritized
        set Insets (to inside from outside) padding to 10
        */
        VBox root = new VBox(10, responseArea, userInput, grid);
        VBox.setVgrow(responseArea, Priority.ALWAYS);
        root.setPadding(new Insets(10));


        /* loadingOverlay and loadingMessage initialized as static
        setAlignment to Center
        add a ProgressIndicator loading
        add children loading and loadingMessage
        setVisible false : will be set true when sendFile and getMessage and readFile is in action
        */
        loadingOverlay.setAlignment(Pos.CENTER);
        ProgressIndicator loading = new ProgressIndicator();
        loadingOverlay.getChildren().addAll(loading, loadingMessage);
        loadingOverlay.setVisible(false);


        // stackPane to add the root and loadingOverlay, initialized in the Scene mainScene
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(root, loadingOverlay);
        Scene mainScene = new Scene(stackPane, 800, 600);

        // CSS getter in /resources/style.css
        loadingOverlay.getStyleClass().add("loadingOverlay");
        mainScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm()
        );

        // show the Scene mainScene
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
            loadingOverlay.setVisible(false);

            new Thread(() ->
            {
                try {
                    Platform.runLater(() ->
                    {
                        responseArea.clear();
                        loadingMessage.setText(modelSelector.getValue() + " is thinking...");
                        loadingOverlay.setVisible(true);
                    });
                    LocalHost.OllamaParsedJson(prompt, responseArea,
                            () -> Platform.runLater(() ->
                                    loadingOverlay.setVisible(false)));
                } catch (Exception err) {
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
                loadingOverlay.setVisible(false);

                Platform.runLater(() ->
                {
                    responseArea.clear();
                    loadingMessage.setText(modelSelector.getValue() + " is thinking...");
                    loadingOverlay.setVisible(true);
                });

                LocalHost.OllamaParsedJson(prompt, responseArea,
                        () -> Platform.runLater(() ->
                                loadingOverlay.setVisible(false)));
            } catch (Exception err) {
                Platform.runLater(() ->
                        responseArea.appendText(err.getMessage()));
            }
        }).start();
    }
}