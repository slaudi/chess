package chess.gui;

import chess.game.Language;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

/**
 * Class for Instantiating a PopUp with a Yes- and No-Button-Option
 */
public class ConfirmationBox {

    private static boolean answer;

    /**
     * Function which displays the specified alert message.
     *
     * @param title Title of PopUp-Window
     * @param message Content of PopUp-Window
     * @param language language to be displayed
     * @return Returns the Answer of User(Yes or No) as a Boolean
     */
    public static boolean display(String title, String message, Language language){
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.initModality(Modality.APPLICATION_MODAL); //blocks user interaction with other windows
        alert.setTitle(title);
        alert.setContentText(message);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        if (language == Language.German) {
            yesButton = new ButtonType("Ja");
            noButton = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
        }
        alert.getButtonTypes().addAll(yesButton,noButton);

        Optional<ButtonType> result = alert.showAndWait();
        ButtonType finalYesButton = yesButton;
        result.ifPresent(buttonType -> answer = buttonType == finalYesButton); // answer = true if 'Yes'-Button is clicked

        return answer;
    }
}
