package chess.game;

import chess.pieces.Piece;
import chess.game.Game;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

/**
 * Move class to calculate and save the movement of a Chess-Piece.
 */
public class Move {
    public Square startSquare;
    public Square finalSquare;
    public Piece movingPiece;

    /**
     * Constructor of Move-Class.
     *
     * @param start The Square the Movement of Chess-Piece starts at.
     * @param finish The Square the Movement goes to.
     */
    public Move(Square start, Square finish) {
        this.startSquare = start;
        this.finalSquare = finish;
        this.movingPiece = start.occupiedBy;
    }

    /**
     * Checks if Console Input is a syntactical correct Move.
     *
     * @param consoleInput Input of active Player as a String.
     *
     * @return boolean
     */
    public static boolean isValidMove(String consoleInput){
        if(consoleInput.length() > 4) {
            if (consoleInput.charAt(2) == '-') {
                return Label.contains(consoleInput.substring(0, 2)) && Label.contains(consoleInput.substring(3, 5));
            } else return false;
        } else return false;
    }


    public boolean isAllowedMove(Board board) {
        return (this.movingPiece.isAllowedPath(this.finalSquare) && isPathEmpty(this.movingPiece.getType(),this.movingPiece.getSquare(),this.finalSquare, board ) );
    }

    public static boolean isPathEmpty(Type type, Square start, Square end, Board board) {
        Square[][] path = new Square[8][8];
        boolean emptyPath = true;
        for(int i = 0; i < path.length; i++) {
            if (path[0][i].occupiedBy == null){
                emptyPath = false;
                break;
            }
        }
        return emptyPath;
    }


    /**
     * Generates Path if Piece moves more than one Square
     * @param type Type of Piece
     * @param start Start-Square of Movement
     * @param end End-Square of Movement
     * @param board Board of current game
     * @return Path of Moving Piece
     */
    public static ArrayList<Square> generatePath(Type type, Square start, Square end, Board board) {
        ArrayList<Square> path = new ArrayList<>();
        if (type == Type.QUEEN) {
            if (start.x - end.x == 0) {                                 //vertical move
                if (Math.abs(start.y - end.y) > 1) {                    //Qheen moves more than one Square
                    int from = Math.min(start.y, end.y);
                    for (int i = 1; i < Math.abs(start.y - end.y); i++) {
                        path.add(board.board[start.x][from + i]);
                    }
                }
            } else if (start.y - end.y == 0){                           //horizontal move
                if (Math.abs(start.x - end.x) > 1) {                    //Queen moves more than one Square
                    int from = Math.min(start.x, end.x);
                    for (int i = 1; i < Math.abs(start.x - end.x); i++) {
                        path.add(board.board[from + i][start.y]);
                    }
                }
            }
            else {
                int diffX = end.x - start.x;                                // if positive: Queen moves from up to down
                int diffY = end.y - start.y;                                // if positive: Queen moves from left to right
                int dirX = diffX / Math.abs(diffX);                         // if positive: Queen moves from up to down
                int dirY = diffY / Math.abs(diffY);                         // if positive: Queen moves from left to right
                if (Math.abs(diffX) > 1){                                   // Queen moves more than one Square
                    for (int i = 1; i < Math.abs(diffX); i++){
                        path.add(board.board[start.x + i * dirX][start.y + i * dirY]);
                    }
                }
            }


        } else if (type == Type.BISHOP) {
            int diffX = end.x - start.x;                                // if positive: Bishop moves from up to down
            int diffY = end.y - start.y;                                // if positive: Bishop moves from left to right
            int dirX = diffX / Math.abs(diffX);                         // if positive: Bishop moves from up to down
            int dirY = diffY / Math.abs(diffY);                         // if positive: Bishop moves from left to right
            if (Math.abs(diffX) > 1){                                   // Bishop moves more than one Square
                for (int i = 1; i < Math.abs(diffX); i++){
                    path.add(board.board[start.x + i * dirX][start.y + i * dirY]);
                }
            }
        } else if (type == Type.ROOK) {
            if (start.x - end.x == 0) {                                 //vertical move
                if (Math.abs(start.y - end.y) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(start.y, end.y);
                    for (int i = 1; i < Math.abs(start.y - end.y); i++) {
                        path.add(board.board[start.x][from + i]);
                    }
                }
            } else {                                                    //horizontal move
                if (Math.abs(start.x - end.x) > 1) {                    //Rook moves more than one Square
                    int from = Math.min(start.x, end.x);
                    for (int i = 1; i < Math.abs(start.x - end.x); i++) {
                        path.add(board.board[from + i][start.y]);
                    }
                }
            }
        }
        return path;
    }

    public Board doMove (Piece piece, Board board){
        if(piece.getType() == Type.KING){
            if(piece.checkSafeSquare(this.finalSquare,board, piece.getColour())){
                piece.setMoved(true);
                piece.setSquare(finalSquare);
                board.board[finalSquare.x][finalSquare.y].occupiedBy = piece;
                board.board[startSquare.x][startSquare.y].occupiedBy = null;
            }
        }
        else {
            piece.setMoved(true);
            piece.setSquare(finalSquare);
            board.board[finalSquare.x][finalSquare.y].occupiedBy = piece;
            board.board[startSquare.x][startSquare.y].occupiedBy = null;
        }
        return board;
    }
    public void undoMove (Stack<Move> history){
        Move actualMove = history.pop();
        actualMove.finalSquare.
    }

}
