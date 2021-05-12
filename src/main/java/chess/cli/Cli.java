package chess.cli;

import chess.game.Game;
import chess.game.Label;
import chess.game.Square;
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
        Game currentGame = new Game();
        currentGame.chessBoard.toConsole();

        while (!currentGame.currentPlayer.isLoser() && !currentGame.isADraw()) {
            // to keep the game running

            if (currentGame.currentPlayer.isInCheck()) {
                System.out.println(currentGame.currentPlayer.getColour() + " is in check!");
            }
            System.out.println("Now playing as " + currentGame.currentPlayer.getColour());
            String userInput = getInput();

            if (!isValidMove(userInput)) {
                // validates user-input syntactically
                System.out.println("!Invalid move\n");
                continue;
            }

            Piece selectedPiece = currentGame.chessBoard.getMovingPieceFromInput(userInput);
            Square startSquare = currentGame.chessBoard.getStartSquareFromInput(userInput);
            Square finalSquare = currentGame.chessBoard.getFinalSquareFromInput(userInput);
            char key = currentGame.chessBoard.getPromotionKey(userInput);

            if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {
                // validates user-input semantically
                System.out.println("!" + userInput);
                if (!currentGame.processMove(startSquare, finalSquare, key)) {
                    // if move puts King in check
                    System.out.println("!Move not allowed\n");
                    break;
                }
            } else {
                System.out.println("!Move not allowed\n");
                generateAnswer(selectedPiece, finalSquare, currentGame);
                continue;
            }
            currentGame.chessBoard.toConsole();
        }
        if (currentGame.currentPlayer.isLoser()) {                                              //checks if current Player has lost or game is draw
            System.out.println(currentGame.currentPlayer.getColour() + " is Loser!");
        } else if (currentGame.isADraw()) {
            System.out.println("The game ended in a draw!");
        }
    }


    /**
     * Gets input as a String from the console.
     *
     * @return a String of the input
     */
    public static String getInput () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Move or Command:");
        return scanner.nextLine();
    }

    /**
     * evaluates Console-Input and state of current game
     *
     * @param selectedPiece The Piece the player wants to move
     * @param finalSquare   The Square the piece wants to move to
     * @param currentGame   The current game
     * @return a boolean indicating if the move is accepted
     */
    public static void generateAnswer (Piece selectedPiece, Square finalSquare, Game currentGame){
        Piece targetPiece = finalSquare.getOccupiedBy();
        if (selectedPiece == null) {
            System.out.println("There is no Piece to move!\n");
        } else if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
            System.out.println("This is not your Piece to move!\n");
        } else if (targetPiece != null && selectedPiece.getSquare() == targetPiece.getSquare()) {
            System.out.println("You have to move!\n");
        } else if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {
            System.out.println("You cannot attack your own Piece!\n");
        }
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

