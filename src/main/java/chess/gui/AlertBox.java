package chess.gui;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class AlertBox {

    public static void display(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.initModality(Modality.APPLICATION_MODAL); //blocks user interaction with other windows
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

}
