package chess.gui;


import chess.game.Colour;
import chess.game.Square;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnglishGame extends BorderPane {

    public ChessBoardView chessBoardView;
    public GuiGame guiGame;
    String white;
    String black;
    String highlight;
    int fontSize = 17;


    /**
     *
     * @param guiGame
     */
    public EnglishGame(GuiGame guiGame, ChessBoardView chessBoardView) {

        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";

        this.guiGame = guiGame;
        this.chessBoardView = chessBoardView;
    }


    HBox generatePlayersMoveLabelBox(){
        Label label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn");
        if (guiGame.game.isCheckMate()) {
            AlertBox.display("Game Information","CheckMate",guiGame.game.currentPlayer.getColour().toString() + " has lost the Game!");
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + " lost the Game!");
        } else if (guiGame.game.isADraw() || guiGame.draw) {
            AlertBox.display("Game Information","Draw","The Game ended in a draw!");
            label = new Label("The Game ended in a draw!");
        } else if (guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            label = new Label(guiGame.game.currentPlayer.getColour().toString() + "s Turn -- " + guiGame.game.currentPlayer.getColour().toString() + " is in Check!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }

    GridPane generateGrid(){
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null) {
            // player selected first square
            if (guiGame.getSquareStart().getOccupiedBy() != null) {
                // selected first square is occupied
                if (guiGame.highlightPossibleMoves) {
                    // 'highlighting' is turned on
                    if (guiGame.game.currentPlayer.getColour() == guiGame.getSquareStart().getOccupiedBy().getColour() ) {
                        return chessBoardView.generateHighlightedButtonGrid();
                    } else {
                        // selected Piece is not players colour
                        guiGame.setSquareStart(null);
                        AlertBox.display("Piece problem", null, "Selected Piece is not your Colour!");
                    }
                } else if (guiGame.getSquareStart().getOccupiedBy().getColour() != guiGame.game.currentPlayer.getColour()) {
                    // 'highlighting' is turned off and selected Piece is not players colour
                    guiGame.setSquareStart(null);
                    AlertBox.display("Piece problem", null, "Selected Piece is not your Colour!");
                }
            } else {
                // selected first square is empty
                    guiGame.setSquareStart(null);
                    AlertBox.display("Piece Problem", null, "There is no Piece to move!");
            }
        }
        // no highlighted moves
        return chessBoardView.generateButtonGrid();
    }

    void noAllowedSquares(List<Square> allowedSquares) {
        if (allowedSquares.isEmpty()) {
            guiGame.setSquareStart(null);
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
