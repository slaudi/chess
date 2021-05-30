package chess.gui;

import chess.game.Colour;
import chess.game.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

    Stage chessWindow;
    Scene startScene, chessScene;

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
        chessWindow = primaryStage;
        Game currentGame = new Game();

        // Start
        Label label = new Label("Welcome to a new Game of Chess!");
        Button startGame = new Button("Start Game");
        startGame.setOnAction(e -> {
            chooseEnemy(currentGame);
            chessWindow.setScene(chessScene);
        });
        Button loadGame = new Button("Load Game");
        loadGame.setOnAction(e -> chessWindow.setScene(chessScene));

        Button language = new Button("Language");
        language.setOnAction(e -> chooseLanguage());

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label,startGame,loadGame,language);
        layout1.setAlignment(Pos.CENTER);
        startScene = new Scene(layout1,400,400);

        // Chess board
        ChessBoardView chessBoardView = new ChessBoardView(currentGame);
        chessScene = new Scene(chessBoardView, 900, 900);

        chessWindow.setScene(startScene);
        chessWindow.setTitle("Chess!");
        chessWindow.show();
    }

    private void chooseEnemy(Game game) {
        if(game.freshGame){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Choose your Enemy:");

            ButtonType buttonTypeOne = new ButtonType("Human");
            ButtonType buttonTypeTwo = new ButtonType("Computer");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            game.enemyIsHuman = result.get() == buttonTypeOne;
            if(!game.enemyIsHuman) {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle(null);
                alert2.setHeaderText(null);
                alert2.setContentText("Choose your Colour:");

                ButtonType buttonTypeThree = new ButtonType("White");
                ButtonType buttonTypeFour = new ButtonType("Black");

                alert2.getButtonTypes().setAll(buttonTypeThree, buttonTypeFour);

                Optional<ButtonType> result2 = alert2.showAndWait();
                if (result2.get() == buttonTypeThree) {
                    game.userColour = Colour.WHITE;
                } else {
                    game.userColour = Colour.BLACK;
                }
            }
            game.freshGame = false;
        }
    }

    private void chooseLanguage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Choose Language: ");

        ButtonType german = new ButtonType("Deutsch");
        ButtonType english = new ButtonType("English");

        alert.getButtonTypes().setAll(german,english);
        alert.showAndWait();
    }
}
