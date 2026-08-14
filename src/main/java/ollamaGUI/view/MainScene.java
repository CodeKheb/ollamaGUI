package ollamaGUI.view;

import java.util.Objects;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ollamaGUI.controller.ChatController;
import ollamaGUI.view.Buttons.ChooseFile;
import ollamaGUI.view.Buttons.SendPrompt;
import ollamaGUI.view.components.Loading;
import ollamaGUI.view.components.ModelSelector;
import ollamaGUI.view.components.ResponseArea;
import ollamaGUI.view.components.UserInput;

public class MainScene {

    private final ChatController controller;
    private final Stage stage;
    private final ResponseArea responseArea;
    private final UserInput userInput;
    private final Loading loading;
    private final ModelSelector modelSelector;

    public MainScene(ChatController controller, Stage stage, ResponseArea responseArea,
                     UserInput userInput, Loading loading, ModelSelector modelSelector) {
        this.controller = controller;
        this.stage = stage;
        this.responseArea = responseArea;
        this.userInput = userInput;
        this.loading = loading;
        this.modelSelector = modelSelector;
    }

    public Scene get() {
        StackPane stackPane = new StackPane();
        Scene mainScene = new Scene(stackPane, 800, 600);
        mainScene.getStylesheets().add(
                Objects.requireNonNull(MainScene.class.getResource("/style.css").toExternalForm()));

        SendPrompt sendPrompt = new SendPrompt(controller::sendPrompt);
        ChooseFile chooseFile = new ChooseFile(controller::sendFile, stage);

        /* GridPane: sendPrompt, chooseFile and modelSelector in the same row but different columns.
           ColumnConstraints setPercentWidth to determine their relative position from each other.
           setHalignment on column1 (modelSelector) HPos.LEFT so it stays close to column0 (sendPrompt). */
        GridPane grid = new GridPane();
        GridPane.setHalignment(chooseFile.getNode(), HPos.RIGHT);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(column0, column1, column2);
        grid.add(sendPrompt.getNode(), 0, 2);
        grid.add(chooseFile.getNode(), 2, 2);
        grid.add(modelSelector.getNode(), 1, 2);

        /* VBox root for vertical layout: responseArea, userInput and grid.
           responseArea grows to fill remaining vertical space, padding 10. */
        VBox root = new VBox(10, responseArea.getNode(), userInput.getNode(), grid);
        VBox.setVgrow(responseArea.getNode(), Priority.ALWAYS);
        root.setPadding(new Insets(10));

        stackPane.getChildren().addAll(root, loading.getNode());

        return mainScene;
    }
}
