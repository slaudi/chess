package chess.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

/**
 * Class for Simplified Instantiating the Game-Options-Button
 */
public class OptionBox {

    static ButtonType buttonType;

    /**
     * @param title Title for the Options-Window
     * @param headerText Heading in Options-Menu
     * @param contentText Some Content in the Options-Menu
     * @param options Name of the Options-Button
     * @return Returns which Button is pressed
     */
    public static ButtonType display(String title, String headerText, String contentText, List<ButtonType> options) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        for (ButtonType opt : options){
            alert.getButtonTypes().add(opt);
        }

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }
}
