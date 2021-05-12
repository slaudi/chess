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
        Game currentGame = new Game();                                                          //initilizes new Game
        currentGame.chessBoard.toConsole();                                                          //shows initilized Board to Player

        while (!currentGame.currentPlayer.isLoser() && !currentGame.isADraw()) {                //keeps game running

            if (currentGame.currentPlayer.isInCheck()) {                                       //checks Check-Status of current Player
                System.out.println(currentGame.currentPlayer.getColour() + " is in check!");
            }
            System.out.println("Now playing as " + currentGame.currentPlayer.getColour());      //announces current Player-Colour
            String userInput = getInput();                                                      //gets inPut from current Player from Console


            while (!generateAnswer(userInput, currentGame)) {                                   //evaluates Input
                if (userInput.equals("beaten") || !isValidMove(userInput)) {
                    userInput = getInput();
                    continue;
                }

                Piece selectedPiece = currentGame.chessBoard.getMovingPieceFromInput(userInput);     //sets Moving Piece for evaluation
                Piece targetPiece = currentGame.chessBoard.getFinalSquareFromInput(userInput).getOccupiedBy();// sets Square to move to for evaluation

                if (selectedPiece == null) {                                                    //evaluates if there is any Piece on selected Square
                    System.out.println("There is no Piece to move!");
                }
                //Console-Output if selected Piece has wrong Colour
                assert selectedPiece != null;
                if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
                    System.out.println("This is not your Piece to move!");
                }
                if (targetPiece != null && selectedPiece.getSquare() == targetPiece.getSquare()) {
                    System.out.println("You have to move!");
                }
                if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {  //Console-Output if selected Square to move to is occupied by an own Piece
                    System.out.println("You cannot attack your own Piece!");
                }
                userInput = getInput();
            }

            if (currentGame.currentPlayer.isLoser()) {                                         //evaluates if current Player has already lost
            continue;
            }
            Square startSquare = currentGame.chessBoard.getStartSquareFromInput(userInput);
            Square finalSquare = currentGame.chessBoard.getFinalSquareFromInput(userInput);
            char key = currentGame.chessBoard.getPromotionKey(userInput);

            if (!currentGame.processMove(startSquare, finalSquare, key)) {                           //checks if input-move is allowed
                // back to the beginning of while loop
                continue;
            }

            currentGame.chessBoard.toConsole();                                                      //gives current state of game to console
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
     * @param userInput Input from Console as a String
     * @param currentGame the current game
     * @return a boolean indicating if the move is accepted
     */
    public static boolean generateAnswer (String userInput, Game currentGame){
        if ("beaten".equals(userInput)) {                                                 //gives list of beaten pieces to console
            System.out.println("Beaten pieces:" + currentGame.beatenPieces);
            return false;
        }
        if ("giveUp".equals(userInput)) {                                                   //ends game for current player
            System.out.println(currentGame.currentPlayer.getColour() + " gave up!");
            currentGame.currentPlayer.setLoser(true);
            return true;
        }
        if (isValidMove(userInput)) {                                                       //validates user-input syntactically
            Piece selectedPiece = currentGame.chessBoard.getMovingPieceFromInput(userInput);
            Square finalSquare = currentGame.chessBoard.getFinalSquareFromInput(userInput);

            if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {                     //validates user-input semantically
                System.out.println("!" + userInput);
                return true;
            } else {
                System.out.println("!Move not allowed");
                return false;
            }
        } else {
            System.out.println("!Invalid move");
            return false;
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

