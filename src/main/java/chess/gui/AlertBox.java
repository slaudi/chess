package chess.gui;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 * This class creates a customised alert box.
 */
public class AlertBox {

    /**
     * Function which displays an alert window with the specified messages.
     *
     * @param title         The title of the alert box.
     * @param headerText    The header text of the alert box.
     * @param contentText   The content text of the alert box showcasing the specified message.
     */
    public static void display(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.initModality(Modality.APPLICATION_MODAL); //blocks user interaction with other windows
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

}
