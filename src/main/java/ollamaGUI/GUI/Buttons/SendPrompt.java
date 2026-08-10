package ollamaGUI.GUI.Buttons;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import ollamaGUI.Read.Message;

public class SendPrompt {
    private Button sendPrompt = new Button();

    public Button get() {
        sendPrompt.setText("Send");
        sendPrompt.setDefaultButton(true);

        /* sendPrompt Button Listeners
        setOnAction click, if clicked call method
        setOnKeyPressed enter, if entered, call method
         */
        sendPrompt.setOnAction(click -> {
            Message.get();
            System.out.println("Send Clicked!");
        });

        sendPrompt.setOnKeyPressed(enter -> {
            if (enter.getCode() == KeyCode.ENTER) {
                System.out.println("Send Clicked!");
                Message.get();
            }
        });
        return sendPrompt;
    }

}
