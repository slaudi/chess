package chess.network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkGame {

    static Socket socket = null;
    static ServerSocket server = null;
    static InputStreamReader inputStreamReader = null;
    static OutputStreamWriter outputStreamWriter = null;
    static BufferedReader bufferedReader = null;
    static BufferedWriter bufferedWriter = null;

    // Sends ping request to a provided IP address
    public static boolean sendPingRequest(String ipAddress) {
        try {
            InetAddress enemy = InetAddress.getByName(ipAddress);
            return enemy.isReachable(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Socket startClient() {
        try {
            socket = new Socket("127.0.0.1", 9876);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static Socket startServer(String ipAddress){
        try {
            if (ipAddress != null && !ipAddress.isEmpty())
                server = new ServerSocket(0, 1, InetAddress.getByName(ipAddress));
            else
                server = new ServerSocket(0, 1, InetAddress.getLocalHost());
            server = new ServerSocket(9876);
            Socket client = server.accept();
            client.getRemoteSocketAddress();
            return client;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String receiveMove(Socket inSocket) {
        try {
            inputStreamReader = new InputStreamReader(inSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void sendMove(String move,Socket outSocket){
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
