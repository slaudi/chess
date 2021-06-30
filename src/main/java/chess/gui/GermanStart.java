package chess.gui;

import chess.game.Colour;
import chess.game.Language;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
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

            ButtonType colourResult = OptionBox.display("Farb-Auswahl",null,"Wähle deine Farbe:", colour);
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
}
