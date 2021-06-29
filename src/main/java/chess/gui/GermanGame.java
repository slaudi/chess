package chess.gui;


import chess.game.Colour;
import chess.game.Language;
import chess.game.Move;
import chess.game.Square;
import chess.savegame.SaveGame;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static chess.gui.Gui.*;

/**
 * The GermanGame class defines the output of the game when German is selected as language.
 */
public class GermanGame extends BorderPane {

    public GuiGame guiGame;
    public Gui gui;
    public Language language = Language.German;
    int fontSize = 17;


    /**
     * The Constructor for GermanGame.
     *
     * @param guiGame           The current guiGame.
     */
    public GermanGame(GuiGame guiGame, Gui gui) {
        this.guiGame = guiGame;
        this.gui = gui;
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


    MenuBar createGermanMenu(Stage primaryStage){
        Menu chessMenu = gameMenu(primaryStage);
        Menu optionsMenu = optionsMenu();
        Menu styleMenu = styleMenu(primaryStage);

        // Help-menu
        Menu helpMenu = new Menu("Hilfe");
        // userGuide-item
        MenuItem userGuide = new MenuItem("Bedienungsanleitung");
        userGuide.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));
        userGuide.setOnAction(event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("Bedienungsanleitung.pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                    AlertBox.display("PDF Viewer",null,"Es wurde kein PDF Viewer gefunden!");
                }
            }
        });
        helpMenu.getItems().add(userGuide);

        // Menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(chessMenu,optionsMenu,styleMenu,helpMenu);
        return menuBar;
    }


    private Menu gameMenu(Stage primaryStage) {
        Menu chessMenu = new Menu("Schach");
        // New Game-Menu item
        MenuItem newGame = new MenuItem("Neues Spiel");
        newGame.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Neues Spiel", "Möchtest du wirklich ein neues Spiel starten?",this.language);
            if (result) {
                guiGame = new GuiGame();
                guiGame.game.setLanguage(Language.German);
                gui.germanStart = new GermanStart(guiGame, gui);
                gui.englishStart = new EnglishStart(guiGame,gui);
                startScene = gui.startWindow(primaryStage, guiGame);
                chessScene = gui.chessWindow(primaryStage, guiGame);
                primaryStage.setScene(startScene);
                primaryStage.show();
            }
        });
        chessMenu.getItems().add(newGame);
        // Save Game-menu item
        MenuItem saveGame = new MenuItem("Spiel speichern");
        saveGame.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Spiel speichern", "Möchtest du diesen Spielstand speichern?",this.language);
            if (result) {
                SaveGame.save(guiGame.game);
            }
        });
        chessMenu.getItems().add(saveGame);
        // Load Game-menu item
        MenuItem loadGame = new MenuItem("Spiel laden");
        loadGame.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        loadGame.setOnAction(e -> gui.germanStart.loadGermanGame(primaryStage));
        chessMenu.getItems().add(loadGame);

        chessMenu.getItems().add(new SeparatorMenuItem());
        // Language-menu item
        MenuItem language = new MenuItem("Sprache");
        language.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+L"));
        language.setOnAction(event -> {
            gui.englishStart.chooseLanguage();
            chessScene = gui.chessWindow(primaryStage,guiGame);
            primaryStage.setScene(chessScene);
            primaryStage.show();
        });
        chessMenu.getItems().add(language);
        // Move History-Menu item
        MenuItem moveHistory = new MenuItem("Zug-Historie");
        moveHistory.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
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
            AlertBox.display("Zug-Historie", null, historyAsString.toString());
        });
        chessMenu.getItems().add(moveHistory);

        chessMenu.getItems().add(new SeparatorMenuItem());
        // Exit-menu item
        MenuItem exit = new MenuItem("Beenden");
        exit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        exit.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Spiel speichern", "Möchtest du diesen Spielstand speichern?",this.language);
            if (result) {
                SaveGame.save(guiGame.game);
            }
            boolean result2 = ConfirmationBox.display("Spiel beenden","Möchtest du das Spiel wirklich beenden?",this.language);
            if (result2) {
                System.exit(0);
            }
        });
        chessMenu.getItems().add(exit);

        return chessMenu;
    }


    private Menu optionsMenu(){
        Menu optionsMenu = new Menu("Optionen");
        // Rotation-check item
        CheckMenuItem rotation = new CheckMenuItem("Drehung des Spielbretts");
        rotation.setAccelerator(KeyCombination.keyCombination("Alt+R"));
        rotation.setOnAction(e -> guiGame.isRotatingBoard = rotation.isSelected());
        // Highlight-check item
        CheckMenuItem highlight = new CheckMenuItem("Züge hervorheben");
        highlight.setAccelerator(KeyCombination.keyCombination("Alt+H"));
        highlight.setOnAction(e -> guiGame.highlightPossibleMoves = highlight.isSelected());
        // Change selected-check item
        CheckMenuItem changeSelected = new CheckMenuItem("Gewählte Figur ändern");
        changeSelected.setAccelerator(KeyCombination.keyCombination("Alt+S"));
        changeSelected.setOnAction(e -> guiGame.allowedToChangeSelectedPiece = changeSelected.isSelected());
        optionsMenu.setOnAction(event -> changeSelected.setDisable(guiGame.getSquareStart() != null));
        // checkHint-check item
        CheckMenuItem checkHint = new CheckMenuItem("Hinweis: Schach");
        checkHint.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        checkHint.setOnAction(event -> guiGame.hintInCheck = checkHint.isSelected());
        // Set default for Options-items
        rotation.setSelected(true);
        highlight.setSelected(true);
        checkHint.setSelected(true);
        // add all items to Options-menu
        optionsMenu.getItems().addAll(rotation,highlight,changeSelected,checkHint);

        return optionsMenu;
    }


    void gridAnswer(int answer) {
        if (answer == 1){
            AlertBox.display("Figuren-Problem", null, "Die ausgewählte Figur ist nicht deine Figur!");
        } else {
            AlertBox.display("Figuren-Problem", null, "Dort steht keine Figur zum Ziehen!");
        }
    }


    private Menu styleMenu(Stage primaryStage) {
        Menu styleMenu = new Menu("Stil");
        ToggleGroup styleToggle = new ToggleGroup();
        // Classic-radio item
        RadioMenuItem classicStyle = new RadioMenuItem("Klassik");
        classicStyle.setToggleGroup(styleToggle);
        // BlackNWhite-item
        RadioMenuItem black_n_whiteStyle = new RadioMenuItem("Schwarz-Weiß");
        black_n_whiteStyle.setToggleGroup(styleToggle);
        // Christmas-item
        //RadioMenuItem christmasStyle = new RadioMenuItem("Christmas");
        //christmasStyle.setToggleGroup(styleToggle);
        // add ChangeListener to Toggle Group
        styleToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (classicStyle.isSelected()) {
                guiGame.white = "-fx-background-color: rgb(180,80,0)";
                guiGame.black = "-fx-background-color: rgb(255,228,196)";
                chessScene = gui.chessWindow(primaryStage,guiGame);
                primaryStage.setScene(chessScene);
                primaryStage.show();
            } else if (black_n_whiteStyle.isSelected()){
                guiGame.white = "-fx-background-color: white";
                guiGame.black = "-fx-background-color: black";
                chessScene = gui.chessWindow(primaryStage,guiGame);
                primaryStage.setScene(chessScene);
                primaryStage.show();
            }
        });
        // add all radio menu items to Style-menu
        styleMenu.getItems().addAll(classicStyle,black_n_whiteStyle);

        return styleMenu;
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
