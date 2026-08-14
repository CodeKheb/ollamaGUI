package ollamaGUI.view.Buttons;

import java.io.File;
import java.util.function.Consumer;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ChooseFile {

    private final Button button = new Button("Choose File");

    public ChooseFile(Consumer<File> onFileChosen, Window owner) {
        button.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select A File");

            File file = fileChooser.showOpenDialog(owner);
            if (file != null) {
                onFileChosen.accept(file);
            }
        });
    }

    public Button getNode() {
        return button;
    }
}
