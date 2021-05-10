package chess.cli;

import chess.game.Game;
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
        currentGame.board.toConsole();

        while(!currentGame.currentPlayer.getLoser() && !currentGame.isADraw()) {

            if (currentGame.currentPlayer.getInCheck()) {
                System.out.println(currentGame.currentPlayer.getColour() + " is in check!");
            }
            System.out.println("Now playing as " + currentGame.currentPlayer.getColour());
            String userInput = getInput();


            while (!generateAnswer(userInput, currentGame)) {
                if (userInput.equals("beaten") || !currentGame.isValidMove(userInput)) {
                    userInput = getInput();
                    continue;
                }

                Piece selectedPiece = currentGame.board.getMovingPieceFromInput(userInput);
                Piece targetPiece = currentGame.board.getFinalSquareFromInput(userInput).getOccupiedBy();

                if (selectedPiece == null) {
                    System.out.println("There is no Piece to move!");
                } else if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
                    System.out.println("This is not your Piece to move!");
                } else if (targetPiece != null && selectedPiece.getSquare() == targetPiece.getSquare()) {
                    System.out.println("You have to move!");
                } else if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {
                    System.out.println("You cannot attack your own Piece!");
                }
                userInput = getInput();
            }

            if (currentGame.currentPlayer.getLoser()) {
                continue;
            }

            Square startSquare = currentGame.board.getStartSquareFromInput(userInput);
            Square finalSquare = currentGame.board.getFinalSquareFromInput(userInput);

            if (!currentGame.processMove(startSquare, finalSquare)) {
                // goes back to the beginning of the while loop, doesn't switch players or redraws board
                continue;
            }

            currentGame.board.toConsole();
        }

        if(currentGame.currentPlayer.getLoser()) {
            System.out.println(currentGame.currentPlayer.getColour() + " is Loser!");
        } else if (currentGame.isADraw()){
            System.out.println("The game ended in a draw!");
        }

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
        if (userInput.equals("beaten")) {
            System.out.println("Beaten pieces:" + currentGame.beatenPieces);
            return false;
        }
        if (userInput.equals("giveUp")) {
            System.out.println(currentGame.currentPlayer.getColour() + " gave up!");
            currentGame.currentPlayer.setLoser(true);
            return true;
        }
        if(currentGame.isValidMove(userInput)){
            Piece selectedPiece = currentGame.board.getMovingPieceFromInput(userInput);
            Square finalSquare = currentGame.board.getFinalSquareFromInput(userInput);;

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
