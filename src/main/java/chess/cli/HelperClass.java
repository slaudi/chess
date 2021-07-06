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

import static chess.cli.Cli.checkForCommand;

public class HelperClass {


    /**
     * Gets the input as a String from the console.
     *
     * @return String A String of the console input.
     */
    public static String getInput (Game currentGame) {
        Scanner scanner = new Scanner(System.in);
        languageOutput("Enter Move or Command: ","Zug oder Kommando: ",currentGame);
        return scanner.nextLine();
    }


    public static void languageOutput(String messageEnglish, String messageGerman, Game game){
        if (game.getLanguage() == Language.English) {
            System.out.println(messageEnglish);
        } else {
            System.out.println(messageGerman);
        }
    }


    public static String checkForInt(Game currentGame){
        String input = new Scanner(System.in).nextLine();
        // TODO IP Adresse einfach ohne Punkte eingegeben? Könnten wir ja in die Anleitung schreiben
        checkForCommand(input,currentGame);
        try {
            Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            languageOutput(input + " is not a valid integer",input+" ist kein Integer-Wert.",currentGame);
            checkForInt(currentGame);
        }
        return input;
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


    public static String getGermanColourName(Game game) {
        if (game.currentPlayer.getColour() == Colour.BLACK) {
            return Colour.SCHWARZ.toString();
        } else {
            return Colour.WEISS.toString();
        }
    }


    public static void openPDF(Game currentGame){
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("Bedienungsanleitung.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
                languageOutput("Es wurde kein PDF Viewer gefunden!", "No PDF viewer found!", currentGame);
            }
        }
    }


    /**
     * The selected Language of the Game is English:
     * Evaluates console input if a move is not allowed and based on state of current game
     * generates an output as to why it's not allowed.
     *
     * @param selectedPiece The Piece the player wants to move.
     * @param finalSquare   The Square the piece wants to move to.
     * @param currentGame   The current game.
     */
    public static void generateAnswer(Piece selectedPiece, Square finalSquare, Game currentGame){
        Piece targetPiece = finalSquare.getOccupiedBy();
        if (selectedPiece == null) {
            languageOutput("There is no Piece to move!\n","Auf dem ausgewählten Feld steht keine Figur!\n",currentGame);
        } else if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
            languageOutput("This is not your Piece to move!\n","Auf dem ausgewählten Feld steht keine Figur!\n",currentGame);
        } else if (targetPiece != null && selectedPiece.getSquare() == finalSquare) {
            languageOutput("You have to move!\n","Du musst einen Zug machen!\n",currentGame);
        } else if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {
            languageOutput("You cannot attack your own Piece!\n","Du kannst nicht deine eigene Figur angreifen!\n",currentGame);
        }
    }


    /**
     * Prints the last statement when a game is lost or a draw is reached in either english or german
     * depending on the currently selected language.
     *
     * @param currentGame The current state of the game.
     */
    public static void finalWords(Game currentGame){
        if (currentGame.currentPlayer.isLoser()) {
            languageOutput(currentGame.currentPlayer.getColour() + " has lost!",
                    getGermanColourName(currentGame)+" hat verloren!",currentGame);
            currentGame.currentPlayer = currentGame.currentPlayer == currentGame.playerWhite
                    ? currentGame.playerBlack : currentGame.playerWhite;
            languageOutput("The Winner is " + currentGame.currentPlayer.getColour() + "!\n",
                    "Die Partie gewonnen hat " + getGermanColourName(currentGame) + "!\n",currentGame);
        } else if (currentGame.isDrawn()) {
            languageOutput("The game ended in a draw!\n","Die Partie endet in einem Unentschieden!\n",currentGame);
        }
    }

}
