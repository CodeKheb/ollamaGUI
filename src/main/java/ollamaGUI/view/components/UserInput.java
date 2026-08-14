package ollamaGUI.view.components;

import javafx.scene.control.TextField;

public class UserInput {

    private final TextField userInput = new TextField();

    public UserInput() {
        userInput.setPromptText("What's on your mind today?");
    }

    public TextField getNode() {
        return userInput;
    }

    public String getText() {
        return userInput.getText();
    }

    public void clear() {
        userInput.clear();
    }
}
