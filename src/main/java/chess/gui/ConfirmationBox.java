package chess.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class ConfirmationBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.initModality(Modality.APPLICATION_MODAL); //blocks user interaction with other windows
        alert.setTitle(title);
        alert.setContentText(message);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().addAll(yesButton,noButton);

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> answer = buttonType == yesButton); // answer = true if 'Yes'-Button is clicked

        return answer;
    }
}
