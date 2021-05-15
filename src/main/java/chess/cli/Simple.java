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
            currentGame.chessBoard.toConsole();

            while(currentGame.currentPlayer.isLoser() || currentGame.isADraw()) {
                String userInput = getInput();

                if (!Cli.isValidMove(userInput)) {
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
                    System.out.println("!" + userInput + "\n");
                    if (!currentGame.processMove(startSquare, finalSquare, key)) {
                        System.out.println("!Move not allowed\n");
                        break;
                    }
                } else {
                    System.out.println("!Move not allowed\n");
                    break;
                }
                currentGame.chessBoard.toConsole();
            }
        }

    /**
     * Gets the input as a String from the console.
     *
     * @return String A String of the console input.
     */
    public static String getInput () {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
