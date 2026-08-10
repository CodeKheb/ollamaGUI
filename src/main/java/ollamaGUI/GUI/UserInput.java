package ollamaGUI.GUI;

import javafx.scene.control.TextField;

public class UserInput {
    
    private TextField userInput = new TextField();

    public TextField InputBox() {
        userInput.setPromptText("What's on your mind today?");

        return userInput;
    }


    public String getText() {
        return userInput.getText();
    }

    public void clear() {
        userInput.clear();
    }
}
