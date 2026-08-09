package ollamaGUI.GUI;

import javafx.scene.control.ComboBox;

public class ModelSelector {

    private static ComboBox<String> selector;

    public ModelSelector() {

    }

    public ComboBox<String> SelectModel(){
        /* ComboBox<String> modelSelector add models in a ComboBox
        set the default value to qwen2.5-coder:0.5b
        the static ComboBox will then get called in LocalHost,
        and then it changes the String "model" value
         */
        selector = new ComboBox<>();
        selector.getItems().addAll(
                "qwen2.5-coder:0.5b",
                "qwen2.5-coder:1.5b",
                "phi",
                "MeetSolanki/MeetAISmall",
                "dolphin-mistral",
                "llama3"
        );
        selector.setValue("qwen2.5-coder:0.5b");

        return selector;
    }

    public String getValue() {
        return selector.getValue();
    }
}
