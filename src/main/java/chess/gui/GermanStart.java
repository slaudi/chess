package chess.gui;

import chess.game.Colour;
import chess.game.Language;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class GermanStart {

    public GuiGame guiGame;
    public Gui gui;
    public Language language = Language.German;

    public GermanStart(GuiGame guiGame, Gui gui) {
        this.guiGame = guiGame;
        this.gui = gui;
    }


    void startWindowGerman(Stage primaryStage, BorderPane pane) {
        primaryStage.setTitle("Schach!");

        Button welcome = new Button("Willkommen zu einer neuen Partie Schach!");
        welcome.getStyleClass().add("startLabel");
        BorderPane.setAlignment(welcome,Pos.TOP_CENTER);
        BorderPane.setMargin(welcome,new Insets(50,0,60,0));
        pane.setTop(welcome);

        // Define Start Game-Button
        Button startLocalGame = new Button("Starte Spiel");
        startLocalGame.getStyleClass().add("startButtons");
        startLocalGame.setOnAction(e -> {
            chooseEnemyGerman();
            chessScene = gui.chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
        });

        // Define Network Game-Button
        Button startNetworkGame = new Button("Netzwerk-Spiel");
        startNetworkGame.getStyleClass().add("startButtons");
        startNetworkGame.setOnAction(e -> gui.startNetworkGame(primaryStage));

        // Define Load Game-Button
        Button loadGame = new Button("Lade Spiel");
        loadGame.getStyleClass().add("startButtons");
        loadGame.setOnAction(e -> {
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

                    gui.loadGame(dialog);
                }
                chessScene = gui.chessWindow(primaryStage);
                primaryStage.setScene(chessScene);
            }
        });

        // Define Language-Button
        Button language = new Button("Sprache");
        language.getStyleClass().add("startButtons");
        language.setOnAction(e -> {
            chooseLanguage();
            startScene = gui.startWindow(primaryStage);
            primaryStage.setScene(startScene);
            primaryStage.show();
        });

        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll( startLocalGame, startNetworkGame, loadGame, language);
        layout1.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(layout1);
    }


    private void chooseEnemyGerman() {
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

            ButtonType colourResult = OptionBox.display("Farb-Auswahl",null,"Wähle deine Farbe:", colour);
            if (colourResult == white) {
                guiGame.game.setUserColour(Colour.WHITE);
            } else {
                guiGame.game.setUserColour(Colour.BLACK);
                guiGame.turnAI = true;
            }
        }
    }


    VBox generateRightMarginColumnGerman(Stage primaryStage){
        //Define New Game-Button
        Button btnNewGame = new Button("Neues Spiel");
        btnNewGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Neues Spiel", "Möchtest du wirklich ein neues Spiel starten?",this.language);
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
        Button btnSaveGame = new Button("Spiel speichern");
        btnSaveGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("Spiel speichern", "Möchtest du das Spiel speichern?",this.language);
            if (result) {
                SaveGame.save(guiGame.game);
            }
        });

        // Define Option-Button
        Button btnOptions = new Button("Optionen");
        btnOptions.setOnAction(event -> {
            ButtonType buttonTypeOne = new ButtonType("Rotieren");
            ButtonType buttonTypeTwo = new ButtonType("Hervorheben");
            ButtonType buttonTypeThree = new ButtonType("Auswahl ändern");
            ButtonType buttonTypeFour = new ButtonType("Schach");
            ButtonType buttonTypeFive = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
            List<ButtonType> options = new ArrayList<>();
            Collections.addAll(options,buttonTypeOne,buttonTypeTwo,buttonTypeThree,buttonTypeFour,buttonTypeFive);

            ButtonType buttonType;
            do {
                String on = "AN";
                String off = "AUS";
                List<String> status = gui.statusChange(on, off);
                buttonType = OptionBox.display("Spiel-Einstellungen",
                        " Schachbrett rotiert: " + status.get(0)
                                + "\n Mögliche Züge hervorheben: " + status.get(1)
                                + "\n Ausgewählte Figur ändern: " + status.get(2)
                                + "\n Hinweis: Spieler befindet sich im Schach: " + status.get(3),
                        "Wähle Option, die du ändern möchtest:", options);
                if (buttonType == buttonTypeOne) {
                    guiGame.isRotatingBoard = !guiGame.isRotatingBoard;
                } else if (buttonType == buttonTypeTwo) {
                    guiGame.highlightPossibleMoves = !guiGame.highlightPossibleMoves;
                } else if (buttonType == buttonTypeThree) {
                    guiGame.allowedToChangeSelectedPiece = !guiGame.allowedToChangeSelectedPiece;
                } else if (buttonType == buttonTypeFour) {
                    guiGame.hintInCheck = !guiGame.hintInCheck;
                }
            } while (buttonType != buttonTypeFive); // player chose CANCEL
        });

        // Define Move History-Button
        Button btnMoveHistory = new Button("Zug-Historie");
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
            AlertBox.display("Zug-Historie", null, historyAsString.toString());
        });

        // Define Language-Button
        Button btnLanguage = new Button("Sprache");
        btnLanguage.setOnAction(event -> {
            chooseLanguage();
            chessScene = gui.chessWindow(primaryStage);
            primaryStage.setScene(chessScene);
            primaryStage.show();
        });

        VBox box = new VBox(20);
        box.getChildren().addAll(btnOptions, btnSaveGame, btnNewGame, btnMoveHistory, btnLanguage);
        return box;
    }


    private void chooseLanguage() {
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

}
