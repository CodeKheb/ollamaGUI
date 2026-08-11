package ollamaGUI;

import javafx.application.Application;
import javafx.stage.Stage;
import ollamaGUI.GUI.MainScene;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(MainScene.get());
        stage.setTitle("Ollama GUI using javaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
