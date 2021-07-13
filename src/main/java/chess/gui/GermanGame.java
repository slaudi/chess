package chess.gui;


import chess.game.Colour;
import chess.game.Language;
import chess.game.Move;
import chess.game.Square;
import chess.savegame.SaveGame;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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

    private GuiGame guiGame;
    private final Gui gui;
    private final Language language = Language.German;
    CheckMenuItem changeSelected;


    /**
     * The Constructor for GermanGame.
     *
     * @param guiGame           The current guiGame.
     * @param gui gui-parameters
     */
    public GermanGame(GuiGame guiGame, Gui gui) {
        this.guiGame = guiGame;
        this.gui = gui;
    }


    void startButtonsGerman(List<Button> startButtons) {
        startButtons.get(0).setText("Willkommen zu einer neuen Partie Schach!");
        startButtons.get(1).setText("Starte Spiel");
        startButtons.get(2).setText("Netzwerk-Spiel");
        startButtons.get(3).setText("Lade Spiel");
        startButtons.get(4).setText("Sprache");
    }


    void chooseEnemyGerman() {
        ButtonType human = new ButtonType("Mensch");
        ButtonType computer = new ButtonType("KI");

        List<ButtonType> enemy = new ArrayList<>();
        Collections.addAll(enemy,human,computer);
        ButtonType enemyResult = OptionBox.display("Gegner-Auswahl",null,"Wähle deinen Gegner:",enemy);
        if (enemyResult == computer){
            guiGame.game.setArtificialEnemy(true);
            guiGame.isRotatingBoard = false;
        }

        if(guiGame.game.isArtificialEnemy()) {
            ButtonType white = new ButtonType("Weiß");
            ButtonType black = new ButtonType("Schwarz");

            List<ButtonType> colour = new ArrayList<>();
            Collections.addAll(colour,white,black);

            ButtonType colourResult = OptionBox.display("Farbauswahl",null,"Wähle deine Farbe:", colour);
            if (colourResult == white) {
                guiGame.game.setUserColour(Colour.WHITE);
            } else {
                guiGame.game.setUserColour(Colour.BLACK);
                guiGame.turnAI = true;
            }
        }
    }


    void chooseLanguage() {
        ButtonType german = new ButtonType("Deutsch");
        ButtonType english = new ButtonType("Englisch");

        List<ButtonType> language = new ArrayList<>();
        Collections.addAll(language,german,english);
        ButtonType result;
        result = OptionBox.display("Sprachauswahl", null, "Wähle eine Sprache", language);
        if (result == german) {
            guiGame.game.setLanguage(Language.German);
        } else {
            guiGame.game.setLanguage(Language.English);
        }
    }


    void loadGermanGame(Stage primaryStage){
        boolean result = ConfirmationBox.display("Spiel laden","Möchtest du ein gespeichertes Spiel laden?", this.language);
        if (result) {
            File f = new File("src/main/resources/saves");
            String[] fileArray = f.list();
            assert fileArray != null;
            if(fileArray.length != 0) {
                List<String> choices = new ArrayList<>();
                Collections.addAll(choices, fileArray);
                ChoiceDialog<String> dialog = new ChoiceDialog<>(fileArray[0], choices);
                dialog.setTitle("Wähle Spielstand");
                dialog.setHeaderText(null);
                dialog.setContentText("Wähle ein gespeichertes Spiel aus:");

                gui.loadGame(dialog,primaryStage);
                }
            }
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
        } else if (guiGame.game.currentPlayer.isLoser()) {
            AlertBox.display("Spiel-Information","Aufgabe",getColourName() + " hat aufgegeben.");
            label = new Label(getColourName() + " hat aufgegeben.");
        }
        label.setFont(new Font(17));
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
                gui.germanGame = new GermanGame(guiGame, gui);
                gui.englishGame = new EnglishGame(guiGame,gui);
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
        loadGame.setOnAction(e -> loadGermanGame(primaryStage));
        chessMenu.getItems().add(loadGame);
        // Giving up
        MenuItem giveUp = new MenuItem("Aufgeben");
        giveUp.setAccelerator(KeyCombination.keyCombination("Ctrl+G"));
        giveUp.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Aufgeben","Möchtest du wirklich aufgeben?",Language.German);
            if (result) {
                guiGame.game.currentPlayer.setLoser(true);
                generatePlayersMoveLabelBox();
            }
        });
        chessMenu.getItems().add(giveUp);
        chessMenu.getItems().add(new SeparatorMenuItem());
        // Language-menu
        Menu language = new Menu("Sprache");
        RadioMenuItem german = new RadioMenuItem("Deutsch");
        RadioMenuItem english = new RadioMenuItem("Englisch");
        english.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+E"));
        english.setOnAction(event -> {
                    guiGame.game.setLanguage(Language.English);
                    chessScene = gui.chessWindow(primaryStage, guiGame);
                    primaryStage.setScene(chessScene);
                    primaryStage.show();
        });
        german.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+G"));
        german.setSelected(true);
        language.getItems().addAll(german,english);
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
            if (!(guiGame.game.isCheckMate() || guiGame.game.isDrawn()) && !guiGame.game.moveHistory.isEmpty()) {
                // only asked when the has already started with a move made or the game is not finished yet
                boolean result = ConfirmationBox.display("Spiel speichern", "Möchtest du diesen Spielstand speichern?", this.language);
                if (result) {
                    SaveGame.save(guiGame.game);
                }
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
        Menu optionsMenu = new Menu("Einstellungen");
        // Rotation-check item
        CheckMenuItem rotation = new CheckMenuItem("Drehung des Spielbretts");
        rotation.setAccelerator(KeyCombination.keyCombination("Alt+R"));
        rotation.setOnAction(e -> guiGame.isRotatingBoard = rotation.isSelected());
        // Highlight-check item
        CheckMenuItem highlight = new CheckMenuItem("Züge hervorheben");
        highlight.setAccelerator(KeyCombination.keyCombination("Alt+H"));
        highlight.setOnAction(e -> guiGame.highlightPossibleMoves = highlight.isSelected());
        // Change selected-check item
        changeSelected = new CheckMenuItem("Gewählte Figur ändern");
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

        // add all items to Settings-menu
        optionsMenu.getItems().addAll(rotation,highlight,changeSelected,checkHint);

        return optionsMenu;
    }


    private Menu styleMenu(Stage primaryStage) {
        Menu styleMenu = new Menu("Stil");
        ToggleGroup styleToggle = new ToggleGroup();
        // Classic-radio item
        RadioMenuItem classicStyle = new RadioMenuItem("Klassik");
        classicStyle.setToggleGroup(styleToggle);
        // BlackNWhite-radio item
        RadioMenuItem black_n_whiteStyle = new RadioMenuItem("Schwarz-Weiß");
        black_n_whiteStyle.setToggleGroup(styleToggle);

        // add ChangeListener to Toggle Group
        styleToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            //styleToggle.getSelectedToggle().setSelected(true);
            if (classicStyle.isSelected()) {
                gui.background = Color.FLORALWHITE;
                guiGame.white = "-fx-background-color: rgb(180,80,0)";
                guiGame.black = "-fx-background-color: rgb(255,228,196)";
                chessScene = gui.chessWindow(primaryStage,guiGame);
                primaryStage.setScene(chessScene);
                primaryStage.show();
            } else if (black_n_whiteStyle.isSelected()){
                gui.background = Color.SLATEGRAY;
                guiGame.white = "-fx-background-color: snow";
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
            AlertBox.display("Keine Züge möglich", null, "Diese Figur kann sich nicht bewegen. Versuch eine andere!");
        }
    }


    void generateAnswer(int result) {
        if (result == 1) {
            AlertBox.display("Fehler",null,"'Auswahl ändern' ist ausgeschaltet. Du kannst keine andere Figur wählen!");
        } else if (result == 2){
            AlertBox.display("Fehler",null,"Zug nicht erlaubt: Dein König wäre im Schach!");
        } else if (result == 4){
            AlertBox.display("Fehler",null,"Zug nicht möglich!");
        } else if (result == 8){
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
