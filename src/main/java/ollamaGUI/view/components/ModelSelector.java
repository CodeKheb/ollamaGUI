package ollamaGUI.view.components;

import javafx.scene.control.ComboBox;

public class ModelSelector {

    private final ComboBox<String> selector = new ComboBox<>();

    public ModelSelector() {
        selector.getItems().addAll(
                "qwen2.5-coder:0.5b",
                "qwen2.5-coder:1.5b",
                "phi",
                "MeetSolanki/MeetAISmall",
                "dolphin-mistral",
                "llama3"
        );
        selector.setValue("qwen2.5-coder:0.5b");
    }

    public ComboBox<String> getNode() {
        return selector;
    }

    public String getValue() {
        return selector.getValue();
    }
}
