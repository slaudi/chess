package chess.network;

import chess.cli.Cli;
import chess.game.*;
import chess.pieces.Piece;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.chess.*;
import com.chess.Move;

/**
 * This class handles the input and output stream of a network game.
 */
public class Network {

    /**
     * Method handles the network game.
     *
     * @param game The new Game.
     */
    public static void startNetworkGame (Game game) {

        // TODO: refactor exceptions handling

        // TODO: wir schicken und empfangen nur die Moves als String: "a2-a4", alles andere wird nur lokal geregelt:
        //  wenn uns ein String geschickt wird, wurde der schon von der anderen Seite geprüft, wir müssen also keine Antwort
        //  zurück schicken (wie "!Move not allowed!" oder "WHITE is in check!" oder ähnliches) und auch andere commands
        //  wie "beaten" oder Sprachauswahl müssen wir den anderen nicht schicken
        try {
            ServerSocket server = new ServerSocket(9876);

            System.out.println("Waiting for client...");
            Socket socket = server.accept();
            System.out.println("Client connected!");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());


            while (!game.isCheckMate() && !game.isADraw()) {
                if (game.currentPlayer.getColour() == Colour.WHITE) {
                    processClientPlayer(game, inputStream, outputStream);
                } else {
                    processLocalPlayer(game, inputStream, outputStream);
                }

                Cli.toConsole(game);
            }

            Cli.finalWords(game);
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private static void processClientPlayer(Game game, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException, ClassNotFoundException {
        while(true) {
            String inputData = getInputFromClient(inputStream);

            if (Cli.checkForCommand(inputData, game)){
                continue;
            }

            if (Cli.isNotValidMove(inputData)) {
                sendMoveStatusToClient(outputStream, new MoveStatus(false, "Invalid input data"));
                continue;
            }

            Square startSquare = game.chessBoard.getStartSquareFromInput(inputData);
            Square finalSquare = game.chessBoard.getFinalSquareFromInput(inputData);
            char key = game.chessBoard.getPromotionKey(inputData);

            if (!game.isMoveAllowed(startSquare.getOccupiedBy(), finalSquare)) {
                sendMoveStatusToClient(outputStream, new MoveStatus(false, getMoveAllowedErrorMessage(startSquare, finalSquare, game)));
                continue;
            }

            if (!game.processMove(startSquare, finalSquare, key)) {
                // if move puts King in check
                sendMoveStatusToClient(outputStream, new MoveStatus(false, game.currentPlayer.getColour() + " is in check!"));
                continue;
            }

            sendMoveStatusToClient(outputStream, new MoveStatus(true, ""));
            break;
        }
    }

    private static void processLocalPlayer(Game game, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException {
        while(true) {
            String inputData = getInputFromCLI();

            if (Cli.checkForCommand(inputData, game)){
                continue;
            }

            if (Cli.isNotValidMove(inputData)) {
                System.out.println("Invalid input data");
                continue;
            }

            Square startSquare = game.chessBoard.getStartSquareFromInput(inputData);
            Piece selectedPiece = startSquare.getOccupiedBy();
            Square finalSquare = game.chessBoard.getFinalSquareFromInput(inputData);
            char key = game.chessBoard.getPromotionKey(inputData);

            if (!game.isMoveAllowed(startSquare.getOccupiedBy(), finalSquare)) {
                Cli.generateAnswer(selectedPiece,finalSquare,game);
                continue;
            }

            if (!game.processMove(startSquare, finalSquare, key)) {
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


}

