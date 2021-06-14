package chess.cli;

import chess.engine.Engine;
import chess.game.*;
import chess.pieces.Piece;

import java.util.ArrayList;
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
        checkForModus(currentGame);

        while (!currentGame.isCheckMate() && !currentGame.isADraw()) {
            // to keep the game running

            if (!canPieceMove(currentGame)) {
                continue;
            }

            toConsole(currentGame);
        }
        if (currentGame.currentPlayer.isLoser()) {                                              //checks if current Player has lost or game is draw
            System.out.println(currentGame.currentPlayer.getColour() + " has lost!");
            currentGame.currentPlayer = currentGame.currentPlayer == currentGame.playerWhite ? currentGame.playerBlack : currentGame.playerWhite;
            System.out.println("The Winner is " + currentGame.currentPlayer.getColour() + "!");
        } else if (currentGame.isDraw()) {
            System.out.println("The game ended in a draw!");
        }
    }

    /**
     * Gets the input as a String from the console.
     *
     * @return String A String of the console input.
     */
    private static String getInput (Game currentGame) {
        String output;
        if (currentGame.isGerman()) {
            output = "Zug oder Kommando: ";
        } else {
            output = "Enter Move or Command: ";
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(output);
        return scanner.nextLine();
    }


    private static void checkForModus(Game currentGame) {
        System.out.println("Do you want to play against a person or an AI? \n person/ai");
        String answer;
        do {
            answer = getInput(currentGame);
            if (answer.equals("ai")) {
                System.out.println("Starting new Game against AI.");
                currentGame.currentPlayer = currentGame.playerWhite;
                currentGame.beatenPieces.clear();
                currentGame.moveHistory.clear();
                currentGame.setEnemyHuman(false);
                toConsole(currentGame);
            } else if (answer.equals("person")) {
                // default
                System.out.println("Starting new Game against another person.");
                toConsole(currentGame);
            }
        } while (!(answer.equals("ai") || answer.equals("person")));
    }


    private static boolean canPieceMove(Game currentGame) {
        String moveNotAllowed = null;
        String invalidMove = null;
        String nowPlaying = null;
        String check = null;
        if (currentGame.isGerman()) {
            moveNotAllowed = "Zug nicht erlaubt";
            invalidMove = "Keine gültige Eingabe\n";
            nowPlaying = "Du spielst als ";
            check = " befindet sich im Schach";
        }
        if (currentGame.currentPlayer.isInCheck()) {
            System.out.println(currentGame.currentPlayer.getColour() + check);
        }
        System.out.println(nowPlaying + currentGame.currentPlayer.getColour());
        String userInput = getInput(currentGame);

        if(checkForCommand(userInput, currentGame)){
            // Input is a command, not a Move
            return false;
        }
        if (!isValidMove(userInput)) {
            // validates user-input syntactically
            System.out.println(invalidMove);
            return false;
        }
        Piece selectedPiece = currentGame.chessBoard.getMovingPieceFromInput(userInput);
        Square startSquare = currentGame.chessBoard.getStartSquareFromInput(userInput);
        Square finalSquare = currentGame.chessBoard.getFinalSquareFromInput(userInput);
        char key = currentGame.chessBoard.getPromotionKey(userInput);

        if (currentGame.isMoveAllowed(selectedPiece, finalSquare)) {
            // validates user-input semantically
            if (currentGame.processMove(startSquare, finalSquare, key)) {
                System.out.println("!" + userInput);
                if(!currentGame.isEnemyHuman()){
                    makeAIMove(currentGame);
                }
                return true;
            } else {
                // if move puts King in check
                System.out.println(moveNotAllowed + "\n" + currentGame.currentPlayer.getColour() + check + "\n");
                return false;
            }
        } else {
            System.out.println(moveNotAllowed + "\n");
            if (currentGame.isGerman()) {
                generateAnswerGerman(selectedPiece, finalSquare, currentGame);
            } else {
                generateAnswerEnglish(selectedPiece,finalSquare,currentGame);
            }
            return false;
        }
    }


    private static boolean checkForCommand(String userInput, Game currentGame){
        if (userInput.equals("beaten")) {
            System.out.println(currentGame.beatenPieces);
            return true;
        }
        if (userInput.equals("english")) {
            currentGame.setGerman(false);
            System.out.println("You changed the language to English.");
            return true;
        }
        if (userInput.equals("deutsch")) {
            currentGame.setGerman(true);
            System.out.println("Du hast die Sprache auf Deutsch geändert.");
            return true;
        }
        if (userInput.equals("giveUp")) {
            System.out.println(currentGame.currentPlayer.getColour() + " gave up!");
            currentGame.currentPlayer.setLoser(true);
            return true;
        }
        return false;
    }

    private static void makeAIMove(Game currentGame){
        Square startSquareEnemy = null;
        Square finalSquareEnemy = null;
        char keyEnemy;
        do {
            Move enemyMove = Engine.nextBestMove(currentGame);
            if (enemyMove == null) {
                currentGame.setDraw(true);
                break;
            }
            startSquareEnemy = enemyMove.getStartSquare();
            finalSquareEnemy = enemyMove.getFinalSquare();
            keyEnemy = 'Q';
        } while (!currentGame.processMove(startSquareEnemy,finalSquareEnemy,keyEnemy));
        if (currentGame.isDraw()){
            System.out.println(" ");
        } else {
            assert startSquareEnemy != null;
            System.out.println("!" + startSquareEnemy.getLabel().toString() + "-" + finalSquareEnemy.getLabel().toString() + "\n");
        }
    }

    /**
     * Evaluates console input if a move is not allowed and based on state of current game
     * generates an output as to why it's not allowed.
     *
     * @param selectedPiece The Piece the player wants to move.
     * @param finalSquare   The Square the piece wants to move to.
     * @param currentGame   The current game.
     */
    private static void generateAnswerEnglish (Piece selectedPiece, Square finalSquare, Game currentGame){
        Piece targetPiece = finalSquare.getOccupiedBy();
        if (selectedPiece == null) {
            System.out.println("There is no Piece to move!\n");
        } else if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
            System.out.println("This is not your Piece to move!\n");
        } else if (targetPiece != null && selectedPiece.getSquare() == finalSquare) {
            System.out.println("You have to move!\n");
        } else if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {
            System.out.println("You cannot attack your own Piece!\n");
        }
    }

    private static void generateAnswerGerman (Piece selectedPiece, Square finalSquare, Game currentGame){
        Piece targetPiece = finalSquare.getOccupiedBy();
        if (selectedPiece == null) {
            System.out.println("Auf dem ausgewählten Feld steht keine Figur!\n");
        } else if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
            System.out.println("Die ausgewählte Figur ist nicht deine Figur!\n");
        } else if (targetPiece != null && selectedPiece.getSquare() == finalSquare) {
            System.out.println("Du musst einen Zug machen!\n");
        } else if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {
            System.out.println("Du kannst nicht deine eigene Figur angreifen!\n");
        }
    }

    /**
     * Checks if the console input is a syntactical correct move.
     *
     * @param consoleInput The console input of the active Player as a String.
     * @return boolean Returns 'true' if the syntax of the input is correct.
     */
    static boolean isValidMove(String consoleInput){
        ArrayList<String> keys = new ArrayList<>();
        keys.add("Q");
        keys.add("B");
        keys.add("N");
        keys.add("R");
        if(consoleInput.length() > 4 && consoleInput.length() < 7) {
            if (consoleInput.length() == 6) {
                if (!keys.contains(consoleInput.substring(5, 6))) {//NOPMD a collapse of the statement would cause a false 'true' return of the method
                    // key reached R and the input still doesn't contain a char from keys
                    return false;
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

    /**
     * Prints current state of game to console.
     *
     * @param game The current game.
     */
    public static void toConsole(Game game){
        for (int y = 0; y < game.chessBoard.getHeight(); y++){
            System.out.print(8-y);
            for (int x = 0; x < game.chessBoard.getWidth(); x++){
                if (game.chessBoard.getBoard()[x][y].getOccupiedBy() != null){
                    System.out.print(" " + game.chessBoard.getBoard()[x][y].getOccupiedBy().toString());
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("  a b c d e f g h");
    }

}

