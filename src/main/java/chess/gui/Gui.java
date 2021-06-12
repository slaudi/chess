package chess.gui;

import chess.game.Colour;
import chess.game.Move;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

    private static Scene startScene, chessScene;

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
        GuiGame currentGame = new GuiGame();

        // Start
        startScene = startWindow(primaryStage,currentGame);

        // Chess board
        chessScene = chessWindow(primaryStage, currentGame);


        primaryStage.setScene(startScene);
        primaryStage.setTitle("Chess!");
        primaryStage.show();
    }


    private Scene startWindow(Stage primaryStage, GuiGame guiGame) {

        Label label = new Label("Welcome to a new Game of Chess!");

        // Define Start Game-Button
        Button startLocalGame = new Button("Start Game");
        startLocalGame.setOnAction(e -> {
            chooseEnemy(guiGame);
            chessScene = chessWindow(primaryStage,guiGame);
            primaryStage.setScene(chessScene);
        });

        // Define Network Game-Button
        Button startNetworkGame = new Button("Network Game");
        startNetworkGame.setOnAction(e -> startNetworkGame(primaryStage));

        // Define Load Game-Button
        Button loadGame = new Button("Load Game");
        loadGame.setOnAction(e -> {
                boolean result = ConfirmationBox.display("Load Game","Do you want to load a saved Game?");

                if (result) {
                    // TODO: implement loading a game
                    primaryStage.setScene(chessScene);
                }
        });

        // Define Language-Button
        Button language = new Button("Language");
        language.setOnAction(e -> chooseLanguage(guiGame));

        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll(label, startLocalGame, startNetworkGame, loadGame, language);
        layout1.setAlignment(Pos.CENTER);

        return new Scene(layout1,300,300);
    }


    private void chooseEnemy(GuiGame guiGame) {
        ButtonType human = new ButtonType("Person");
        ButtonType computer = new ButtonType("AI");

        List<ButtonType> enemy = new ArrayList<>();
        Collections.addAll(enemy,human,computer);

        ButtonType enemyResult = OptionBox.display("Enemy Selection",null,"Choose your Enemy",enemy);
        if (enemyResult == computer){
            guiGame.game.setEnemyHuman(false);
            guiGame.isRotatingBoard = false;
        }

        if(!guiGame.game.isEnemyHuman()) {
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


    private void startNetworkGame(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Scene IP_scene = new Scene(grid, 350,150);
        grid.add(new Label("Enter IP Address of Enemy: "), 0, 0);

        TextField IPAddress = new TextField();
        grid.add(IPAddress,1,2);

        HBox btn = new HBox();
        btn.setPadding(new Insets(5));
        btn.setSpacing(10);
        btn.setAlignment(Pos.BOTTOM_CENTER);
        // Define buttons
        Button startGame = new Button("Start Connection");
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> primaryStage.setScene(startScene));
        btn.getChildren().addAll(startGame,cancel);
        grid.add(btn,1,4);

        primaryStage.setScene(IP_scene);
        primaryStage.show();
    }

    private void chooseLanguage(GuiGame guiGame) {
        ButtonType german = new ButtonType("Deutsch");
        ButtonType english = new ButtonType("English");

        List<ButtonType> language = new ArrayList<>();
        Collections.addAll(language,german,english);
        ButtonType result;
        if (guiGame.game.isGerman()) {
            result = OptionBox.display("Sprachauswahl",null,"Wähle Sprache",language);
        } else {
            result = OptionBox.display("Language Selection", null, "Choose Language", language);
        }
        guiGame.game.setGerman(result == german);
    }

    private Scene chessWindow(Stage primaryStage, GuiGame guiGame) {
        BorderPane pane = new BorderPane();

        ChessBoardView chessBoardView = new ChessBoardView(guiGame);
        VBox right = generateRightMarginColumn(guiGame, primaryStage);

        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(30));
        pane.setRight(right);
        pane.setCenter(chessBoardView);

        return new Scene(pane, 900, 800);
    }


    private VBox generateRightMarginColumn(GuiGame guiGame, Stage primaryStage){
        //Define New Game-Button
        Button btnNewGame = new Button("New Game");
        btnNewGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("New Game", "Do you really want to start a new Game?");

            if (result) {
                GuiGame newGuiGame = new GuiGame();
                startScene = startWindow(primaryStage, newGuiGame);
                chessScene = chessWindow(primaryStage, newGuiGame);

                primaryStage.setScene(startScene);
                primaryStage.setTitle("Chess!");
                primaryStage.show();
            }
        });

        // Define Option-Button
        Button btnOptions = new Button("Options");
        btnOptions.setOnAction(event -> {
            ButtonType buttonTypeOne = new ButtonType("Rotation");
            ButtonType buttonTypeTwo = new ButtonType("Highlight");
            ButtonType buttonTypeThree = new ButtonType("Change Selection");
            ButtonType buttonTypeFour = new ButtonType("Check");
            ButtonType buttonTypeFive = new ButtonType("Cancel");

            ButtonType buttonType;
            do {
                String isBoardRotationStatus;
                String highlightPossibleMoveStatus;
                String allowedChangeSelectedPieceStatus;
                String hintInCheckStatus;
                String on = "ON";
                String off = "OFF";
                if(guiGame.isRotatingBoard){
                    isBoardRotationStatus = on;
                } else {
                    isBoardRotationStatus = off;
                }
                if(guiGame.highlightPossibleMoves){
                    highlightPossibleMoveStatus = on;
                } else {
                    highlightPossibleMoveStatus = off;
                }
                if(guiGame.allowedToChangeSelectedPiece){
                    allowedChangeSelectedPieceStatus = on;
                } else {
                    allowedChangeSelectedPieceStatus = off;
                }
                if(guiGame.hintInCheck){
                    hintInCheckStatus = on;
                } else {
                    hintInCheckStatus = off;
                }

                List<ButtonType> options = new ArrayList<>();
                Collections.addAll(options,buttonTypeOne,buttonTypeTwo,buttonTypeThree,buttonTypeFour,buttonTypeFive);
                buttonType = OptionBox.display("Game-Settings",
                        " ChessBoard-Rotation: " + isBoardRotationStatus
                                + "\n Highlighting of Moves: " + highlightPossibleMoveStatus
                                + "\n Change a selected Piece: " + allowedChangeSelectedPieceStatus
                                + "\n Player is in Check-Notification: " + hintInCheckStatus,
                        "Choose Option you want to Change:", options);
                if (buttonType == buttonTypeOne) {
                    guiGame.isRotatingBoard = !guiGame.isRotatingBoard;
                } else if (buttonType == buttonTypeTwo) {
                    guiGame.highlightPossibleMoves = !guiGame.highlightPossibleMoves;
                } else if (buttonType == buttonTypeThree) {
                    guiGame.allowedToChangeSelectedPiece = !guiGame.allowedToChangeSelectedPiece;
                } else if (buttonType == buttonTypeFour) {
                    guiGame.hintInCheck = !guiGame.hintInCheck;
                }
            } while (buttonType != buttonTypeFive); // user chose CANCEL
        });

        // Define Move History-Button
        Button btnMoveHistory = new Button("Move-History");
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

        VBox box = new VBox(20);
        box.getChildren().addAll(btnOptions, btnNewGame, btnMoveHistory);
        return box;
    }



}
