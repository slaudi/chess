package chess.network;

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
        try {
            ServerSocket server = new ServerSocket(9876);

            System.out.println("Waiting for client...");
            socket = server.accept();
            socket.getRemoteSocketAddress();
            System.out.println("Client connected!");
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getMoveFromClient(Socket inSocket) {
        try {
            System.out.println("Waiting for client move...");
            inputStreamReader = new InputStreamReader(inSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
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

