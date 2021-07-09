package chess.network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * The class handles the network connection when playing a network game.
 */
public class NetworkGame {

    /**
     * Sends ping request to a provided IP address.
     *
     * @param ipAddress The IP address as a String given by the user.
     * @return boolean Returns 'true' if the given IP address is reachable.
     */
    public static boolean sendPingRequest(String ipAddress) {
        try {
            InetAddress enemy = InetAddress.getByName(ipAddress);
            return enemy.isReachable(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Method starts the client by creating a local socket.
     *
     * @return Socket The socket which was created to handle the network connection.
     */
    public static Socket startClient() {
        try {
            return new Socket("127.0.0.1", 9876);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Method starts the server by creating a new server socket depending on the given IP address
     * which accepts an ingoing request from a client and a socket for the network.
     *
     * @param ipAddress The IP address as a String given by the user.
     * @return Socket The created socket to handle the network connection.
     */
    public static Socket startServer(String ipAddress){
        try {
            ServerSocket server = new ServerSocket(9876);
            Socket socket = server.accept();
            socket.getRemoteSocketAddress();
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * Method which reads an incoming message.
     *
     * @param inSocket The socket handling the network connection.
     * @return String The received message as a String.
     */
    public static String receiveMove(Socket inSocket) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Method which sends an outgoing message.
     *
     * @param move The message to send as a String.
     * @param outSocket The socket handling the network connection.
     */
    public static void sendMove(String move, Socket outSocket){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outSocket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(move);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
