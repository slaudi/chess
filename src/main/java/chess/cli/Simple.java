package chess.cli;

import chess.game.Game;
import chess.game.Square;
import chess.pieces.Piece;

import java.util.Scanner;

/**
 * Starting point of the command line interface with the argument '--simple'
 */
public class Simple {

    /**
     * The entry point of the SIMPLE application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        Game currentGame = new Game();
        HelperClass.toConsole(currentGame);

        while(!currentGame.currentPlayer.isLoser() || !currentGame.isADraw()) {
            String userInput = getInput();

            if (Cli.isNotValidMove(userInput)) {
                //validates user-input syntactically
                System.out.println("!Invalid move\n");
                continue;
            }
            Piece selectedPiece = currentGame.chessBoard.getMovingPieceFromInput(userInput);
            Square startSquare = currentGame.chessBoard.getStartSquareFromInput(userInput);
            Square finalSquare = currentGame.chessBoard.getFinalSquareFromInput(userInput);
            char key = currentGame.chessBoard.getPromotionKey(userInput);

            if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {
                //validates user-input semantically

                if (!currentGame.processMove(startSquare, finalSquare, key) && currentGame.currentPlayer.isInCheck()) {
                    System.out.println("!Move not allowed\n");
                    continue;
                }
                else{
                    System.out.println("!" + userInput + "\n");
                }
            } else {
                System.out.println("!Move not allowed\n");
                continue;
            }
            HelperClass.toConsole(currentGame);
        }
    }


    private static String getInput () {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
