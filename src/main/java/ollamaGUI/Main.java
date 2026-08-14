package ollamaGUI;

import javafx.application.Application;
import javafx.stage.Stage;
import ollamaGUI.controller.ChatController;
import ollamaGUI.service.OllamaClient;
import ollamaGUI.view.MainScene;
import ollamaGUI.view.MenuScene;
import ollamaGUI.view.components.Loading;
import ollamaGUI.view.components.ModelSelector;
import ollamaGUI.view.components.ResponseArea;
import ollamaGUI.view.components.UserInput;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        ResponseArea responseArea = new ResponseArea();
        UserInput userInput = new UserInput();
        Loading loading = new Loading();
        ModelSelector modelSelector = new ModelSelector();

        ChatController controller = new ChatController(
                new OllamaClient(), responseArea, userInput, loading, modelSelector);

        MainScene mainScene = new MainScene(controller, stage, responseArea, userInput, loading, modelSelector);
        MenuScene menuScene = new MenuScene(() -> stage.setScene(mainScene.get()));

        stage.setScene(menuScene.get());
        stage.setTitle("Ollama GUI");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
