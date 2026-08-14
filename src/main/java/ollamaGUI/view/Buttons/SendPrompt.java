package ollamaGUI.view.Buttons;

import javafx.scene.control.Button;

public class SendPrompt {

    private final Button button = new Button("Send");

    public SendPrompt(Runnable onSend) {
        // Enter anywhere in the scene triggers the default button, so no key handler needed
        button.setDefaultButton(true);
        button.setOnAction(e -> onSend.run());
    }

    public Button getNode() {
        return button;
    }
}
