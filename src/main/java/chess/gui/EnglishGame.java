package chess.gui;


import chess.game.Colour;
import chess.game.Language;
import chess.game.Move;
import chess.game.Square;
import chess.savegame.SaveGame;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static chess.gui.Gui.*;

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


    void startButtonsEnglish(List<Button> startButtons) {
        startButtons.get(0).setText("Welcome to a new Game of Chess!");
        startButtons.get(1).setText("Start Game");
        startButtons.get(2).setText("Network Game");
        startButtons.get(3).setText("Load Game");
        startButtons.get(4).setText("Language");
    }

    void chooseEnemyEnglish() {
        ButtonType human = new ButtonType("Person");
        ButtonType computer = new ButtonType("AI");

        List<ButtonType> enemy = new ArrayList<>();
        Collections.addAll(enemy,human,computer);

        ButtonType enemyResult = OptionBox.display("Enemy Selection",null,"Choose your Enemy",enemy);
        if (enemyResult == computer){
            guiGame.game.setArtificialEnemy(true);
            guiGame.isRotatingBoard = false;
        }

        if(guiGame.game.isArtificialEnemy()) {
            ButtonType white = new ButtonType("White");
            ButtonType black = new ButtonType("Black");

            List<ButtonType> colour = new ArrayList<>();
            Collections.addAll(colour,white,black);

            ButtonType colourResult = OptionBox.display("Colour Selection",null,"Choose your Colour", colour);
            if (colourResult == white) {
                guiGame.game.setUserColour(Colour.WHITE);
            } else {
                guiGame.game.setUserColour(Colour.BLACK);
                guiGame.turnAI = true;
            }
        }
    }

    void chooseLanguage() {
        ButtonType german = new ButtonType("German");
        ButtonType english = new ButtonType("English");

        List<ButtonType> language = new ArrayList<>();
        Collections.addAll(language,german,english);
        ButtonType result;
        result = OptionBox.display("Language Selection", null, "Choose Language", language);
        if (result == german) {
            guiGame.game.setLanguage(Language.German);
        } else {
            guiGame.game.setLanguage(Language.English);
        }
    }

    void loadEnglishGame(Stage primaryStage){
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

                if (dialog.isShowing()) {
                    chessScene = gui.chessWindow(primaryStage, guiGame);
                    primaryStage.setScene(chessScene);
                } else {
                    startScene = gui.startWindow(primaryStage,guiGame);
                    primaryStage.setScene(startScene);
                }
            }

        }
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
            label = new Label(label + " -- " + guiGame.game.currentPlayer.getColour() + " is in Check!");
        }
        label.setFont(new Font(fontSize));
        return new HBox(label);
    }


    MenuBar createEnglishMenu(Stage primaryStage){
        Menu chessMenu = gameMenu(primaryStage);
        Menu optionsMenu = optionsMenu();
        Menu styleMenu = styleMenu(primaryStage);

        // Help-menu
        Menu helpMenu = new Menu("Help");
        // userGuide-item
        MenuItem userGuide = new MenuItem("User Guide");
        userGuide.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));
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


    private Menu gameMenu(Stage primaryStage){
        Menu chessMenu = new Menu("Chess");
        // New Game-Menu item
        MenuItem newGame = new MenuItem("New Game");
        newGame.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("New Game", "Do you really want to start a new Game?",this.language);
            if (result) {
                guiGame = new GuiGame();
                guiGame.game.setLanguage(Language.English);
                gui.englishGame = new EnglishGame(guiGame,gui);
                gui.germanGame = new GermanGame(guiGame, gui);
                startScene = gui.startWindow(primaryStage, guiGame);
                chessScene = gui.chessWindow(primaryStage, guiGame);
                primaryStage.setScene(startScene);
                primaryStage.show();
            }
        });
        chessMenu.getItems().add(newGame);
        // Save Game-menu item
        MenuItem saveGame = new MenuItem("Save Game");
        saveGame.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Save Game", "Do you want to save this Game?",this.language);
            if (result) {
                SaveGame.save(guiGame.game);
            }
        });
        chessMenu.getItems().add(saveGame);
        // Load Game-menu item
        MenuItem loadGame = new MenuItem("Load Game");
        loadGame.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        loadGame.setOnAction(e -> loadEnglishGame(primaryStage));
        chessMenu.getItems().add(loadGame);

        chessMenu.getItems().add(new SeparatorMenuItem());
        // Language-menu item
        Menu language = new Menu("Language");
        RadioMenuItem german = new RadioMenuItem("German");
        RadioMenuItem english = new RadioMenuItem("English");
        german.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+G"));
        german.setOnAction(event -> {
            guiGame.game.setLanguage(Language.German);
            chessScene = gui.chessWindow(primaryStage,guiGame);
            primaryStage.setScene(chessScene);
            primaryStage.show();
        });
        english.setSelected(true);
        english.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+E"));
        language.getItems().addAll(german,english);
        chessMenu.getItems().add(language);
        // Move History-menu item
        MenuItem moveHistory = new MenuItem("Move History");
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
            AlertBox.display("Move History", null, historyAsString.toString());
        });
        chessMenu.getItems().add(moveHistory);

        chessMenu.getItems().add(new SeparatorMenuItem());
        // Exit-menu item
        MenuItem exit = new MenuItem("Exit");
        exit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        exit.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Save Game", "Do you want to save this Game?",this.language);
            if (result) {
                SaveGame.save(guiGame.game);
            }
            boolean result2 = ConfirmationBox.display("Exit Game","Do you really want to exit the game?",this.language);
            if (result2) {
                System.exit(0);
            }
        });
        chessMenu.getItems().add(exit);

        return chessMenu;
    }


    private Menu optionsMenu(){
        Menu optionsMenu = new Menu("Options");
        // Rotation-check item
        CheckMenuItem rotation = new CheckMenuItem("Board Rotation");
        rotation.setAccelerator(KeyCombination.keyCombination("Alt+R"));
        rotation.setOnAction(event -> guiGame.isRotatingBoard = rotation.isSelected());
        // Highlight-check item
        CheckMenuItem highlight = new CheckMenuItem("Highlight Moves");
        highlight.setAccelerator(KeyCombination.keyCombination("Alt+H"));
        highlight.setOnAction(event -> guiGame.highlightPossibleMoves = highlight.isSelected());
        // Change selected-check item
        CheckMenuItem changeSelected = new CheckMenuItem("Change Selected Piece");
        changeSelected.setAccelerator(KeyCombination.keyCombination("Alt+S"));
        changeSelected.setOnAction(event -> guiGame.allowedToChangeSelectedPiece = changeSelected.isSelected());
        optionsMenu.setOnAction(event -> changeSelected.setDisable(guiGame.getSquareStart() != null));
        // checkHint-check item
        CheckMenuItem checkHint = new CheckMenuItem("Hint: In Check");
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


    private Menu styleMenu(Stage primaryStage){
        // Style RadioMenu
        Menu styleMenu = new Menu("Themes");
        ToggleGroup styleToggle = new ToggleGroup();
        // Classic-radio item
        RadioMenuItem classicStyle = new RadioMenuItem("Classic");
        classicStyle.setToggleGroup(styleToggle);
        // BlackNWhite-item
        RadioMenuItem black_n_whiteStyle = new RadioMenuItem("Black'n'White");
        black_n_whiteStyle.setToggleGroup(styleToggle);
        // Christmas-item
        //RadioMenuItem christmasStyle = new RadioMenuItem("Christmas");
        //christmasStyle.setToggleGroup(styleToggle);
        /*classicStyle.setSelected(true);
        black_n_whiteStyle.setSelected(false);
        if (black_n_whiteStyle.isSelected()){
            classicStyle.setSelected(false);
            black_n_whiteStyle.setSelected(true);
        }*/
        // add ChangeListener to Toggle Group
        styleToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
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
            } /*else if (christmasStyle.isSelected()){
                guiGame.whiteImage = new Image("chess/gui/snow.png");
                guiGame.whiteBG = new BackgroundImage(guiGame.whiteImage, null,null,null,null);
                guiGame.blackImage = new Image("chess/gui/xmas_tree");
                guiGame.blackBG = new BackgroundImage(guiGame.blackImage, null,null,null,null);
            }*/
        });

        // add all radio menu items to Style-menu
        //styleToggle.getSelectedToggle().setSelected(true);
        styleMenu.getItems().addAll(classicStyle,black_n_whiteStyle);

        return styleMenu;
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
