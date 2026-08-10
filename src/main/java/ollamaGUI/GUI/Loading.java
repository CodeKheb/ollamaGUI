package ollamaGUI.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

public class Loading {

    private static final VBox loadingOverlay = new VBox(20);
    private static ProgressIndicator loading = new ProgressIndicator();
    private static Label loadingMessage = new Label();

    public static VBox getOverlay() {
        loadingOverlay.setAlignment(Pos.CENTER);
        loadingOverlay.getChildren().addAll(loading, loadingMessage);
        loadingOverlay.setVisible(false);

        // CSS getter in /resources/style.css
        loadingOverlay.getStyleClass().add("loadingOverlay");
        return loadingOverlay;
    }

    public static void hide(boolean hide) {
        loadingOverlay.setVisible(!hide);
    }

    public static boolean isHidden(){
        return loadingOverlay.isVisible();
    }

    public static void setText(String text) {
        loadingMessage.setText(text);
    }
}
