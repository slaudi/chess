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
            socket = server.accept();
            socket.getRemoteSocketAddress();
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getMoveFromClient(Socket inSocket) {
        try {
            inputStreamReader = new InputStreamReader(inSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void sendMoveToClient(String move, Socket outSocket){
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

