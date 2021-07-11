package chess.cli;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Language;
import chess.game.Square;
import chess.pieces.Piece;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * A helper class for the cli package to make it more concise.
 */
public class HelperClass {

    /**
     * Gets the input as a String from the console.
     *
     * @param game The current state of the game.
     * @return String A String of the console input.
     */
    public static String getInput (Game game) {
        Scanner scanner = new Scanner(System.in);
        languageOutput("Enter Move or Command: ","Zug oder Kommando: ",game);
        return scanner.nextLine();
    }


    /**
     * Method which prints a message in the correct language on the CLI according to the language set at the moment.
     *
     * @param messageEnglish The message in english.
     * @param messageGerman The message in german.
     * @param game The state of the current game
     */
    public static void languageOutput(String messageEnglish, String messageGerman, Game game){
        if (game.getLanguage() == Language.English) {
            System.out.println(messageEnglish);
        } else {
            System.out.println(messageGerman);
        }
    }


    /**
     * Method which checks if an input in the form of a String is an Integer, otherwise throws a
     * NumberFormatException with a printed message.
     *
     * @param input The input by the user as a String.
     * @param game The state of the current game.
     * @return boolean Returns 'true' if the input is an Integer.
     */
    public static boolean checkForInt(String input, Game game) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            languageOutput(input + " is not a valid integer",
                    input+" ist kein Integer-Wert.",game);
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


    /**
     * Method which will determine the german pendant to the current player colour.
     *
     * @param game The current state of the game.
     * @return String The correlating colour in german.
     */
    public static String getGermanColourName(Game game) {
        if (game.currentPlayer.getColour() == Colour.BLACK) {
            return Colour.SCHWARZ.toString();
        } else {
            return Colour.WEISS.toString();
        }
    }


    /**
     * Method which will open open the user manual if a PDF viewer is installed. Will otherwise catch the
     * IOException and print an according message.
     *
     * @param game The current state of the game.
     */
    public static void openPDF(Game game){
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("Bedienungsanleitung.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
                languageOutput("Es wurde kein PDF Viewer gefunden!", "No PDF viewer found!", game);
            }
        }
    }


    /**
     * Evaluates console input if a move is not allowed and based on state of current game
     * generates an output as to why it's not allowed.
     *
     * @param selectedPiece The Piece the player wants to move.
     * @param finalSquare   The Square the piece wants to move to.
     * @param game   The current game.
     */
    public static void generateAnswer(Piece selectedPiece, Square finalSquare, Game game){
        Piece targetPiece = finalSquare.getOccupiedBy();
        if (selectedPiece == null) {
            languageOutput("There is no Piece to move!\n",
                    "Auf dem ausgewählten Feld steht keine Figur!\n",game);
        } else if (selectedPiece.getColour() != game.currentPlayer.getColour()) {
            languageOutput("This is not your Piece to move!\n",
                    "Auf dem ausgewählten Feld steht keine Figur!\n",game);
        } else if (targetPiece != null && selectedPiece.getSquare() == finalSquare) {
            languageOutput("You have to move!\n","Du musst einen Zug machen!\n",game);
        } else if (targetPiece != null && targetPiece.getColour() == game.currentPlayer.getColour()) {
            languageOutput("You cannot attack your own Piece!\n",
                    "Du kannst nicht deine eigene Figur angreifen!\n",game);
        }
    }


    /**
     * Prints the last statement when a game is lost or a draw is reached.
     *
     * @param game The current state of the game.
     */
    public static void finalWords(Game game){
        if (game.currentPlayer.isLoser()) {
            languageOutput(game.currentPlayer.getColour() + " has lost!",
                    getGermanColourName(game)+" hat verloren!",game);
            game.currentPlayer = game.currentPlayer == game.playerWhite
                    ? game.playerBlack : game.playerWhite;
            languageOutput("The Winner is " + game.currentPlayer.getColour() + "!\n",
                    "Die Partie gewonnen hat " + getGermanColourName(game) + "!\n",game);
        } else if (game.isDrawn()) {
            languageOutput("The game ended in a draw!\n",
                    "Die Partie endet in einem Unentschieden!\n",game);
        }
    }

    public static String generateMoveString (Square start, Square end){
        String moveString = "";
        moveString = moveString + start.getCharFromX() + start.getCharFromY() + '-' + end.getCharFromX() + end.getCharFromY();
        System.out.println(moveString);
        return moveString;
    }

}
