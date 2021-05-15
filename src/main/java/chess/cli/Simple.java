package chess.cli;


import chess.game.Game;
import chess.game.Label;
import chess.game.Square;
import chess.pieces.Piece;

import java.util.Scanner;

/**
 * Starting point of the command line interface with argument '--simple'
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

            while(!currentGame.currentPlayer.isLoser() || !currentGame.isADraw()) {
                String userInput = getInput();

                if (!isValidMove(userInput)) {
                    //validates user-input syntactically
                    System.out.println("!Invalid move\n");
                    continue;
                }
                Piece selectedPiece = currentGame.chessBoard.getMovingPieceFromInput(userInput);
                Square startSquare = currentGame.chessBoard.getStartSquareFromInput(userInput);
                Square finalSquare = currentGame.chessBoard.getFinalSquareFromInput(userInput);
                char key = currentGame.chessBoard.getPromotionKey(userInput);

                if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {                     //validates user-input semantically
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
     * Gets input as a String from the console.
     *
     * @return a String of the input
     */
    public static String getInput () {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Checks if Console Input is a syntactical correct Move.
     *
     * @param consoleInput Input of active Player as a String.
     * @return a boolean if the syntax of the input is correct
     */
    public static boolean isValidMove(String consoleInput){

        if(consoleInput.length() > 4 && consoleInput.length() < 7) {
            if (consoleInput.length() == 6) {
                char[] keys = {'Q','B','N','R'};
                for (char key : keys) {
                    if (key != consoleInput.charAt(consoleInput.length()-1)) {
                        if (key == keys[keys.length - 1]) {
                            // key reached R and the input still doesn't contain a char from keys
                            return false;
                        }
                        break;
                    }
                }
            }
            if (consoleInput.charAt(2) == '-') {
                return Label.contains(consoleInput.substring(0, 2)) && Label.contains(consoleInput.substring(3, 5));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
