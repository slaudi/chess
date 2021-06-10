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

public class GermanGame extends BorderPane {

    public ChessBoardView chessBoardView;
    public GuiGame guiGame;
    String white;
    String black;
    String highlight;
    int fontSize = 17;
    String colour;



    /**
     *
     * @param guiGame
     */
    public GermanGame(GuiGame guiGame, ChessBoardView chessBoardView) {

        this.white = "-fx-background-color: floralwhite";
        this.black = "-fx-background-color: slategray";
        this.highlight = "-fx-border-color: skyblue";

        this.guiGame = guiGame;
        this.chessBoardView = chessBoardView;

    }


    HBox generatePlayersMoveLabelBox(){
        Label label = new Label(getColour() + " ist am Zug");
        if (guiGame.game.isCheckMate()) {
            label = new Label(getColour() + " hat das Spiel verloren!");
        } else if (guiGame.game.isADraw() || guiGame.draw) {
            label = new Label("Das Spiel endet in einem Unentschieden!");
        } else if (guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            label = new Label(getColour() + " ist am Zug -- " + getColour() + " steht im Schach!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }

    private String getColour() {
        if (guiGame.game.currentPlayer.getColour() == Colour.BLACK) {
            return "SCHWARZ";
        } else {
            return "WEISS";
        }
    }


    GridPane generateGrid(){
        if (guiGame.getSquareStart() != null && guiGame.getSquareFinal() == null && guiGame.highlightPossibleMoves) {
            System.out.println(guiGame.getSquareStart().getLabel());
            if(guiGame.getSquareStart().getOccupiedBy() != null){
                System.out.println(guiGame.getSquareStart().getOccupiedBy());
                if(guiGame.game.currentPlayer.getColour() == Colour.WHITE && guiGame.getSquareStart().getOccupiedBy().getColour() == Colour.WHITE) {
                    return chessBoardView.generateHighlightedButtonGrid();
                } else if (guiGame.game.currentPlayer.getColour() == Colour.BLACK && guiGame.getSquareStart().getOccupiedBy().getColour() == Colour.BLACK) {
                    return chessBoardView.generateHighlightedButtonGrid();
                } else {
                    if (!guiGame.game.isADraw() || !guiGame.game.isCheckMate()) {
                        guiGame.setSquareStart(null);
                        AlertBox.display("Figuren-Problem", null, "Die ausgewählte Figur ist nicht deine Figur!");
                    }
                }
            } else {
                if (!guiGame.game.isADraw() || !guiGame.game.isCheckMate()) {
                    guiGame.setSquareStart(null);
                    AlertBox.display("Figuren-Problem", null, "Dort steht keine Figur zum Ziehen!");
                }
            }
        }
        // no highlighted moves
        return chessBoardView.generateButtonGrid();
    }


    void noAllowedSquares(List<Square> allowedSquares) {
        if (allowedSquares.isEmpty()) {
            guiGame.setSquareStart(null);
            AlertBox.display("Keine Züge möglich", null, "Diese Figur kann sich nicht bewegen. Versuch eine andere!!");
        }
    }



    void generateAnswer(int result) {
        if (result == 1) {
            AlertBox.display("Fehler",null,"'Auswahl ändern' ist ausgeschaltet. Du kannst keine andere Figur wählen!");
        } else if (result == 2){
            AlertBox.display("Fehler",null,"Zug nicht erlaubt: Dein König wäre im Schach!");
        } else if (result == 4){
            AlertBox.display("Fehler",null,"Zug nicht möglich!");
        } else if (result == 5){
            AlertBox.display("Spiel-Information","Schachmatt",getColour() + " hat das Spiel verloren!");
        } else if (result == 6){
            AlertBox.display("Spiel-Information","Unentschieden","Das Spiel endet in einem Unentschieden!");
        } else if (result == 7){
            AlertBox.display("Spiel-Fehler",null,"Etwas unerwartetes ist passiert!?");
        }
        if (result == 0 && guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            AlertBox.display("Schach-Hinweis",null, getColour() + " steht im Schach!");
        }
    }



    char promotionSelection() {
        ButtonType buttonTypeOne = new ButtonType("Turm");
        ButtonType buttonTypeTwo = new ButtonType("Springer");
        ButtonType buttonTypeThree = new ButtonType("Läufer");
        ButtonType buttonTypeFour = new ButtonType("Königin");

        List<ButtonType> options = new ArrayList<>();
        Collections.addAll(options,buttonTypeOne,buttonTypeTwo,buttonTypeThree,buttonTypeFour);

        ButtonType buttonType = OptionBox.display("Umwandlungs-Optionen",null,"Dein Bauer wird zu:",options);

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
