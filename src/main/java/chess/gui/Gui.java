package chess.gui;

import chess.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {
    /**
     * This method is called by the Application to start the GUI.
     *
     * @param primaryStage The initial root stage of the application.
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        primaryStage.setTitle("Hello Chess!");
        Game currentGame = new Game();
        ChessBoardView chessBoardView = new ChessBoardView(currentGame);
        Scene chessScene = new Scene(chessBoardView);
        primaryStage.setScene(chessScene);
        primaryStage.show();
    }

    /**
     * The entry point of the GUI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
