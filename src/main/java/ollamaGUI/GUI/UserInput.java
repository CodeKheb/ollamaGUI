package ollamaGUI.GUI;

import javafx.scene.control.TextField;

public class UserInput {
    
    private static TextField userInput = new TextField();

    public static TextField get() {
        userInput.setPromptText("What's on your mind today?");
        return userInput;
    }

    public static String getText() {
        return userInput.getText();
    }

    public static void clear() {
        userInput.clear();
    }
}
