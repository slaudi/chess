
package chess.network;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkClient {

    static Socket socket = null;
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


    public static String getMoveFromServer(Socket inSocket) {
        try {
            inputStreamReader = new InputStreamReader(inSocket.getInputStream());
            System.out.println("Waiting for server move...");
            bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void sendMoveToServer(String move,Socket outSocket){
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
