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
        Game game = new Game();
        playGame(game);
    }

    private static void playGame(Game game) {
        startChessGame(game);

        // output if current Player has lost or if game is a draw
        HelperClass.finalWords(game);
        // starts new game or exits application
        HelperClass.languageOutput("Do you want to start a new game?    y/n",
                "Möchtest du ein neues Spiel starten?   y/n",game);
        String input = new Scanner(System.in).nextLine();
        if (input.equals("y")) {
            Game newGame = new Game();
            playGame(newGame);
        } else {
            System.exit(0);
        }
    }


    private static void startChessGame(Game game){
        HelperClass.languageOutput("Do you want to play a network or a local game?  network/local",
                "Möchtest du ein Netzwerk-Spiel oder ein lokales Spiel starten? network/local", game);
        String answer;
        do {
            answer = HelperClass.getInput(game);
            checkForCommand(answer, game);
            if (answer.equals("network")) {
                startNetworkGame(game);
            } else if (answer.equals("local")) {
                startLocalGame(game);
            }
        } while (!(answer.equals("network") || answer.equals("local")));
    }


    private static void startNetworkGame(Game game) {
        HelperClass.languageOutput("Enter IP address:","Gib IP Adresse ein:",game);
        String ipAddress = new Scanner(System.in).nextLine();
        checkForCommand(ipAddress,game);
        if (ipAddress.equals("0")){
            // IP address = 0 starts server
            HelperClass.languageOutput("Waiting for client...","Warte auf Client...",game);
            Socket connectionSocket = NetworkGame.startServer();
            HelperClass.languageOutput("Connection successful!","Verbindung hergestellt!",game);
            game.setNetworkServer(true);
            game.setUserColour(Colour.WHITE);
            HelperClass.toConsole(game);
            runNetworkGame(game, connectionSocket);
        } else {
            // IP Address != 0 starts client
            HelperClass.languageOutput("Sending Ping Request to " + ipAddress,
                    "Sende Ping-Anfrage an" + ipAddress,game);
            if (NetworkGame.sendPingRequest(ipAddress)){
                Socket connectionSocket = NetworkGame.startClient();
                HelperClass.languageOutput("Connection successful!","Verbindung hergestellt!",game);
                game.setNetworkClient(true);
                game.setUserColour(Colour.BLACK);
                HelperClass.toConsole(game);
                runNetworkGame(game, connectionSocket);
            } else {
                // couldn't connect
                HelperClass.languageOutput("Sorry ! We can't reach this host: " + ipAddress,
                        "Sorry! Wir können diesen Host nicht erreichen: " + ipAddress,game);
                startNetworkGame(game);
            }
        }
    }


    private static void runNetworkGame(Game game, Socket connectionSocket) {
        while (!game.isCheckMate() && !game.isADraw() && !game.currentPlayer.isLoser()) {
            if (game.currentPlayer.isInCheck()) {
                HelperClass.languageOutput(game.currentPlayer.getColour() + " is in check!",
                        HelperClass.getGermanColourName(game) + " befindet sich im Schach!",game);
            }
            HelperClass.languageOutput(game.currentPlayer.getColour() + "'s move",
                    HelperClass.getGermanColourName(game) + " ist am Zug",game);
            if (game.getUserColour() == game.currentPlayer.getColour()) {
                String userInput = HelperClass.getInput(game);
                if (checkForCommand(userInput, game)) {
                    continue;
                }
                if (pieceCannotMove(userInput, game)) {
                    continue;
                }
                NetworkGame.sendMove(userInput,connectionSocket);
            } else {
                HelperClass.languageOutput("Waiting for move...","Warte auf Zug...",game);
                NetworkGame.makeNetworkMove(game, connectionSocket);
            }
            HelperClass.toConsole(game);
        }
        try {
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void startLocalGame(Game game) {
        HelperClass.languageOutput("Do you want to play against a person or an AI?  person/ai",
                "Möchtest du gegen einen Menschen oder eine KI spielen? person/ai", game);
        String answer;
        do {
            answer = HelperClass.getInput(game);
            checkForCommand(answer, game);
            if (answer.equals("ai")) {
                HelperClass.languageOutput("Starting new Game against AI.","Starte ein neues Spiel gegen die KI", game);
                game.currentPlayer = game.playerWhite;
                game.beatenPieces.clear();
                game.moveHistory.clear();
                game.setArtificialEnemy(true);
                HelperClass.toConsole(game);
            } else if (answer.equals("person")) {
                HelperClass.languageOutput("Starting new Game against another person.",
                        "Starte ein neues Spiel gegen eine andere Person", game);
                HelperClass.toConsole(game);
            }
        } while (!(answer.equals("ai") || answer.equals("person")));
        runLocalGame(game);
    }


    private static void runLocalGame(Game game) {
        while (!game.isCheckMate() && !game.isADraw() && !game.currentPlayer.isLoser()) {
            if (game.currentPlayer.isInCheck()) {
                HelperClass.languageOutput(game.currentPlayer.getColour() + " is in check!",
                        HelperClass.getGermanColourName(game) + " befindet sich im Schach!",game);
            }
            HelperClass.languageOutput(game.currentPlayer.getColour() + "'s move",
                    HelperClass.getGermanColourName(game) + " ist am Zug",game);

            String userInput = HelperClass.getInput(game);
            if(checkForCommand(userInput, game)){
                // Input is a command, not a Move
                continue;
            }
            if (pieceCannotMove(userInput, game)) {
                continue;
            }
            HelperClass.toConsole(game);
        }
    }


    private static boolean pieceCannotMove(String userInput, Game game) {
        if (isNotValidMove(userInput)) {
            // validates user-input syntactically
            HelperClass.languageOutput("!Invalid move\n","!Keine gültige Eingabe\n",game);
            return true;
        }
        Piece selectedPiece = game.chessBoard.getMovingPieceFromInput(userInput);
        Square startSquare = game.chessBoard.getStartSquareFromInput(userInput);
        Square finalSquare = game.chessBoard.getFinalSquareFromInput(userInput);
        char key = game.chessBoard.getPromotionKey(userInput);

        if (game.isMoveAllowed(selectedPiece, finalSquare)) {
            // validates user-input semantically
            if (game.processMove(startSquare, finalSquare, key)) {
                System.out.println("!" + userInput);
                if(game.isArtificialEnemy()){
                    makeAIMove(game);
                }
                return false;
            } else {
                // if move puts King in check
                HelperClass.languageOutput("!Move not allowed\n" + game.currentPlayer.getColour() + " would be in check!\n",
                        "!Zug nicht erlaubt\n" + HelperClass.getGermanColourName(game) + " befände sich im Schach!",game);
                return true;
            }
        } else {
            HelperClass.languageOutput("!Move not allowed\n","!Zug nicht erlaubt\n",game);
            HelperClass.generateAnswer(selectedPiece, finalSquare, game);
            return true;
        }
    }


    /**
     * Checks the input given by player for other commands than a move and performs the necessary action.
     *
     * @param userInput     The input of the player as a String.
     * @param game   The current status of the game.
     * @return boolean Returns 'True' if a valid command was given.
     */
    private static boolean checkForCommand(String userInput, Game game) {//NOPMD - need to check for every command available
        switch (userInput) {
            case "beaten":
                System.out.println(game.beatenPieces);
                return true;
            case "english":
                game.setLanguage(Language.English);
                System.out.println("You changed the language to English.");
                return true;
            case "deutsch":
                game.setLanguage(Language.German);
                System.out.println("Du hast die Sprache zu Deutsch geändert.");
                return true;
            case "giveUp":
                HelperClass.languageOutput(game.currentPlayer.getColour() + " gave up!",
                        HelperClass.getGermanColourName(game) + " hat aufgegeben!", game);
                game.currentPlayer.setLoser(true);
                return true;
            case "save":
                if(!game.isNetworkServer() && !game.isNetworkClient()){
                    SaveGame.save(game);
                    HelperClass.languageOutput("You saved the current stage of the game!",
                            "Du hast den aktuellen Spielstand gespeichert!", game);
                } else {
                    HelperClass.languageOutput("Saving the game while playing a network game is enabled!",
                            "Während eines Netzwerk-Spiels ist Speichern nicht möglich!", game);
                }
                return true;
            case "load":
                if(!game.isNetworkServer() && !game.isNetworkClient()){
                    cliLoad(game);
                } else{
                    HelperClass.languageOutput("Loading a game while playing a network game is enabled!",
                            "Während eines Netzwerk-Spiels ist Laden nicht möglich!", game);
                }
                return true;
            case "newGame":
                Game newGame = new Game();
                newGame.setLanguage(game.getLanguage());
                playGame(newGame);
                break;
            case "help":
                HelperClass.openPDF(game);
                return true;
            case "quit":
                System.exit(0);
        }
        return false;
    }


    private static void makeAIMove(Game game){
        Square startSquareEnemy = null;
        Square finalSquareEnemy = null;
        char keyEnemy;
        do {
            Move enemyMove = Engine.nextBestMove(game);
            if (enemyMove == null) {
                game.setDrawn(true);
                break;
            }
            startSquareEnemy = enemyMove.getStartSquare();
            finalSquareEnemy = enemyMove.getFinalSquare();
            keyEnemy = 'Q';
        } while (!game.processMove(startSquareEnemy,finalSquareEnemy,keyEnemy));
        if (game.isDrawn()){
            System.out.println(" ");
        } else {
            assert startSquareEnemy != null;
            System.out.println("!" + startSquareEnemy.getLabel().toString() + "-" + finalSquareEnemy.getLabel().toString() + "\n");
        }
    }



    /**
     * Checks if the console input is a syntactical correct move.
     *
     * @param userInput The console input of the active Player as a String.
     * @return boolean Returns 'true' if the syntax of the input is correct.
     */
    static boolean isNotValidMove(String userInput){
        ArrayList<String> keys = new ArrayList<>();
        keys.add("Q");
        keys.add("B");
        keys.add("N");
        keys.add("R");
        if(userInput.length() > 4 && userInput.length() < 7) {
            if (userInput.length() == 6) {
                if (!keys.contains(userInput.substring(5, 6))) {//NOPMD - a collapse of the statement would cause an unwanted 'false' return of the method
                    // key reached R and the input still doesn't contain a char from keys
                    return true;
                }
            }
            if (userInput.charAt(2) == '-') {
                return !chess.game.Label.contains(userInput.substring(0, 2)) || !Label.contains(userInput.substring(3, 5));
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private static void cliLoad(Game game) {
        HelperClass.languageOutput("Select a saved Game you want to load by entering the number:",
                "Wähle eine Nummer um ein gespeichertes Spiel zu laden:",game);
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
        checkForCommand(input,game);
        if (!HelperClass.checkForInt(input, game)) {
            cliLoad(game);
        }
        int choice = Integer.parseInt(input);
        Game tempGame = null;
        if(choice > -1 && choice < saves.size()){
            File loadingFile = new File("src/main/resources/saves/" + saves.get(choice));
            tempGame = LoadGame.loadFile(loadingFile);
        } else {
            HelperClass.languageOutput("No saved game exists for chosen number.\n",
                    "Es existiert kein gespeichertes Spiel unter der angegebenen Nummer.\n",game);
            cliLoad(game);
        }
        assert tempGame != null;
        game.currentPlayer = tempGame.currentPlayer;
        game.chessBoard = tempGame.chessBoard;
        game.setUserColour(tempGame.getUserColour());
        game.setArtificialEnemy(tempGame.isArtificialEnemy());
        game.beatenPieces = tempGame.beatenPieces;
        game.moveHistory = tempGame.moveHistory;
        game.setLanguage(tempGame.getLanguage());
        HelperClass.toConsole(game);
    }

}