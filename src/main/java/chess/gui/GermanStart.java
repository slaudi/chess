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
