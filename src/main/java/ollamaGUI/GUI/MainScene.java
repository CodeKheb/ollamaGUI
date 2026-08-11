package ollamaGUI.GUI;

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
import ollamaGUI.GUI.Buttons.ChooseFile;
import ollamaGUI.GUI.Buttons.SendPrompt;

public class MainScene {
    private static StackPane stackPane = new StackPane();
    private static Scene mainScene = new Scene(stackPane, 800, 600);
    private static String cssPath = MainScene.class.getResource("/style.css").toExternalForm(); 

    private static SendPrompt sendPrompt = new SendPrompt();
    private static ChooseFile chooseFile = new ChooseFile();

    private static ModelSelector modelSelector = new ModelSelector();

    private static GridPane grid = new GridPane();

    public static Scene get() {
        mainScene.getStylesheets().add(
                Objects.requireNonNull(cssPath));
        /* GridPane grid
           Adds the button sendPrompt and chooseFile + modelSelector in the same row
           but different columns
           ColumnConstraints setPercentWidth to determine their relative position from each other
           setHalignment for column1 modelSelector HPos.LEFT so it is close to column0 sendPrompt
           */
        GridPane.setHalignment(chooseFile.get(), HPos.RIGHT);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(column0, column1, column2);
        grid.add(sendPrompt.get(), 0, 2);
        grid.add(chooseFile.get(), 2, 2);
        grid.add(modelSelector.SelectModel(), 1, 2);

        /* VBox root for Vertical Layout add responseArea, userInput and grid
           setVgrow for responseArea to be always Prioritized
           set Insets (to inside from outside) padding to 10
           */
        VBox root = new VBox(10, ResponseArea.get(), UserInput.get(), grid);
        VBox.setVgrow(ResponseArea.get(), Priority.ALWAYS);
        root.setPadding(new Insets(10));

        stackPane.getChildren().add(root);
        stackPane.getChildren().add(Loading.getOverlay());

        return mainScene;
    }
}
