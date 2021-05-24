package chess.gui;

import chess.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello Chess!");
        Game game = new Game();

        ChessBoardView testChessView = new ChessBoardView(game);
        primaryStage.setScene(new Scene(testChessView));
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
