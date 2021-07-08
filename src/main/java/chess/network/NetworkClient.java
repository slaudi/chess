
package chess.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import chess.cli.HelperClass;
import chess.game.Board;
import chess.game.Game;
import chess.game.Label;
import chess.game.Square;
import com.chess.*;

public class NetworkClient {

    static boolean gameEnd = false;
    static boolean isServerWhite;
    static Socket socket;

    // Sends ping request to a provided IP address
    public static boolean sendPingRequest(String ipAddress, Game game) {
        try {
            InetAddress enemy = InetAddress.getByName(ipAddress);
            if (enemy.isReachable(5000)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    public static Socket startClient() {
        try {
            socket = new Socket("127.0.0.1", 9876);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            //ChooseColor chooseColor = (ChooseColor) inputStream.readObject();
            //isServerWhite = chooseColor.color.equals("white");

        } catch (Exception e) {
            System.out.println(e);
        }
        return socket;
    }

 /*   public static String runClient(Game game){

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            if(isServerWhite) {
                return processServerPlayer(inputStream, game.chessBoard);
            }

            //if (gameEnd) return;

            while(true) {
                processLocalPlayer(outputStream, inputStream, game.chessBoard);
                if (gameEnd) return;

                processServerPlayer(inputStream, game.chessBoard);
                if (gameEnd) return;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String processServerPlayer(ObjectInputStream inputStream, Board board) throws IOException, ClassNotFoundException {
        System.out.println("Waiting for server move or end game message...");
        Object moveOrEndGame = inputStream.readObject();
        return moveOrEndGame.toString();

        if (moveOrEndGame instanceof EndGame) {
            System.out.println("Received EndGame: " + moveOrEndGame);
            gameEnd = true;
            return;
        }

        Move serverMove = (Move) moveOrEndGame;
        System.out.println("Received server move: " + serverMove);
        movePieces(serverMove.from  + '-' + serverMove.to, board);
        printChessBoard(board);

        System.out.println("Waiting for make move or end game message...");
        Object makeMoveOrEndGame = inputStream.readObject();

        if (makeMoveOrEndGame instanceof EndGame) {
            System.out.println("Received EndGame: " + moveOrEndGame);
            gameEnd = true;
            return;
        }

        System.out.println("Received make move message");*//*

    }

    private static void processLocalPlayer(ObjectOutputStream outputStream, ObjectInputStream inputStream, Board board) throws IOException, ClassNotFoundException {
        while (true) {
            String inputData = getInputFromCLI();

            if (inputData.equals("giveUp"))
            {
                System.out.println("Sending GiveUp...");
                outputStream.writeObject(new GiveUp());
                System.out.println("Waiting server for EndGame message...");
                Object endGame = inputStream.readObject();
                System.out.println("Received EndGame: " + endGame);
                gameEnd = true;
                break;
            }

            if (!checkMoveSyntax(inputData)) {
                System.out.println("Invalid input data");
                continue;
            }

            Move move = convertToProtocolMove(inputData);
            System.out.println("Sending our move: " + move);
            outputStream.writeObject(move);
            System.out.println("Waiting server for move status...");
            MoveStatus moveStatus = (MoveStatus) inputStream.readObject();
            System.out.println("Received move status: " + moveStatus);

            if (!moveStatus.valid) {
                System.out.println("Move status was invalid!");
                continue;
            }

            System.out.println("Move status was valid, leaving loop");

            movePieces(inputData, board);
            printChessBoard(board);
            break;
        }
    }

    private static void movePieces(String inputData, Board board)
    {
        Square fromSquare = board.getStartSquareFromInput(inputData);
        Square toSquare = board.getFinalSquareFromInput(inputData);

        toSquare.setOccupiedBy(fromSquare.getOccupiedBy());
        fromSquare.setOccupiedBy(null);
    }

    private static String getInputFromCLI () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Move or Command: ");
        return scanner.nextLine();
    }

    public static Move convertToProtocolMove(String userInput)
    {
        String[] tokens;
        tokens = userInput.split("-");
        return new Move(tokens[0], tokens[1], ' ');
    }

    // TODO: add support for commands
    private static boolean checkForCommand(String userInput, chess.game.Game currentGame){
        if (userInput.equals("beaten")) {
            System.out.println(currentGame.beatenPieces);
            return true;
        }
        if (userInput.equals("giveUp")) {
            System.out.println(currentGame.currentPlayer.getColour() + " gave up!");
            currentGame.currentPlayer.setLoser(true);
            return true;
        }
        return false;
    }

    static boolean checkMoveSyntax(String input){
        if(input.length() < 5 && input.length() > 6) {
            return false;
        }

        if (input.length() == 6) {
            return Arrays.asList("Q", "B", "N", "R").contains(input.substring(5, 6));
        }

        if (input.charAt(2) != '-') {
            return false;
        }

        return Label.contains(input.substring(0, 2)) || Label.contains(input.substring(3, 5));
    }

    public static void printChessBoard(Board board){
        for (int y = 0; y < board.getHeight(); y++){
            System.out.print(8-y);
            for (int x = 0; x < board.getWidth(); x++){
                if (board.getBoard()[x][y].getOccupiedBy() != null){
                    System.out.print(" " + board.getBoard()[x][y].getOccupiedBy().toString());
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("  a b c d e f g h");
    }
            */

}