package chess.cli;

import chess.engine.Engine;
import chess.game.*;
import chess.network.NetworkGame;
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
 * Starting point of the command line interface.
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

    private static void playGame(Game currentGame) {
        startChessGame(currentGame);

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


    private static void startChessGame(Game currentGame){
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

    private static void startNetworkGame(Game currentGame) {
        HelperClass.languageOutput("Enter IP address:","Gib IP Adresse ein:",currentGame);
        String ipAddress = new Scanner(System.in).nextLine();
        if (ipAddress.equals("0")){
            HelperClass.languageOutput("Waiting for client...","Warte auf Client...",currentGame);
            Socket connectionSocket = NetworkGame.startServer();
            HelperClass.languageOutput("Connection successful!","Verbindung hergestellt!",currentGame);
            currentGame.setNetworkServer(true);
            currentGame.setUserColour(Colour.WHITE);
            HelperClass.toConsole(currentGame);
            runNetworkGame(currentGame, connectionSocket);
        } else {
            // IP Address != 0
            HelperClass.languageOutput("Sending Ping Request to " + ipAddress,
                    "Sende Ping-Anfrage an" + ipAddress,currentGame);
            if (NetworkGame.sendPingRequest(ipAddress)){
                Socket connectionSocket = NetworkGame.startClient();
                HelperClass.languageOutput("Connection successful!","Verbindung hergestellt!",currentGame);
                currentGame.setNetworkClient(true);
                currentGame.setUserColour(Colour.BLACK);
                HelperClass.toConsole(currentGame);
                runNetworkGame(currentGame, connectionSocket);
            } else {
                // couldn't connect
                HelperClass.languageOutput("Sorry ! We can't reach this host: " + ipAddress,
                        "Sorry! Wir können diesen Host nicht erreichen: " + ipAddress,currentGame);
                startNetworkGame(currentGame);
            }
        }
    }


    private static void runNetworkGame(Game currentGame, Socket connectionSocket) {
        while (!currentGame.isCheckMate() && !currentGame.isADraw() && !currentGame.currentPlayer.isLoser()) {
            if (currentGame.currentPlayer.isInCheck()) {
                HelperClass.languageOutput(currentGame.currentPlayer.getColour() + " is in check!",
                        HelperClass.getGermanColourName(currentGame) + " befindet sich im Schach!",currentGame);
            }
            HelperClass.languageOutput(currentGame.currentPlayer.getColour() + "'s move",
                    HelperClass.getGermanColourName(currentGame) + " ist am Zug",currentGame);
            if (currentGame.getUserColour() == currentGame.currentPlayer.getColour()) {
                String userInput = HelperClass.getInput(currentGame);
                if (checkForCommand(userInput, currentGame)) {
                    continue;
                }
                if (!canPieceMove(userInput, currentGame)) {
                    continue;
                }
                NetworkGame.sendMove(userInput,connectionSocket);
            } else {
                HelperClass.languageOutput("Waiting for move...","Warte auf Zug...",currentGame);
                NetworkGame.makeNetworkMove(currentGame, connectionSocket);
            }
            HelperClass.toConsole(currentGame);
        }
        try {
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void startLocalGame(Game currentGame) {
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


    private static void runLocalGame(Game currentGame) {
        while (!currentGame.isCheckMate() && !currentGame.isADraw() && !currentGame.currentPlayer.isLoser()) {
            if (currentGame.currentPlayer.isInCheck()) {
                HelperClass.languageOutput(currentGame.currentPlayer.getColour() + " is in check!",
                        HelperClass.getGermanColourName(currentGame) + " befindet sich im Schach!",currentGame);
            }
            HelperClass.languageOutput(currentGame.currentPlayer.getColour() + "'s move",
                    HelperClass.getGermanColourName(currentGame) + " ist am Zug",currentGame);

            String userInput = HelperClass.getInput(currentGame);
            if(checkForCommand(userInput, currentGame)){
                // Input is a command, not a Move
                continue;
            }
            if (!canPieceMove(userInput, currentGame)) {
                continue;
            }
            HelperClass.toConsole(currentGame);
        }
    }


    private static boolean canPieceMove(String userInput, Game currentGame) {
        if (isNotValidMove(userInput)) {
            // validates user-input syntactically
            HelperClass.languageOutput("!Invalid move\n","!Keine gültige Eingabe\n",currentGame);
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
                HelperClass.languageOutput("!Move not allowed\n" + currentGame.currentPlayer.getColour() + " would be in check!\n",
                        "!Zug nicht erlaubt\n" + HelperClass.getGermanColourName(currentGame) + " befände sich im Schach!",currentGame);
                return false;
            }
        } else {
            HelperClass.languageOutput("!Move not allowed\n","!Zug nicht erlaubt\n",currentGame);
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
    private static boolean checkForCommand(String userInput, Game currentGame) {//NOPMD - need to check for every command available
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
                if(!currentGame.isNetworkServer() && !currentGame.isNetworkClient()){
                    SaveGame.save(currentGame);
                    HelperClass.languageOutput("You saved the current stage of the game!",
                            "Du hast den aktuellen Spielstand gespeichert!", currentGame);
                } else {
                    HelperClass.languageOutput("Saving the game while playing a network game is enabled!",
                            "Während eines Netzwerk-Spiels ist Speichern nicht möglich!", currentGame);
                }
                return true;
            case "load":
                if(!currentGame.isNetworkServer() && !currentGame.isNetworkClient()){
                    cliLoad(currentGame);
                } else{
                    HelperClass.languageOutput("Loading a game while playing a network game is enabled!",
                            "Während eines Netzwerk-Spiels ist Laden nicht möglich!", currentGame);
                }
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
    static boolean isNotValidMove(String consoleInput){
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
                return !chess.game.Label.contains(consoleInput.substring(0, 2)) || !Label.contains(consoleInput.substring(3, 5));
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private static void cliLoad(Game currentGame) {
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
        String input = new Scanner(System.in).nextLine();
        checkForCommand(input,currentGame);
        if (!HelperClass.checkForInt(input, currentGame)) {
            cliLoad(currentGame);
        }
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