package chess.gui;


import chess.game.Language;
import chess.game.Move;
import chess.game.Square;
import chess.savegame.SaveGame;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static chess.gui.Gui.chessScene;

/**
 * The EnglishGame class defines the output of the game when English is selected as language.
 */
public class EnglishGame extends BorderPane {

    public GuiGame guiGame;
    public Gui gui;
    public Language language = Language.English;
    int fontSize = 17;

    /**
     * The Constructor for EnglishGame.
     *
     * @param guiGame           The current guiGame.
     */
    public EnglishGame(GuiGame guiGame, Gui gui) {
        this.guiGame = guiGame;
        this.gui = gui;
    }


    HBox generatePlayersMoveLabelBox(){
        Label label = new Label(guiGame.game.currentPlayer.getColour() + "s Turn");
        if (guiGame.game.isCheckMate()) {
            AlertBox.display("Game Information","CheckMate",guiGame.game.currentPlayer.getColour() + " has lost the Game!");
            label = new Label(guiGame.game.currentPlayer.getColour() + " lost the Game!");
        } else if (guiGame.game.isADraw() || guiGame.game.isDrawn()) {
            AlertBox.display("Game Information","Draw","The Game ended in a draw!");
            label = new Label("The Game ended in a draw!");
        } else if (guiGame.hintInCheck && guiGame.game.currentPlayer.isInCheck()){
            label = new Label(guiGame.game.currentPlayer.getColour() + "s Turn -- " + guiGame.game.currentPlayer.getColour() + " is in Check!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }


    MenuBar createEnglishMenu(Stage primaryStage){
        // Game menu
        Menu chessMenu = new Menu("Chess");
        // New Game-Menu item
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("New Game", "Do you really want to start a new Game?",this.language);
            if (result) {
                gui.start(primaryStage);
            }
        });
        chessMenu.getItems().add(newGame);
        // Save Game-menu item
        MenuItem saveGame = new MenuItem("Save Game");
        saveGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Save Game", "Do you want to save this Game?",this.language);
            if (result) {
                SaveGame.save(guiGame.game);
            }
        });
        chessMenu.getItems().add(saveGame);
        // Load Game-menu item
        MenuItem loadGame = new MenuItem("Load Game");
        loadGame.setOnAction(e -> {
            boolean result = ConfirmationBox.display("Load Game","Do you want to load a saved Game?", this.language);
            if (result) {
                File f = new File("src/main/resources/saves");
                String[] fileArray = f.list();
                assert fileArray != null;
                if(fileArray.length != 0) {
                    List<String> choices = new ArrayList<>();
                    Collections.addAll(choices, fileArray);
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(fileArray[0], choices);
                    dialog.setTitle("Choose Game");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Choose a saved Game:");

                    gui.loadGame(dialog);
                }
                chessScene = gui.chessWindow(primaryStage);
                primaryStage.setScene(chessScene);
            }
        });
        chessMenu.getItems().add(loadGame);
        chessMenu.getItems().add(new SeparatorMenuItem());
        // Language-menu item
        MenuItem language = new MenuItem("Language");
        language.setOnAction(event -> {
            gui.englishStart.chooseLanguage();
            chessScene = gui.chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
            primaryStage.show();
        });
        chessMenu.getItems().add(language);
        // Move History-Menu item
        MenuItem moveHistory = new MenuItem("Move History");
        moveHistory.setOnAction(event -> {
            List<Move> history = guiGame.game.moveHistory;
            StringBuilder historyAsString = new StringBuilder();
            if(!history.isEmpty()){
                for (Move move : history) {
                    historyAsString.append(move.getStartSquare().getLabel().toString()).append("-").append(move.getFinalSquare().getLabel().toString()).append("\n");
                }
            } else {
                historyAsString = new StringBuilder(" ");
            }
            AlertBox.display("Move History", null, historyAsString.toString());
        });
        chessMenu.getItems().add(moveHistory);
        chessMenu.getItems().add(new SeparatorMenuItem());
        // Exit-menu item
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> System.exit(0));
        chessMenu.getItems().add(exit);

        // Options menu
        Menu optionsMenu = new Menu("Options");
        // Rotation-check item
        CheckMenuItem rotation = new CheckMenuItem("Board Rotation");
        rotation.setOnAction(event -> guiGame.isRotatingBoard = rotation.isSelected());
        // Highlight-check item
        CheckMenuItem highlight = new CheckMenuItem("Highlight Moves");
        highlight.setOnAction(event -> guiGame.highlightPossibleMoves = highlight.isSelected());
        // Change selected-check item
        CheckMenuItem changeSelected = new CheckMenuItem("Change Selected Piece");
        changeSelected.setOnAction(event -> guiGame.allowedToChangeSelectedPiece = changeSelected.isSelected());
        optionsMenu.setOnAction(event -> changeSelected.setDisable(guiGame.getSquareStart() != null));
        // checkHint-check item
        CheckMenuItem checkHint = new CheckMenuItem("Hint: In Check");
        checkHint.setOnAction(event -> guiGame.hintInCheck = checkHint.isSelected());
        // Set default for Options-items
        rotation.setSelected(true);
        highlight.setSelected(true);
        checkHint.setSelected(true);
        // add all items to Options-menu
        optionsMenu.getItems().addAll(rotation,highlight,changeSelected,checkHint);

        // Style Menu
        Menu styleMenu = new Menu("Style");
        // Classic-item
        MenuItem classicStyle = new MenuItem("Classic");
        classicStyle.setOnAction(event -> {
            guiGame.white = "-fx-background-color: rgb(180,80,0)";
            guiGame.black = "-fx-background-color: rgb(255,228,196)";
            chessScene = gui.chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
            primaryStage.show();
        });
        // BlackNWhite-item
        MenuItem black_n_whiteStyle = new MenuItem("Black'n'White");
        black_n_whiteStyle.setOnAction(event -> {
            guiGame.white = "-fx-background-color: white";
            guiGame.black = "-fx-background-color: black";
            chessScene = gui.chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
            primaryStage.show();
        });
        // Christmas-item
        MenuItem christmasStyle = new MenuItem("Christmas");
        // add all check items to Style-menu
        styleMenu.getItems().addAll(classicStyle,black_n_whiteStyle,christmasStyle);

        // Help-menu
        Menu helpMenu = new Menu("Help");
        // userGuide-item
        MenuItem userGuide = new MenuItem("User Guide");
        userGuide.setOnAction(event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("Bedienungsanleitung.pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                    AlertBox.display("PDF Viewer",null,"No PDF Viewer detected!");
                }
            }
        });
        helpMenu.getItems().add(userGuide);

        // Menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(chessMenu,optionsMenu,styleMenu,helpMenu);
        return menuBar;
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
            AlertBox.display("Check Hint",null, guiGame.game.currentPlayer.getColour() + " is in Check!");
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
