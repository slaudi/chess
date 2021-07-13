package chess.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.util.List;
import java.util.Optional;

/**
 * Class for Simplified Instantiating the Game-Options-Button
 */
public class OptionBox {

    /**
     * @param title Title for the Options-Window
     * @param headerText Heading in Options-Menu
     * @param contentText Some Content in the Options-Menu
     * @param options Name of the Options-Button
     * @return Returns which Button is pressed
     */
    public static ButtonType display(String title, String headerText, String contentText, List<ButtonType> options) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.initModality(Modality.APPLICATION_MODAL); //blocks user interaction with other windows
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        for (ButtonType opt : options){
            alert.getButtonTypes().add(opt);
        }
        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }
}
