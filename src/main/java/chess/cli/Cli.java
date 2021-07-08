package chess.cli;

import chess.engine.Engine;
import chess.game.*;
import chess.game.Label;
import chess.network.NetworkClient;
import chess.network.NetworkServer;
import chess.pieces.Piece;
import chess.savegame.LoadGame;
import chess.savegame.SaveGame;

import java.io.*;
import java.net.Socket;
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
    private static String  colour;
    public static ObjectOutputStream outStream;
    public static ObjectInputStream inStream;


    /**
     * The entry point of the CLI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) throws IOException {
        Game currentGame = new Game();
        playGame(currentGame);
    }

    private static void playGame(Game currentGame) throws IOException {
        checkForModus(currentGame);

        //checks if current Player has lost or if game is a draw
        HelperClass.finalWords(currentGame);
        HelperClass.languageOutput("Do you want to start a new game?    y/n",
                "Möchtest du ein neues Spiel starten?   y/n",currentGame);
        String input = new Scanner(System.in).nextLine();
        if (input.equals("y")) {
            Game newGame = new Game();
            playGame(newGame);
        } else {
            System.exit(0);
        }
    }



    private static void checkForModus(Game currentGame) throws IOException {
        HelperClass.languageOutput("Do you want to play a network or a local game?  network/local",
                "Möchtest du ein Netzwerk-Spiel oder ein lokales Spiel starten? network/local", currentGame);
        String answer;
        do {
            answer = HelperClass.getInput(currentGame);
            checkForCommand(answer, currentGame);
            if (answer.equals("network")) {
                startNetworkGame(currentGame);
            } else if (answer.equals("local")) {
                startLocalGame(currentGame);
            }
        } while (!(answer.equals("network") || answer.equals("local")));
    }

    private static void startNetworkGame(Game currentGame) throws IOException {
        HelperClass.languageOutput("Enter IP address:","Gib IP Adresse ein:",currentGame);
        String ipAddress = new Scanner(System.in).nextLine();
        if (ipAddress.equals("0")){
            Socket connectionSocket = NetworkServer.startServer();
            currentGame.setNetworkServer(true);
            HelperClass.toConsole(currentGame);
            runNetworkGame(currentGame, connectionSocket);

        } else {
            // IP Address != 0
            HelperClass.languageOutput("Sending Ping Request to " + ipAddress,
                    "Sende Ping-Anfrage an" + ipAddress,currentGame);
            if (NetworkClient.sendPingRequest(ipAddress)){
                Socket connectionSocket = NetworkClient.startClient();
                currentGame.setNetworkClient(true);
                HelperClass.toConsole(currentGame);
                runNetworkGame(currentGame, connectionSocket);
            } else {
                // couldn't connect
                HelperClass.languageOutput("Sorry ! We can't reach to this host.",
                        "Sorry! Wir können diesen Host nicht erreichen.",currentGame);
                startNetworkGame(currentGame);
            }
        }
    }

    private static void runNetworkGame(Game currentGame, Socket testSocket) throws IOException {
        System.out.println("geladen");
        //ObjectOutputStream outStream = NetworkServer.generateOutputStream(testSocket);
        //ObjectOutputStream outStream;
        //ObjectInputStream inStream = NetworkServer.generateInputStream(testSocket);
        //ObjectInputStream inStream;
        System.out.println("geladen 3");
        if(currentGame.freshGame){
            if (currentGame.isNetworkServer()){
                currentGame.setUserColour(Colour.BLACK);
                //ObjectOutputStream outStream = NetworkServer.generateOutputStream(testSocket);
                ObjectOutputStream outStream = new ObjectOutputStream(testSocket.getOutputStream());
                //ObjectInputStream inStream = NetworkServer.generateInputStream(testSocket);
                ObjectInputStream inStream = new ObjectInputStream(testSocket.getInputStream());
                System.out.println("geladen 3");
                NetworkServer.sendMoveToClient("Server started",testSocket);
                System.out.println("geladen2");
            } else {
                currentGame.setUserColour(Colour.WHITE);
                //ObjectInputStream inStream = NetworkServer.generateInputStream(testSocket);
                ObjectInputStream inStream = new ObjectInputStream(testSocket.getInputStream());
                //ObjectOutputStream outStream = NetworkServer.generateOutputStream(testSocket);
                ObjectOutputStream outStream = new ObjectOutputStream(testSocket.getOutputStream());
                NetworkClient.sendMoveToServer("Client started",testSocket);
            }
            currentGame.freshGame = false;
        }
        while (!currentGame.isCheckMate() && !currentGame.isADraw() && !currentGame.currentPlayer.isLoser()) {
            colour = currentGame.currentPlayer.getColour().toString();
            if (currentGame.getLanguage() == Language.German) {
                moveNotAllowed = "!Zug nicht erlaubt\n";
                invalidMove = "!Keine gültige Eingabe\n";
                nowPlaying = " ist am Zug";
                check = " befindet sich im Schach!";
                colour = HelperClass.getGermanColourName(currentGame);
            }

            if (currentGame.currentPlayer.isInCheck()) {
                System.out.println(colour + check);
            }
            System.out.println(colour + nowPlaying);
            if (currentGame.getUserColour() == currentGame.currentPlayer.getColour()) {
                System.out.println(currentGame.currentPlayer.getColour());
                if (currentGame.isNetworkServer()) {
                    NetworkServer.sendMoveToClient("server",testSocket);
                    String userInput = HelperClass.getInput(currentGame);
                    if (checkForCommand(userInput, currentGame)) {
                        continue;
                    }
                    if (!canPieceMove(userInput, currentGame)) {
                        continue;
                    }
                    NetworkServer.sendMoveToClient(userInput,testSocket);
                } else {
                    NetworkClient.sendMoveToServer("client",testSocket);
                    String userInput = HelperClass.getInput(currentGame);
                    if (checkForCommand(userInput, currentGame)) {
                        continue;
                    }
                    if (!canPieceMove(userInput, currentGame)) {
                        continue;
                    }
                    NetworkClient.sendMoveToServer(userInput,testSocket);
                }
            } else {
                String enemyInput;
                if (currentGame.isNetworkServer()) {
                    enemyInput = NetworkServer.getMoveFromClient(testSocket);
                } else {
                    enemyInput = NetworkClient.getMoveFromServer(testSocket);
                }
                canPieceMove(enemyInput, currentGame);
            }
            HelperClass.toConsole(currentGame);
        }
    }


    private static void startLocalGame(Game currentGame) throws IOException {
        HelperClass.languageOutput("Do you want to play against a person or an AI?  person/ai",
                "Möchtest du gegen einen Menschen oder eine KI spielen? person/ai", currentGame);
        String answer;
        do {
            answer = HelperClass.getInput(currentGame);
            checkForCommand(answer, currentGame);
            if (answer.equals("ai")) {
                HelperClass.languageOutput("Starting new Game against AI.","Starte ein neues Spiel gegen die KI", currentGame);
                currentGame.currentPlayer = currentGame.playerWhite;
                currentGame.beatenPieces.clear();
                currentGame.moveHistory.clear();
                currentGame.setArtificialEnemy(true);
                HelperClass.toConsole(currentGame);
            } else if (answer.equals("person")) {
                HelperClass.languageOutput("Starting new Game against another person.",
                        "Starte ein neues Spiel gegen eine andere Person", currentGame);
                HelperClass.toConsole(currentGame);
            }
        } while (!(answer.equals("ai") || answer.equals("person")));
        runLocalGame(currentGame);
    }


    private static void runLocalGame(Game currentGame) throws IOException {
        while (!currentGame.isCheckMate() && !currentGame.isADraw() && !currentGame.currentPlayer.isLoser()) {
            colour = currentGame.currentPlayer.getColour().toString();
            if (currentGame.getLanguage() == Language.German) {
                moveNotAllowed = "!Zug nicht erlaubt\n";
                invalidMove = "!Keine gültige Eingabe\n";
                nowPlaying = " ist am Zug";
                check = " befindet sich im Schach!";
                colour = HelperClass.getGermanColourName(currentGame);
            }

            if (currentGame.currentPlayer.isInCheck()) {
                System.out.println(colour + check);
            }
            System.out.println(colour + nowPlaying);

            String userInput = HelperClass.getInput(currentGame);

            if(checkForCommand(userInput, currentGame)){
                // Input is a command, not a Move
                continue;
            }
            // to keep the game running
            if (!canPieceMove(userInput, currentGame)) {
                continue;
            }
            HelperClass.toConsole(currentGame);
        }
    }


    private static boolean canPieceMove(String userInput, Game currentGame) {

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
            HelperClass.generateAnswer(selectedPiece, finalSquare, currentGame);
            return false;
        }
    }


    /**
     * Checks the input given by player for other commands than a move and performs the necessary action.
     *
     * @param userInput     The input of the player as a String.
     * @param currentGame   The current status of the game.
     * @return boolean Returns 'True' if a valid command was given.
     */
    public static boolean checkForCommand(String userInput, Game currentGame) throws IOException {//NOPMD - need to check for every command available
        switch (userInput) {
            case "beaten":
                System.out.println(currentGame.beatenPieces);
                return true;
            case "english":
                currentGame.setLanguage(Language.English);
                System.out.println("You changed the language to English.");
                return true;
            case "deutsch":
                currentGame.setLanguage(Language.German);
                System.out.println("Du hast die Sprache zu Deutsch geändert.");
                return true;
            case "giveUp":
                HelperClass.languageOutput(currentGame.currentPlayer.getColour() + " gave up!",
                        HelperClass.getGermanColourName(currentGame) + " hat aufgegeben!", currentGame);
                currentGame.currentPlayer.setLoser(true);
                return true;
            case "save":
                SaveGame.save(currentGame);
                HelperClass.languageOutput("You saved the current stage of the game!",
                        "Du hast den aktuellen Spielstand gespeichert!", currentGame);
                return true;
            case "load":
                cliLoad(currentGame);
                return true;
            case "newGame":
                Game newGame = new Game();
                newGame.setLanguage(currentGame.getLanguage());
                playGame(newGame);
                break;
            case "help":
                HelperClass.openPDF(currentGame);
                return true;
            case "quit":
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
                if (!keys.contains(consoleInput.substring(5, 6))) {//NOPMD - a collapse of the statement would cause an unwanted 'false' return of the method
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


    private static void cliLoad(Game currentGame) throws IOException {
        HelperClass.languageOutput("Select a saved Game you want to load by entering the number:",
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
        String input = HelperClass.checkForInt(currentGame);
        int choice = Integer.parseInt(input);
        Game tempGame = null;
        if(choice > -1 && choice < saves.size()){
            File loadingFile = new File("src/main/resources/saves/" + saves.get(choice));
            tempGame = LoadGame.loadFile(loadingFile);
        } else {
            HelperClass.languageOutput("No saved game exists for chosen number.\n",
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
        HelperClass.toConsole(currentGame);
    }

}

