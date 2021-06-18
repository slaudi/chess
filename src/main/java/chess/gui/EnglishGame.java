package chess.gui;


import chess.game.Square;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The EnglishGame class defines the output of the game when English is selected as language.
 */
public class EnglishGame extends BorderPane {

    public ChessBoardView chessBoardView;
    public GuiGame guiGame;

    int fontSize = 17;


    /**
     * The Constructor for EnglishGame.
     *
     * @param guiGame           The current guiGame.
     * @param chessBoardView    The current chessBoardView.
     */
    public EnglishGame(GuiGame guiGame, ChessBoardView chessBoardView) {
        this.guiGame = guiGame;
        this.chessBoardView = chessBoardView;
    }


    HBox generatePlayersMoveLabelBox(){
        Label label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn");
        if (guiGame.game.isCheckMate()) {
            AlertBox.display("Game Information","CheckMate",guiGame.game.currentPlayer.getColour().toString() + " has lost the Game!");
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + " lost the Game!");
        } else if (guiGame.game.isADraw() || guiGame.game.isDrawn()) {
            AlertBox.display("Game Information","Draw","The Game ended in a draw!");
            label = new Label("The Game ended in a draw!");
        } else if (guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn -- " + guiGame.game.currentPlayer.getColour().toString() + " is in Check!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }


    void gridAnswer(int answer) {
        if (answer == 1){
            AlertBox.display("Piece problem", null, "Selected Piece is not your Colour!");
        } else {
            AlertBox.display("Piece Problem", null, "There is no Piece to move!");
        }
    }


    void noAllowedSquares(List<Square> allowedSquares) {
        if (allowedSquares.isEmpty()) {
            guiGame.setSquareStartNull();
            AlertBox.display("No Moves Possible", null, "This Piece cannot move. Try another!");
        }
    }


    void generateAnswer(int result) {
        if (result == 1){
            AlertBox.display("Piece Error", null, "'Change Selection' is turned off. You can't select another piece!");
        } else if (result == 2) {
            AlertBox.display("Movement Error", null, "Move not allowed: Your King would be in Check!");
        } else if (result == 4){
            AlertBox.display("Movement Error",null,"Move not allowed: Not possible!");
        } else if (result == 7){
            AlertBox.display("Game-Error",null,"Something unexpected happened!?");
        }
        if (result == 0 && guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            AlertBox.display("Check Hint",null, guiGame.game.currentPlayer.getColour().toString() + " is in Check!");
        }
    }


    char promotionSelection() {
        ButtonType buttonTypeOne = new ButtonType("Rook");
        ButtonType buttonTypeTwo = new ButtonType("Knight");
        ButtonType buttonTypeThree = new ButtonType("Bishop");
        ButtonType buttonTypeFour = new ButtonType("Queen");

        List<ButtonType> options = new ArrayList<>();
        Collections.addAll(options,buttonTypeOne,buttonTypeTwo,buttonTypeThree,buttonTypeFour);

        ButtonType buttonType = OptionBox.display("Promotion Options",null,"Your Pawn changes to:",options);

        if (buttonType == buttonTypeOne) {
            return 'R';
        } else if (buttonType == buttonTypeTwo) {
            return 'N';
        } else if (buttonType == buttonTypeThree) {
            return 'B';
        } else {
            return 'Q';
        }
    }


}
