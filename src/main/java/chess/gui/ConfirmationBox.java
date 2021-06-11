package chess.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

/**
 * Class for Instantiating a PopUp with a Yes- and No-Button-Option
 */
public class ConfirmationBox {

    static boolean answer;

    /**
     * Function which displays the specified alert message.
     *
     * @param title Title of PopUp-Window
     * @param message Content of PopUp-Window
     * @return Returns the Answer of User(Yes or No) as a Boolean
     */
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
