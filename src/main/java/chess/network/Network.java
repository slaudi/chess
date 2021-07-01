package chess.network;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Label;
import chess.game.Square;
import chess.pieces.Piece;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import com.chess.*;

public class Network {

    public static void main(String[] args){

        // TODO: refactor exceptions handling
        try {
            ServerSocket server = new ServerSocket(9876);

            System.out.println("Waiting for client...");
            Socket socket = server.accept();
            System.out.println("Client connected!");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            Game game = new Game();

            while (!game.isCheckMate() && !game.isADraw()) {
                if (game.currentPlayer.getColour() == Colour.WHITE) {
                    processClientPlayer(game, inputStream, outputStream);
                } else {
                    processLocalPlayer(game, inputStream, outputStream);
                }

                printChessBoard(game);
            }

            finalWords(game);
        }
        catch (java.io.IOException | java.lang.ClassNotFoundException e)
        {
            System.out.println(e);
        }
    }

    private static void processClientPlayer(Game game, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        while(true)
        {
            String inputData = getInputFromClient(inputStream);

            if (checkInputDataSyntax(inputData))
            {
                sendMoveStatusToClient(outputStream, new MoveStatus(false, "Invalid input data"));
                continue;
            }

            Square startSquare = game.chessBoard.getStartSquareFromInput(inputData);
            Square finalSquare = game.chessBoard.getFinalSquareFromInput(inputData);
            char key = game.chessBoard.getPromotionKey(inputData);

            if (!game.isMoveAllowed(startSquare.getOccupiedBy(), finalSquare))
            {
                sendMoveStatusToClient(outputStream, new MoveStatus(false, getMoveAllowedErrorMessage(startSquare, finalSquare, game)));
                continue;
            }

            if (!game.processMove(startSquare, finalSquare, key))
            {
                // if move puts King in check
                sendMoveStatusToClient(outputStream, new MoveStatus(false, game.currentPlayer.getColour() + " is in check!"));
                continue;
            }

            sendMoveStatusToClient(outputStream, new MoveStatus(true, ""));
            break;
        }
    }

    private static void processLocalPlayer(Game game, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException {
        while(true)
        {
            String inputData = getInputFromCLI();

            if (checkInputDataSyntax(inputData))
            {
                System.out.println("Invalid input data");
                continue;
            }

            Square startSquare = game.chessBoard.getStartSquareFromInput(inputData);
            Square finalSquare = game.chessBoard.getFinalSquareFromInput(inputData);
            char key = game.chessBoard.getPromotionKey(inputData);

            if (!game.isMoveAllowed(startSquare.getOccupiedBy(), finalSquare))
            {
                System.out.println(getMoveAllowedErrorMessage(startSquare, finalSquare, game));
                continue;
            }

            if (!game.processMove(startSquare, finalSquare, key))
            {
                // if move puts King in check
                System.out.println(game.currentPlayer.getColour() + " is in check!");
                continue;
            }

            String[] tokens = inputData.split("-");
            sendMoveToClient(outputStream, new Move(tokens[0], tokens[1], ' '));

            break;
        }
    }

    private static void sendMoveToClient(ObjectOutputStream outputStream, Move move) throws IOException {
        System.out.println("Sending move: " + move);
        outputStream.writeObject(move);
    }

    private static void sendMoveStatusToClient(ObjectOutputStream outputStream, MoveStatus status) throws IOException {
        System.out.println("Sending move status: " + status);
        outputStream.writeObject(status);
    }

    private static String getInputFromClient (ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        System.out.println("Waiting for client move...");
        Move move = (Move) inputStream.readObject();
        System.out.println("Client move received: " + move);
        return move.from + "-" + move.to;
    }

    private static String getInputFromCLI () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Move or Command: ");
        return scanner.nextLine();
    }

    // TODO: add support for commands
    private static boolean checkForCommand(String userInput, Game currentGame){
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

    private static String getMoveAllowedErrorMessage (Square startSquare, Square finalSquare, Game currentGame){
        Piece targetPiece = finalSquare.getOccupiedBy();
        Piece selectedPiece = startSquare.getOccupiedBy();

        if (selectedPiece == null) {
            return "There is no Piece to move!";
        } else if (selectedPiece.getColour() != currentGame.currentPlayer.getColour()) {
            return "This is not your Piece to move!";
        } else if (targetPiece != null && selectedPiece.getSquare() == finalSquare) {
            return "You have to move!";
        } else if (targetPiece != null && targetPiece.getColour() == currentGame.currentPlayer.getColour()) {
            return "You cannot attack your own Piece!";
        }

        return "Something bad happened";
    }

    static boolean checkInputDataSyntax(String input){
        ArrayList<String> keys = new ArrayList<>();
        keys.add("Q");
        keys.add("B");
        keys.add("N");
        keys.add("R");
        if(input.length() > 4 && input.length() < 7) {
            if (input.length() == 6) {
                if (!keys.contains(input.substring(5, 6))) {//NOPMD a collapse of the statement would cause a false 'true' return of the method
                    // key reached R and the input still doesn't contain a char from keys
                    return true;
                }
            }
            if (input.charAt(2) == '-') {
                return !Label.contains(input.substring(0, 2)) || !Label.contains(input.substring(3, 5));
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static void printChessBoard(Game game){
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

    private static void finalWords(Game currentGame) {
        if (currentGame.currentPlayer.isLoser()) {
            System.out.println(currentGame.currentPlayer.getColour() + " has lost!");
            currentGame.currentPlayer = currentGame.currentPlayer == currentGame.playerWhite
                    ? currentGame.playerBlack : currentGame.playerWhite;
            System.out.println("The Winner is " + currentGame.currentPlayer.getColour() + "!");
        } else if (currentGame.isDrawn()) {
            System.out.println("The game ended in a draw!");
        }
    }

}

