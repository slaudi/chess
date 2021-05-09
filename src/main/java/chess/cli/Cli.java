package chess.cli;

import chess.game.Game;
import chess.game.Square;
import chess.pieces.Piece;

import java.util.Scanner;

/**
 * Starting point of the command line interface
 */
public class Cli {

    // TODO: ein Spieler gibt auf!
    /**
     * The entry point of the CLI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        Game currentGame = new Game();
        currentGame.board.toConsole();

        while(!currentGame.currentPlayer.getLoser() /*&& !currentGame.isADraw() && !aufgegeben*/) {

            System.out.println("Now playing as " + currentGame.currentPlayer.getColour());
            String userInput = getInput();

            Piece selectedPiece = currentGame.board.getMovingPieceFromInput(userInput);
            Piece targetPiece = currentGame.board.getFinalSquareFromInput(userInput).getOccupiedBy();

            while (!generateAnswer(userInput, currentGame)) {
                if (selectedPiece == null) {
                    System.out.println("There is no Piece to move!");
                }
                assert selectedPiece != null;
                if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
                    System.out.println("This is not your Piece to move!");
                }
                if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {
                    System.out.println("You cannot attack your own Piece!");
                }
                userInput = getInput();
            }

            Square startSquare = currentGame.board.getStartSquareFromInput(userInput);
            Square finalSquare = currentGame.board.getFinalSquareFromInput(userInput);

            if (!currentGame.processMove(startSquare, finalSquare)) {
                // goes back to the beginning of the while loop, doesn't switch players or redraws board
                continue;
            }
            currentGame.currentPlayer = currentGame.currentPlayer == currentGame.playerWhite ?
                    currentGame.playerBlack : currentGame.playerWhite;

            currentGame.board.toConsole();
        }

        if(currentGame.currentPlayer.getLoser()) {
            System.out.println(currentGame.currentPlayer.getColour() + " is Loser!");
        } else /*if(currentGame.isADraw())*/{
            System.out.println("The game ended in a draw!");
        } /*else {
            System.out.println(currentGame.currentPlayer.getColour() + " has given up!");
            }*/

    }

    /**
     * Gets input as a String from the console.
     *
     * @return a String of the input
     */
    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Move or Command:");
        return scanner.nextLine();
    }

    /**
     * Computes an answer based on Console-Input and state of current game.
     *
     * @param userInput Input from Console as a String
     * @param currentGame the current game
     * @return a boolean indicating if the move is accepted
     */
    public static boolean generateAnswer(String userInput, Game currentGame) {
        Piece selectedPiece = currentGame.board.getMovingPieceFromInput(userInput);
        Square finalSquare = currentGame.board.getFinalSquareFromInput(userInput);;

        // TODO: funktioniert noch nicht
        /*String beaten = "beaten";
        if (userInput.equals(beaten)) {
            System.out.println("Beaten pieces:" + currentGame.beatenPieces);
            return false;
        }*/
        if(currentGame.isValidMove(userInput)){
            if (currentGame.isMoveAllowed(selectedPiece, finalSquare)){
                System.out.println("!" + userInput);
                return true;
            } else {
                System.out.println("!MoveNotAllowed");
                return false;
            }
        } else {
            System.out.println("!InvalidMove");
            return false;
        }
    }
}
