package chess.gui;

import chess.game.Colour;
import chess.game.Language;
import chess.game.Move;
import chess.savegame.SaveGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static chess.gui.Gui.chessScene;
import static chess.gui.Gui.startScene;

public class EnglishStart {

    public GuiGame guiGame;
    public Gui gui;
    public Language language;
    private final String chess = "Chess!";

    public EnglishStart(GuiGame guiGame, Gui gui){
        this.guiGame = guiGame;
        this.gui = gui;
        this.language = Language.English;
    }

    void startWindowEnglish(Stage primaryStage, BorderPane pane) {
        primaryStage.setTitle(chess);

        Button welcome = new Button("Welcome to a new Game of Chess!");
        welcome.getStyleClass().add("startLabel");
        BorderPane.setAlignment(welcome, Pos.TOP_CENTER);
        BorderPane.setMargin(welcome,new Insets(50,0,60,0));
        pane.setTop(welcome);

        // Define Start Game-Button
        Button startLocalGame = new Button("Start Game");
        startLocalGame.setDefaultButton(true);
        startLocalGame.getStyleClass().add("startButtons");
        startLocalGame.setOnAction(e -> {
            chooseEnemyEnglish();
            chessScene = gui.chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
        });

        // Define Network Game-Button
        Button startNetworkGame = new Button("Network Game");
        startNetworkGame.setOnAction(e -> gui.startNetworkGame(primaryStage));
        startNetworkGame.getStyleClass().add("startButtons");

        // Define Load Game-Button
        Button loadGame = new Button("Load Game");
        loadGame.getStyleClass().add("startButtons");
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

        // Define Language-Button
        Button language = new Button("Language");
        language.getStyleClass().add("startButtons");
        language.setOnAction(e -> {
            chooseLanguage();
            startScene = gui.startWindow(primaryStage);
            primaryStage.setScene(startScene);
            primaryStage.show();
        });
        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll(startLocalGame, startNetworkGame, loadGame, language);
        layout1.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(layout1);
    }

    private void chooseEnemyEnglish() {
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

    private void chooseLanguage() {
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


    VBox generateRightMarginColumnEnglish(Stage primaryStage){
        //Define New Game-Button
        Button btnNewGame = new Button("New Game");
        btnNewGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("New Game", "Do you really want to start a new Game?",this.language);
            if (result) {
                guiGame = new GuiGame();
                startScene = gui.startWindow(primaryStage);
                chessScene = gui.chessWindow(primaryStage);

                primaryStage.setScene(startScene);
                primaryStage.setTitle(chess);
                primaryStage.show();
            }
        });

        //Define Save Game-Button
        Button btnSaveGame = new Button("Save Game");
        btnSaveGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Save Game", "Do you want to save this Game?",this.language);
            if (result) {
                SaveGame.save(guiGame.game);
            }
        });

        // Define Option-Button
        Button btnOptions = new Button("Options");
        btnOptions.setOnAction(event -> {
            List<ButtonType> options = new ArrayList<>();
            ButtonType rotation = new ButtonType("Rotation");
            ButtonType highlight = new ButtonType("Highlight");
            ButtonType changeSelection = new ButtonType("Change Selection");
            ButtonType check = new ButtonType("Check");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Collections.addAll(options,rotation,highlight,changeSelection,check,cancel);

            ButtonType buttonType;
            do {
                String on = "ON";
                String off = "OFF";
                List<String> status = gui.statusChange(on, off);
                buttonType = OptionBox.display("Game-Settings",
                        " ChessBoard-Rotation: " + status.get(0)
                                + "\n Highlighting of Moves: " + status.get(1)
                                + "\n Change a selected Piece: " + status.get(2)
                                + "\n Player is in Check-Notification: " + status.get(3),
                        "Choose Option you want to Change:", options);
                if (buttonType == rotation) {
                    guiGame.isRotatingBoard = !guiGame.isRotatingBoard;
                } else if (buttonType == highlight) {
                    guiGame.highlightPossibleMoves = !guiGame.highlightPossibleMoves;
                } else if (buttonType == changeSelection) {
                    guiGame.allowedToChangeSelectedPiece = !guiGame.allowedToChangeSelectedPiece;
                } else if (buttonType == check) {
                    guiGame.hintInCheck = !guiGame.hintInCheck;
                }
            } while (buttonType != cancel); // player chose CANCEL
        });

        // Define Move History-Button
        Button btnMoveHistory = new Button("Move History");
        btnMoveHistory.setOnAction(event -> {
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

        // Define Language-Button
        Button btnLanguage = new Button("Language");
        btnLanguage.setOnAction(event -> {
            chooseLanguage();
            chessScene = gui.chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
            primaryStage.show();
        });

        VBox box = new VBox(20);
        box.getChildren().addAll(btnOptions, btnNewGame, btnSaveGame, btnMoveHistory, btnLanguage);
        return box;
    }


}
