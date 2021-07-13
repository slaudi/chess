package chess.network;

import chess.game.Game;
import chess.game.Square;

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
            return new Socket("127.0.0.1", 4999);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Method starts the server by creating a new server socket depending on the given IP address
     * which accepts an ingoing request from a client and creates a socket for the network.
     *
     * @return Socket The created socket to handle the network connection.
     */
    public static Socket startServer(){
        try {
            ServerSocket server = new ServerSocket(4999);
            Socket socket = server.accept();
            socket.getRemoteSocketAddress();
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * Makes the move received over the network connection.
     *
     * @param game              The current state of the game.
     * @param connectionSocket  The socket handling the network connection.
     */
    public static void makeNetworkMove(Game game, Socket connectionSocket){
        String enemyInput = receiveMove(connectionSocket);
        assert enemyInput != null;
        Square startSquare = game.chessBoard.getStartSquareFromInput(enemyInput);
        Square finalSquare = game.chessBoard.getFinalSquareFromInput(enemyInput);
        char key = game.chessBoard.getPromotionKey(enemyInput);
        game.processMove(startSquare, finalSquare, key);
    }


    /**
     * Method which reads an incoming message.
     *
     * @param inSocket The socket handling the network connection.
     * @return String The received message as a String.
     */
    public static String receiveMove(Socket inSocket) {
        try {
            InputStream in = inSocket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(in);
            return dataInputStream.readUTF();
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
            OutputStream out = outSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeUTF(move);
            dataOutputStream.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
