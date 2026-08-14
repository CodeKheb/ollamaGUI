package ollamaGUI.view.components;

import static java.lang.Double.MAX_VALUE;

import javafx.scene.control.TextArea;

public class ResponseArea {

    private final TextArea responseArea = new TextArea();

    public ResponseArea() {
        responseArea.setEditable(false);
        responseArea.setWrapText(true);
        responseArea.setMaxWidth(MAX_VALUE);
        responseArea.setMaxHeight(MAX_VALUE);
    }

    public TextArea getNode() {
        return responseArea;
    }

    public void clear() {
        responseArea.clear();
    }

    public void append(String text) {
        responseArea.appendText(text);
    }
}
