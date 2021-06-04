package chess.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class OptionBox {

    static ButtonType buttonType;

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
