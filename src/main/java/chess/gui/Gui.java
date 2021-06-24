package chess.gui;


import chess.game.Language;
import chess.savegame.LoadGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

    public GuiGame guiGame;
    public EnglishStart englishStart;
    public GermanStart germanStart;
    static Scene startScene, chessScene;

    /**
     * The entry point of the GUI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called by the Application to start the GUI.
     *
     * @param primaryStage The initial root stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        guiGame = new GuiGame();
        englishStart = new EnglishStart(guiGame,this);
        germanStart = new GermanStart(guiGame,this);

        // Start
        startScene = startWindow(primaryStage);

        // Chess board
        chessScene = chessWindow(primaryStage);

        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    Scene startWindow(Stage primaryStage){
        BorderPane pane = new BorderPane();
        Image chessboard = new Image(Objects.requireNonNull(Gui.class.getResourceAsStream("chessboard.jpeg")));
        pane.setBackground(new Background(new BackgroundImage(chessboard,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(40,40,
                false,false,false,true))));

        Scene startScene = new Scene(pane,500,480);
        startScene.getStylesheets().add(Objects.requireNonNull(Gui.class.getResource("style.css")).toString());

        if (guiGame.game.getLanguage() == Language.German) {
            germanStart.startWindowGerman(primaryStage, pane);
            String schach = "Schach!";
            primaryStage.setTitle(schach);
        } else {
            englishStart.startWindowEnglish(primaryStage, pane);
            String chess = "Chess!";
            primaryStage.setTitle(chess);
        }
        return startScene;
    }


    void loadGame(ChoiceDialog<String> dialog){
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            File loadingFile = new File("src/main/resources/saves/" + result.get());
            Scanner sc = null;
            try {
                sc = new Scanner(loadingFile);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            ArrayList<String> loadingGame = new ArrayList<>();
            while (true) {
                assert sc != null;
                if (!sc.hasNextLine()) break;
                loadingGame.add(sc.nextLine());
            }
            guiGame.game = LoadGame.load(loadingGame);
        }
    }


    void startNetworkGame(Stage primaryStage) {
        String ipAddress = "Enter IP Address of Enemy: ";
        String startConnection = "Start Connection";
        String cancel = "Cancel";
        if (guiGame.game.getLanguage() == Language.German){
            ipAddress = "Gebe die IP-Adresse deines Gegners ein: ";
            startConnection = "Starte Verbindung";
            cancel = "Abbrechen";
        }
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Scene IP_scene = new Scene(grid, 350,150);
        grid.add(new Label(ipAddress), 0, 0);

        TextField IPAddress = new TextField();
        grid.add(IPAddress,0,2);

        HBox btn = new HBox();
        btn.setPadding(new Insets(5));
        btn.setSpacing(10);
        btn.setAlignment(Pos.BOTTOM_CENTER);
        // Define buttons
        Button btnStartGame = new Button(startConnection);
        Button btnCancel = new Button(cancel);
        btnCancel.setOnAction(e -> primaryStage.setScene(startScene));
        btn.getChildren().addAll(btnStartGame,btnCancel);
        grid.add(btn,0,4);

        primaryStage.setScene(IP_scene);
        primaryStage.show();
    }


    Scene chessWindow(Stage primaryStage) {
        BorderPane pane = new BorderPane();

        ChessBoardView chessBoardView = new ChessBoardView(guiGame);
        VBox right;
        if (guiGame.game.getLanguage() == Language.German) {
            right = germanStart.generateRightMarginColumnGerman(primaryStage);
        } else {
            right = englishStart.generateRightMarginColumnEnglish(primaryStage);
        }

        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(30));
        pane.setRight(right);
        pane.setCenter(chessBoardView);

        return new Scene(pane, 900, 800);
    }

   /* MenuBar createMenu() {
        // File menu
        Menu gameMenu = new Menu("Game");
        Menu designMenu = new Menu()

        // Menu items
        gameMenu.getItems().add(new MenuItem("Settings"));

        // Menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(gameMenu);

        return menuBar;
    }
    */

    List<String> statusChange() {
        String rotation;
        String highlight;
        String changePiece;
        String hint;

        String on = "AN";
        String off = "AUS";
        if (guiGame.game.getLanguage() == Language.English){
            on = "ON";
            off = "OFF";
        }

        if(guiGame.isRotatingBoard){
            rotation = on;
        } else {
            rotation = off;
        }
        if(guiGame.highlightPossibleMoves){
            highlight = on;
        } else {
            highlight = off;
        }
        if(guiGame.allowedToChangeSelectedPiece){
            changePiece = on;
        } else {
            changePiece = off;
        }
        if(guiGame.hintInCheck){
            hint = on;
        } else {
            hint = off;
        }

        List<String> status = new ArrayList<>();
        Collections.addAll(status, rotation, highlight, changePiece, hint);
        return status;
    }

}
