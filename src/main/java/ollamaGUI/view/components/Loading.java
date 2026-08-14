package ollamaGUI.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

public class Loading {

    private final VBox loadingOverlay = new VBox(20);
    private final ProgressIndicator loading = new ProgressIndicator();
    private final Label loadingMessage = new Label();

    public Loading() {
        loadingOverlay.setAlignment(Pos.CENTER);
        loadingOverlay.getChildren().addAll(loading, loadingMessage);
        loadingOverlay.setVisible(false);

        // CSS class in /resources/style.css
        loadingOverlay.getStyleClass().add("loadingOverlay");
    }

    public VBox getNode() {
        return loadingOverlay;
    }

    public void show() {
        loadingOverlay.setVisible(true);
    }

    public void hide() {
        loadingOverlay.setVisible(false);
    }

    public void setText(String text) {
        loadingMessage.setText(text);
    }
}
