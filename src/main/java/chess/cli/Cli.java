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

    private static String moveNotAllowed = "!Move not allowed";
    private static String invalidMove = "!Invalid move";
    private static String nowPlaying = "'s move";
    private static String check = " is in check!";

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
        Scanner scanner = new Scanner(System.in);
        languageOutput("Enter Move or Command: ","Zug oder Kommando: ",currentGame);
        return scanner.nextLine();
    }


    private static void checkForModus(Game currentGame) {
        languageOutput("Do you want to play a network or a local game?  network/local",
                "Möchtest du ein Netzwerk-Spiel oder ein lokales Spiel starten? network/local", currentGame);
        String answer;
        do {
            answer = getInput(currentGame);
            checkForCommand(answer, currentGame);
            if (answer.equals("network")) {
                Network.startNetworkGame(currentGame);
            } else if (answer.equals("local")) {
                startLocalGame(currentGame);
            }
        } while (!(answer.equals("network") || answer.equals("local")));
    }


    private static void startLocalGame(Game currentGame){
        languageOutput("Do you want to play against a person or an AI?  person/ai",
                "Möchtest du gegen einen Menschen oder eine KI spielen? person/ai", currentGame);
        String answer;
        do {
            answer = getInput(currentGame);
            checkForCommand(answer, currentGame);
            if (answer.equals("ai")) {
                languageOutput("Starting new Game against AI.","Starte ein neues Spiel gegen die KI", currentGame);
                currentGame.currentPlayer = currentGame.playerWhite;
                currentGame.beatenPieces.clear();
                currentGame.moveHistory.clear();
                currentGame.setArtificialEnemy(true);
                toConsole(currentGame);
            } else if (answer.equals("person")) {
                languageOutput("Starting new Game against another person.",
                        "Starte ein neues Spiel gegen eine andere Person", currentGame);
                toConsole(currentGame);
            }
        } while (!(answer.equals("ai") || answer.equals("person")));
    }


    private static boolean canPieceMove(Game currentGame) {
        String  colour = currentGame.currentPlayer.getColour().toString();
        if (currentGame.getLanguage() == Language.German) {
            moveNotAllowed = "!Zug nicht erlaubt\n";
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
            languageOutput(currentGame.currentPlayer.getColour()+" gave up!",
                    getGermanColourName(currentGame)+" hat aufgegeben!",currentGame);
            currentGame.currentPlayer.setLoser(true);
            return true;
        }
        if (userInput.equals("save")) {
            SaveGame.save(currentGame);
            languageOutput("You saved the current stage of the game!",
                    "Du hast den aktuellen Spielstand gespeichert!",currentGame);
            return true;
        }
        if (userInput.equals("load")) {
            cliLoad(currentGame);
            return true;
        }
        if (userInput.equals("newGame")){
            Game newGame = new Game();
            newGame.setLanguage(currentGame.getLanguage());
            playGame(newGame);
        }
        if (userInput.equals("help")){
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("Bedienungsanleitung.pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                    languageOutput("Es wurde kein PDF Viewer gefunden!","No PDF viewer found!",currentGame);
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
        Game newGame = new Game();
        playGame(newGame);
    }


    private static void cliLoad(Game currentGame){
        languageOutput("Select a saved Game you want to load by entering the number:",
                "Wähle eine Nummer um ein gespeichertes Spiel zu laden:",currentGame);
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
        String input = scanner.nextLine();
        checkForCommand(input,currentGame);
        int choice = Integer.parseInt(input);
        Game tempGame = null;
        if(choice > -1 && choice < saves.size()){
            File loadingFile = new File("src/main/resources/saves/" + saves.get(choice));
            tempGame = LoadGame.loadFile(loadingFile);
        } else {
            languageOutput("No saved game exists for chosen number.\n",
                    "Es existiert kein gespeichertes Spiel unter der angegebenen Nummer.\n",currentGame);
            cliLoad(currentGame);
        }
        assert tempGame != null;
        currentGame.currentPlayer = tempGame.currentPlayer;
        currentGame.chessBoard = tempGame.chessBoard;
        currentGame.setUserColour(tempGame.getUserColour());
        currentGame.setArtificialEnemy(tempGame.isArtificialEnemy());
        currentGame.beatenPieces = tempGame.beatenPieces;
        currentGame.moveHistory = tempGame.moveHistory;
        currentGame.setLanguage(tempGame.getLanguage());
        toConsole(currentGame);
    }


    private static void languageOutput(String messageEnglish, String messageGerman, Game game){
        if (game.getLanguage() == Language.English) {
            System.out.println(messageEnglish);
        } else {
            System.out.println(messageGerman);
        }
    }

}

