package chess.cli;

import chess.game.Game;

/**
 * Starting point of the command line interface
 */
public class Cli {
    /**
     * The entry point of the CLI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {


        System.out.println("Läuft bis hier hin schon mal");
        Game currentGame = new Game();
        currentGame.board.toConsole();
    }
}
