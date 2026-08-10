package ollamaGUI;

import javafx.application.Application;
import javafx.stage.Stage;
import ollamaGUI.GUI.MainScene;
import ollamaGUI.GUI.Buttons.ChooseFile;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainScene.setStage(stage);
        stage.setScene(MainScene.get());
        stage.setTitle("Ollama GUI using javaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
