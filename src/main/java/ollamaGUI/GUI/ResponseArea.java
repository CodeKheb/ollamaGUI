package ollamaGUI.GUI;

import static java.lang.Double.MAX_VALUE;
import javafx.scene.control.TextArea;

public class ResponseArea {

    private static TextArea responseArea = new TextArea();

    public static TextArea get() {
        responseArea.setEditable(false);
        responseArea.setWrapText(true);
        responseArea.setMaxWidth(MAX_VALUE);
        responseArea.setMaxHeight(MAX_VALUE);
        return responseArea;
    }

    public static void clear(){
        responseArea.clear();
    }

    public static void setResponse(String response) {
        responseArea.appendText(response);
    }
}
