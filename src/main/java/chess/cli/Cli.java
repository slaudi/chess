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
        Game currentGame = new Game();                                                          //initilizes new Game
        currentGame.board.toConsole();                                                          //shows initilized Board to Player

        while (!currentGame.currentPlayer.getLoser() && !currentGame.isADraw()) {                //keeps game running

            if (currentGame.currentPlayer.getInCheck()) {                                       //checks Check-Status of current Player
                System.out.println(currentGame.currentPlayer.getColour() + " is in check!");
            }
            System.out.println("Now playing as " + currentGame.currentPlayer.getColour());      //announces current Player-Colour
            String userInput = getInput();                                                      //gets inPut from current Player from Console


            while (!generateAnswer(userInput, currentGame)) {                                   //evaluates Input
                if (userInput.equals("beaten") || !currentGame.isValidMove(userInput)) {
                    userInput = getInput();
                    continue;
                }

                Piece selectedPiece = currentGame.board.getMovingPieceFromInput(userInput);     //sets Moving Piece for evaluation
                Piece targetPiece = currentGame.board.getFinalSquareFromInput(userInput).getOccupiedBy();// sets Square to move to for evaluation

                if (selectedPiece == null) {                                                    //evaluates if there is any Piece on selected Square
                    System.out.println("There is no Piece to move!");
                    //Console-Output if selected Piece has wrong Colour
                    if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
                        System.out.println("This is not your Piece to move!");
                    } else if (targetPiece != null && selectedPiece.getSquare() == targetPiece.getSquare()) {
                        System.out.println("You have to move!");
                        if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {//Console-Output if selected Square to move to is occupied by an own Piece
                            System.out.println("You cannot attack your own Piece!");
                        }
                        userInput = getInput();                                                         //sets User_input if everything is semantical correct
                    }

                    if (currentGame.currentPlayer.getLoser()) {                                         //evaluates if current Player has already lost
                        continue;
                    }
                    Square startSquare = currentGame.board.getStartSquareFromInput(userInput);
                    Square finalSquare = currentGame.board.getFinalSquareFromInput(userInput);

                    if (!currentGame.processMove(startSquare, finalSquare)) {                           //checks if input-move is allowed
                        // goes back to the beginning of the while loop, doesn't switch players or redraws board
                        continue;
                    }

                    currentGame.board.toConsole();                                                      //gives current state of game to console
                }

                if (currentGame.currentPlayer.getLoser()) {                                              //checks if current Player has lost or game is draw
                    System.out.println(currentGame.currentPlayer.getColour() + " is Loser!");
                } else if (currentGame.isADraw()) {
                    System.out.println("The game ended in a draw!");
                }
            }
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
     * @param userInput Input from Console as a String
     * @param currentGame the current game
     * @return a boolean indicating if the move is accepted
     */
    public static boolean generateAnswer (String userInput, Game currentGame){
        if (userInput.equals("beaten")) {                                                 //gives list of beaten pieces to console
            System.out.println("Beaten pieces:" + currentGame.beatenPieces);
            return false;
        }
        if (userInput.equals("giveUp")) {                                                   //ends game for current player
            System.out.println(currentGame.currentPlayer.getColour() + " gave up!");
            currentGame.currentPlayer.setLoser(true);
            return true;
        }
        if (currentGame.isValidMove(userInput)) {                                             //validates user-input syntactically
            Piece selectedPiece = currentGame.board.getMovingPieceFromInput(userInput);
            Square finalSquare = currentGame.board.getFinalSquareFromInput(userInput);
            ;

            if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {                     //validates user-input semantically
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

