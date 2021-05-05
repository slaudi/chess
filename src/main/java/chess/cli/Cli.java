package chess.cli;

import chess.game.Game;
import chess.game.Move;

import java.util.Scanner;

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
        currentGame.processGame();
    }

    /**
     * Gets input as String from console.
     * @return Input from console as String.
     */
    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Move or Command:");
        return scanner.nextLine();
    }

    /**
     * Computes an answer based on Console-Input and state of current game.
     * @param input Input from Console as a String
     * @param game Current game.
     * @return An answer to the Player if his entered Move is syntactically or semantically correct or incorrect.
     */
    public static String generateAnswer(String input, Game game){
        if(Move.isValidMove(input)){
            if (game.board.getMovingPieceFromInput(input).isAllowedPath(game.board.getFinalSquareFromInput(input))){
                return "!" + input;
            }
            else return "!Move not allowed";
        }
        else return "!Invalid move";
    }
}
