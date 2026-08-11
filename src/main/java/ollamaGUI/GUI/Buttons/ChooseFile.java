package ollamaGUI.GUI.Buttons;

import java.io.File;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ollamaGUI.Read.FileManager;

public class ChooseFile {

    private static Button chooseFile = new Button("Choose File");
    private static FileChooser fileChooser = new FileChooser();
    private static Stage stage = new Stage();

    public Button get() {
        /*
         * chooseFile Button listener
         * setOnAction choose, if clicked -> Open fileChooser
         * if file is not null
         * Print the path (Error checking)
         * readFile the file
         * else (ERROR CHECKING)
         */
        chooseFile.setOnAction(choose -> {
            fileChooser.setTitle("Select A File");

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                FileManager.read(file);
            } else {
                System.out.println("ERROR");
            }
        });
        return chooseFile;
    }

    public static void setStage(Stage scene) {
        stage = scene;
    }
}
