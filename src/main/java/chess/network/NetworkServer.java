package chess.network;

import chess.cli.HelperClass;
import chess.game.Colour;
import chess.game.Game;
import chess.game.Label;
import chess.game.Square;
import chess.pieces.Piece;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {

    static Socket socket;
    static InputStreamReader inputStreamReader = null;
    static OutputStreamWriter outputStreamWriter = null;
    static BufferedReader bufferedReader = null;
    static BufferedWriter bufferedWriter = null;

    public static Socket startServer(){

        // TODO: refactor exceptions handling
        try {
            ServerSocket server = new ServerSocket(9876);

            System.out.println("Waiting for client...");
            socket = server.accept();
            socket.getRemoteSocketAddress();
            System.out.println("Client connected!");


            return socket;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getMoveFromClient(Socket inSocket) {
        try {
            inputStreamReader = new InputStreamReader(inSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            System.out.println("Waiting for client move or give up.2..");
            String input = bufferedReader.readLine();
            System.out.println(input);
            //String testo = in.readUTF();
            //String outString = br.readLine();
            System.out.println("vorbei");
            return input;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("inputFehler");
            return null;
        }
    }



    public static void sendMoveToClient(String move, Socket outSocket){
        System.out.println("Sending move: " + move);
        try {

            outputStreamWriter = new OutputStreamWriter(outSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(move);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}

