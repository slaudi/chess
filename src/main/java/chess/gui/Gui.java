package chess.gui;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Move;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

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
    public void start(Stage primaryStage) throws InterruptedException {
        Game currentGame = new Game();

        // Start
        startScene = startWindow(primaryStage,currentGame);

        // Chess board
        chessScene = chessWindow(primaryStage, currentGame);

        primaryStage.setScene(startScene);
        primaryStage.setTitle("Chess!");
        primaryStage.show();
    }


    private Scene startWindow(Stage primaryStage, Game currentGame){
        Label label = new Label("Welcome to a new Game of Chess!");
        Button startLocalGame = new Button("Start Game");
        startLocalGame.setOnAction(e -> {
            chooseEnemy(currentGame);
            primaryStage.setScene(chessScene);
        });

        Button startNetworkGame = new Button("Network Game");
        startNetworkGame.setOnAction(e -> startNetworkGame(primaryStage));

        Button loadGame = new Button("Load Game");
        loadGame.setOnAction(e -> {
                boolean result = ConfirmationBox.display("Load Game","Do you want to load a saved Game?");

                if (result) {
                    // TODO: implement loading a game
                    primaryStage.setScene(chessScene);
                }
        });

        Button language = new Button("Language");
        language.setOnAction(e -> chooseLanguage());

        VBox layout1 = new VBox(25);
        layout1.getChildren().addAll(label, startLocalGame, startNetworkGame, loadGame, language);
        layout1.setAlignment(Pos.CENTER);
        return new Scene(layout1,300,300);
    }


    private void chooseEnemy(Game game) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Choose your Enemy:");

        ButtonType buttonTypeOne = new ButtonType("Human");
        ButtonType buttonTypeTwo = new ButtonType("Computer");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> game.enemyIsHuman = buttonType == buttonTypeOne);
        if(!game.enemyIsHuman) {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle(null);
            alert2.setHeaderText(null);
            alert2.setContentText("Choose your Colour:");

            ButtonType buttonTypeThree = new ButtonType("White");
            ButtonType buttonTypeFour = new ButtonType("Black");

            alert2.getButtonTypes().setAll(buttonTypeThree, buttonTypeFour);

            Optional<ButtonType> result2 = alert2.showAndWait();
            if (result2.isPresent()) {
                if (result2.get() == buttonTypeThree) {
                    game.userColour = Colour.WHITE;
                } else {
                    game.userColour = Colour.BLACK;
                }
            }
        }
    }

    private void startNetworkGame(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Scene IP_scene = new Scene(grid, 350,150);
        grid.add(new Label("IP Address: "), 0, 0);

        TextField IPAddress = new TextField();
        grid.add(IPAddress,1,2);

        HBox btn = new HBox();
        btn.setPadding(new Insets(5));
        btn.setSpacing(10);
        btn.setAlignment(Pos.BOTTOM_CENTER);
        Button startGame = new Button("Start Connection");
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> primaryStage.setScene(startScene));
        btn.getChildren().addAll(startGame,cancel);
        grid.add(btn,1,4);

        primaryStage.setScene(IP_scene);
        primaryStage.show();
    }

    private void chooseLanguage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Language Selection");
        alert.setHeaderText(null);
        alert.setContentText("Choose Language: ");

        ButtonType german = new ButtonType("Deutsch");
        ButtonType english = new ButtonType("English");

        alert.getButtonTypes().setAll(german,english);
        alert.showAndWait();
    }

    private Scene chessWindow(Stage primaryStage, Game currentGame) {
        BorderPane pane = new BorderPane();

        ChessBoardView chessBoardView = new ChessBoardView(currentGame);
        VBox right = generateRightMarginColumn(currentGame, primaryStage);

        right.setAlignment(Pos.CENTER);
        right.setPadding(new Insets(40));
        pane.setRight(right);
        pane.setCenter(chessBoardView);

        return new Scene(pane, 1000, 900);
    }


    private VBox generateRightMarginColumn(Game game, Stage primaryStage){
        Button btnNewGame = new Button("New Game");
        btnNewGame.setOnAction(event -> {
            boolean result = ConfirmationBox.display("New Game", "Do you really want to start a new Game?");

            if (result) {
                Game newGame = new Game();
                startScene = startWindow(primaryStage, newGame);
                chessScene = chessWindow(primaryStage, newGame);

                primaryStage.setScene(startScene);
                primaryStage.setTitle("Chess!");
                primaryStage.show();
            }
        });

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
                if(game.isRotatingBoard){
                    isBoardRotationStatus = on;
                } else {
                    isBoardRotationStatus = off;
                }
                if(game.highlightPossibleMoves){
                    highlightPossibleMoveStatus = on;
                } else {
                    highlightPossibleMoveStatus = off;
                }
                if(game.allowedToChangeSelectedPiece){
                    allowedChangeSelectedPieceStatus = on;
                } else {
                    allowedChangeSelectedPieceStatus = off;
                }
                if(game.hintInCheck){
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
                    game.isRotatingBoard = !game.isRotatingBoard;
                } else if (buttonType == buttonTypeTwo) {
                    game.highlightPossibleMoves = !game.highlightPossibleMoves;
                } else if (buttonType == buttonTypeThree) {
                    game.allowedToChangeSelectedPiece = !game.allowedToChangeSelectedPiece;
                } else if (buttonType == buttonTypeFour) {
                    game.hintInCheck = !game.hintInCheck;
                }  //user chose CANCEL or closed the dialog
            } while (buttonType != buttonTypeFive);


        });
        Button btnMoveHistory = new Button("Move-History");
        btnMoveHistory.setOnAction(event -> {
            List<Move> history = game.moveHistory;
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
        VBox box = new VBox(15);
        box.getChildren().addAll(btnNewGame, btnOptions, btnMoveHistory);
        return box;
    }



}
