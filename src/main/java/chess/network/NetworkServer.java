package chess.network;

import chess.game.Game;
import chess.game.Label;
import chess.game.Square;
import chess.pieces.Piece;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import com.chess.*;

public class NetworkServer {

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

            boolean isWhite = false;
            ChooseColor chooseColor = new ChooseColor(isWhite ? "white" : "black");
            System.out.println("Sending ChooseColor message: " + chooseColor);
            outputStream.writeObject(chooseColor);

            if (isWhite)
            {
                processLocalPlayer(game, inputStream, outputStream);
            }

            while(true)
            {
                processClientPlayer(game, inputStream, outputStream);
                printChessBoard(game);
                boolean isEndGame = game.isCheckMate() || game.isADraw() || game.currentPlayer.isLoser();

                if (isEndGame)
                {
                    sendEndGameToClient(outputStream, "todo");
                    break;
                }

                processLocalPlayer(game, inputStream, outputStream);
                printChessBoard(game);
                isEndGame = game.isCheckMate() || game.isADraw() || game.currentPlayer.isLoser();

                if (isEndGame)
                {
                    sendEndGameToClient(outputStream, "todo");
                    break;
                }
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
            System.out.println("Waiting for client move or give up...");

            Object moveOrGiveUp = inputStream.readObject();

            if (moveOrGiveUp instanceof GiveUp)
            {
                System.out.println("Received GiveUp message");
                game.currentPlayer.setLoser(true);
                break;
            }

            Move move = (Move) moveOrGiveUp;
            System.out.println("Received client move: " + move);
            String inputData = move.from + "-" + move.to;

            if (!checkMoveSyntax(inputData))
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

            if (inputData.equals("giveUp"))
            {
                game.currentPlayer.setLoser(true);
                break;
            }

            if (!checkMoveSyntax(inputData))
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
            sendMakeMoveToClient(outputStream);

            break;
        }
    }

    private static void sendEndGameToClient(ObjectOutputStream outputStream, String reason) throws IOException {
        EndGame endGame = new EndGame(reason);
        System.out.println("Sending EndGame message: " + endGame);
        outputStream.writeObject(endGame);
    }

    private static void sendMakeMoveToClient(ObjectOutputStream outputStream) throws IOException {
        System.out.println("Sending make move");
        outputStream.writeObject(new MakeMove());
    }

    private static void sendMoveToClient(ObjectOutputStream outputStream, Move move) throws IOException {
        System.out.println("Sending move: " + move);
        outputStream.writeObject(move);
    }

    private static void sendMoveStatusToClient(ObjectOutputStream outputStream, MoveStatus status) throws IOException {
        System.out.println("Sending move status: " + status);
        outputStream.writeObject(status);
    }

    private static String getInputFromCLI () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Move or Command: ");
        return scanner.nextLine();
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

