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

public class EnglishStart {

    public GuiGame guiGame;
    public Gui gui;
    public Language language;

    public EnglishStart(GuiGame guiGame, Gui gui){
        this.guiGame = guiGame;
        this.gui = gui;
        this.language = Language.English;
    }

    void startWindowEnglish(Stage primaryStage, BorderPane pane) {
        primaryStage.setTitle("Chess!");

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


}
