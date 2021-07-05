package chess.cli;

import chess.engine.Engine;
import chess.game.*;
import chess.game.Label;
import chess.network.Network;
import chess.pieces.Piece;
import chess.savegame.LoadGame;
import chess.savegame.SaveGame;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        playGame(currentGame);
    }

    private static void playGame(Game currentGame){
        checkForModus(currentGame);

        while (!currentGame.isCheckMate() && !currentGame.isADraw() && !currentGame.currentPlayer.isLoser()) {
            // to keep the game running

            if (!canPieceMove(currentGame)) {
                continue;
            }

            toConsole(currentGame);
        }
        //checks if current Player has lost or if game is a draw
        finalWords(currentGame);
    }

    /**
     * Gets the input as a String from the console.
     *
     * @return String A String of the console input.
     */
    private static String getInput (Game currentGame) {
        String output;
        if (currentGame.getLanguage() == Language.German) {
            output = "Zug oder Kommando: ";
        } else {
            output = "Enter Move or Command: ";
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(output);
        return scanner.nextLine();
    }


    private static void checkForModus(Game currentGame) {
        String answer;
        System.out.println("Do you want to play a network or a local game? \n network/local");
        do {
            answer = getInput(currentGame);
            if (answer.equals("network")) {
                Network.startNetworkGame(currentGame);
            } else if (answer.equals("local")) {
                startLocalGame(currentGame);
            }
        } while (!answer.equals("network"));

    }


    private static void startLocalGame(Game currentGame){
        System.out.println("Do you want to play against a person or an AI? \n person/ai");
        String answer;
        do {
            answer = getInput(currentGame);
            if (answer.equals("ai")) {
                System.out.println("Starting new Game against AI.");
                currentGame.currentPlayer = currentGame.playerWhite;
                currentGame.beatenPieces.clear();
                currentGame.moveHistory.clear();
                currentGame.setArtificialEnemy(true);
                toConsole(currentGame);
            } else if (answer.equals("person")) {
                // default
                System.out.println("Starting new Game against another person.");
                toConsole(currentGame);
            }
        } while (!(answer.equals("ai") || answer.equals("person")));
    }

    private static boolean canPieceMove(Game currentGame) {//NOPMD - no reasonable way to split the code, would make it harder to read
        String moveNotAllowed = "!Move not allowed";
        String invalidMove = "!Invalid move";
        String nowPlaying = "'s move";
        String check = " is in check!";
        String  colour = currentGame.currentPlayer.getColour().toString();
        if (currentGame.getLanguage() == Language.German) {
            moveNotAllowed = "!Zug nicht erlaubt";
            invalidMove = "!Keine gültige Eingabe\n";
            nowPlaying = " ist am Zug";
            check = " befindet sich im Schach!";
            colour = getGermanColourName(currentGame);
        }

        if (currentGame.currentPlayer.isInCheck()) {
            System.out.println(colour + check);
        }
        System.out.println(colour + nowPlaying);

        String userInput = getInput(currentGame);

        if(checkForCommand(userInput, currentGame)){
            // Input is a command, not a Move
            return false;
        }
        if (isNotValidMove(userInput)) {
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
                if(currentGame.isArtificialEnemy()){
                    makeAIMove(currentGame);
                }
                return true;
            } else {
                // if move puts King in check
                System.out.println(moveNotAllowed + "\n" + colour + check + "\n");
                return false;
            }
        } else {
            System.out.println(moveNotAllowed + "\n");
            if (currentGame.getLanguage() == Language.German) {
                generateAnswerGerman(selectedPiece, finalSquare, currentGame);
            } else {
                generateAnswerEnglish(selectedPiece,finalSquare,currentGame);
            }
            return false;
        }
    }


    private static String getGermanColourName(Game game) {
        if (game.currentPlayer.getColour() == Colour.BLACK) {
            return Colour.SCHWARZ.toString();
        } else {
            return Colour.WEISS.toString();
        }
    }


    /**
     * Checks the input given by player for other commands than a move and performs the necessary action.
     *
     * @param userInput     The input of the player as a String.
     * @param currentGame   The current status of the game.
     * @return boolean Returns 'True' if a valid command was given.
     */
    public static boolean checkForCommand(String userInput, Game currentGame){//NOPMD - dividing into sub methods would make code harder to read
        if (userInput.equals("beaten")) {
            System.out.println(currentGame.beatenPieces);
            return true;
        }
        if (userInput.equals("english")) {
            currentGame.setLanguage(Language.English);
            System.out.println("You changed the language to English.");
            return true;
        }
        if (userInput.equals("deutsch")) {
            currentGame.setLanguage(Language.German);
            System.out.println("Du hast die Sprache zu Deutsch geändert.");
            return true;
        }
        if (userInput.equals("giveUp")) {
            System.out.println(currentGame.currentPlayer.getColour() + " gave up!");
            currentGame.currentPlayer.setLoser(true);
            return true;
        }
        if (userInput.equals("save")) {
            System.out.println("You saved the current stage of the game!");
            SaveGame.save(currentGame);
            return true;
        }
        if (userInput.equals("load")) {
            Game tempGame = cliLoad();
            if(tempGame != null) {
                currentGame.currentPlayer = tempGame.currentPlayer;
                currentGame.chessBoard = tempGame.chessBoard;
                currentGame.setUserColour(tempGame.getUserColour());
                currentGame.setArtificialEnemy(tempGame.isArtificialEnemy());
                currentGame.beatenPieces = tempGame.beatenPieces;
                currentGame.moveHistory = tempGame.moveHistory;
                currentGame.setLanguage(tempGame.getLanguage());
                toConsole(currentGame);
            }
            return true;
        }
        if (userInput.equals("newGame")){
            Game newGame = new Game();
            playGame(newGame);
        }
        if (userInput.equals("help")){
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("Bedienungsanleitung.pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                    if (currentGame.getLanguage() == Language.German) {
                        System.out.println("Es wurde kein PDF Viewer gefunden!");
                    } else {
                        System.out.println("No PDF viewer found!");
                    }
                }
            }
            return true;
        }
        if (userInput.equals("quit")){
            System.exit(0);
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
                currentGame.setDrawn(true);
                break;
            }
            startSquareEnemy = enemyMove.getStartSquare();
            finalSquareEnemy = enemyMove.getFinalSquare();
            keyEnemy = 'Q';
        } while (!currentGame.processMove(startSquareEnemy,finalSquareEnemy,keyEnemy));
        if (currentGame.isDrawn()){
            System.out.println(" ");
        } else {
            assert startSquareEnemy != null;
            System.out.println("!" + startSquareEnemy.getLabel().toString() + "-" + finalSquareEnemy.getLabel().toString() + "\n");
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
    public static void generateAnswerEnglish(Piece selectedPiece, Square finalSquare, Game currentGame){
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


    /**
     * The selected Language of the Game is German:
     * Evaluates console input if a move is not allowed and based on state of current game
     * generates an output as to why it's not allowed.
     *
     * @param selectedPiece The Piece the player wants to move.
     * @param finalSquare   The Square the piece wants to move to.
     * @param currentGame   The current game.
     */
    public static void generateAnswerGerman(Piece selectedPiece, Square finalSquare, Game currentGame){
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
    public static boolean isNotValidMove(String consoleInput){
        ArrayList<String> keys = new ArrayList<>();
        keys.add("Q");
        keys.add("B");
        keys.add("N");
        keys.add("R");
        if(consoleInput.length() > 4 && consoleInput.length() < 7) {
            if (consoleInput.length() == 6) {
                if (!keys.contains(consoleInput.substring(5, 6))) {//NOPMD a collapse of the statement would cause an unwanted 'true' return of the method
                    // key reached R and the input still doesn't contain a char from keys
                    return true;
                }
            }
            if (consoleInput.charAt(2) == '-') {
                return !Label.contains(consoleInput.substring(0, 2)) || !Label.contains(consoleInput.substring(3, 5));
            } else {
                return true;
            }
        } else {
            return true;
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
     * Prints the last statement when a game is lost or a draw is reached in either english or german
     * depending on the currently selected language.
     *
     * @param currentGame The current state of the game.
     */
    public static void finalWords(Game currentGame){
        if (currentGame.getLanguage() == Language.English) {
            if (currentGame.currentPlayer.isLoser()) {
                System.out.println(currentGame.currentPlayer.getColour() + " has lost!");
                currentGame.currentPlayer = currentGame.currentPlayer == currentGame.playerWhite
                        ? currentGame.playerBlack : currentGame.playerWhite;
                System.out.println("The Winner is " + currentGame.currentPlayer.getColour() + "!\n");
            } else if (currentGame.isDrawn()) {
                System.out.println("The game ended in a draw!\n");
            }
        } else {
            if (currentGame.currentPlayer.isLoser()) {
                System.out.println(getGermanColourName(currentGame) + " hat verloren!");
                currentGame.currentPlayer = currentGame.currentPlayer == currentGame.playerWhite
                        ? currentGame.playerBlack : currentGame.playerWhite;
                System.out.println("Die Partie gewonnen hat " + getGermanColourName(currentGame) + "!\n");
            } else if (currentGame.isDrawn()) {
                System.out.println("Die Partie endet in einem Unentschieden!\n");
            }
        }
        Game newGame = new Game();
        playGame(newGame);
    }


    private static Game cliLoad(){
        System.out.println("Select Save-Game you want to load by entering the number:");
        List<String> saves = new ArrayList<>();
        File f = new File("src/main/resources/saves");
        String[] fileArray = f.list();
        assert fileArray != null;
        if(fileArray.length != 0) {
            Collections.addAll(saves, fileArray);
            int counter = 0;
            for (String i : saves) {
                System.out.println(counter + ") " + i);
                counter++;
            }
        }
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        if(choice > -1 && choice < saves.size()){
            File loadingFile = new File("src/main/resources/saves/" + saves.get(choice));
            return LoadGame.loadFile(loadingFile);
        }
        else{
            System.out.println("It exist no saved game for chosen number.");
            return null;
        }
    }

}

