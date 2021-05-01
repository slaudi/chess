package chess.cli;

import chess.game.Game;
import chess.game.Move;
import chess.pieces.Piece;

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
        currentGame.board.toConsole();
        while (true) {
            System.out.println(generateAnswer(getInput(), currentGame));
        }
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

    public static String generateAnswer(String input, Game game){
        if(Move.validMove(input)){
            if (game.board.getMovingPieceFromInput(input).isAllowedMove(game.board.getFinalSquareFromInput(input))){
                return "!" + input;
            }
            else return "!Move not allowed";
        }
        else return "!Invalid move";
    }
}
