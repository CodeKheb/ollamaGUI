package ollamaGUI.view;

import java.util.Objects;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MenuScene {

    private final Runnable onEnter;

    public MenuScene(Runnable onEnter) {
        this.onEnter = onEnter;
    }

    public Scene get() {
        Button enterButton = new Button("Enter");
        enterButton.setOnAction(e -> onEnter.run());

        StackPane root = new StackPane(enterButton);
        root.setAlignment(Pos.CENTER);

        Scene menuScene = new Scene(root, 800, 600);
        menuScene.getStylesheets().add(
                Objects.requireNonNull(MenuScene.class.getResource("/style.css").toExternalForm()));
        return menuScene;
    }
}
