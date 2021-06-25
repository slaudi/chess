package chess.gui;


import chess.game.Colour;
import chess.game.Square;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The GermanGame class defines the output of the game when German is selected as language.
 */
public class GermanGame extends BorderPane {

    public ChessBoardView chessBoardView;
    public GuiGame guiGame;
    int fontSize = 17;


    /**
     * The Constructor for GermanGame.
     *
     * @param guiGame           The current guiGame.
     * @param chessBoardView    The current chessBoardView.
     */
    public GermanGame(GuiGame guiGame, ChessBoardView chessBoardView) {
        this.guiGame = guiGame;
        this.chessBoardView = chessBoardView;
    }


    HBox generatePlayersMoveLabelBox(){
        Label label = new Label(getColourName() + " ist am Zug");
        if (guiGame.game.isCheckMate()) {
            AlertBox.display("Spiel-Information","Schachmatt", getColourName() + " hat das Spiel verloren!");
            label = new Label(getColourName() + " hat das Spiel verloren!");
        } else if (guiGame.game.isADraw() || guiGame.game.isDrawn()) {
            AlertBox.display("Spiel-Information","Unentschieden","Das Spiel endet in einem Unentschieden!");
            label = new Label("Das Spiel endet in einem Unentschieden!");
        } else if (guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            label = new Label(getColourName() + " ist am Zug -- " + getColourName() + " steht im Schach!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }


    private Colour getColourName() {
        if (guiGame.game.currentPlayer.getColour() == Colour.BLACK) {
            return Colour.SCHWARZ;
        } else {
            return Colour.WEISS;
        }
    }


    MenuBar createGermanMenu(){
        // File menu
        Menu gameMenu = new Menu("Spiel");
        Menu designMenu = new Menu("Design");

        // Menu items
        gameMenu.getItems().add(new MenuItem("Settings"));

        // Menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(gameMenu,designMenu);

        return menuBar;
    }


    void gridAnswer(int answer) {
        if (answer == 1){
            AlertBox.display("Figuren-Problem", null, "Die ausgewählte Figur ist nicht deine Figur!");
        } else {
            AlertBox.display("Figuren-Problem", null, "Dort steht keine Figur zum Ziehen!");
        }
    }


    void noAllowedSquares(List<Square> allowedSquares) {
        if (allowedSquares.isEmpty()) {
            guiGame.setSquareStartNull();
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
        } else if (result == 7){
            AlertBox.display("Spiel-Fehler",null,"Etwas unerwartetes ist passiert!?");
        }
        if (result == 0 && guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            AlertBox.display("Schach-Hinweis",null, getColourName() + " steht im Schach!");
        }
    }


    char promotionSelection() {
        ButtonType buttonTypeOne = new ButtonType("Turm");
        ButtonType buttonTypeTwo = new ButtonType("Springer");
        ButtonType buttonTypeThree = new ButtonType("Läufer");
        ButtonType buttonTypeFour = new ButtonType("Königin");

        List<ButtonType> options = new ArrayList<>();
        Collections.addAll(options,buttonTypeOne,buttonTypeTwo,buttonTypeThree,buttonTypeFour);

        ButtonType buttonType = OptionBox.display("Umwandlungsoptionen",null,"Dein Bauer wird zu:",options);

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
