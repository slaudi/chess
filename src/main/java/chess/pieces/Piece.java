package chess.pieces;

import chess.game.*;

import java.util.ArrayList;

public abstract class Piece {

    Square square;
    Colour colour;
    boolean moved;

    /**
     * Constructor for a Piece
     * @param square the location of the Piece
     * @param colour the Colour object associated with the Piece
     */
    public Piece(Square square, Colour colour) {
        this.square = square;
        this.colour = colour;
        this.moved = false;
    }

    public Square getSquare(){
        return this.square;
    }

    public abstract void setSquare(Square square);

    public abstract Colour getColour();

    public abstract Type getType();

    public abstract boolean getMoved();

    public abstract void setMoved(boolean x);

    /**
     * A function to determine if a Piece is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the Piece on the chess board
     */
    public abstract String toString();

    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    public abstract boolean isAllowedPath(Square finalSquare, Board board);
    // kein Piece darf ziehen, wenn der eigene King im Angriff steht oder dadurch einem Angriff ausgesetzt wird

    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    public abstract boolean isAllowedPath(Square finalSquare);
    // kein Piece darf ziehen, wenn der eigene King im Angriff steht oder dadurch einem Angriff ausgesetzt wird

    /**
     * Draws a path of a move based on the type of the Piece and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final position
     * @return an array of the path
     */
    public abstract Square[][] drawMove(Square finalSquare);

    public abstract boolean isSurroundingSquare(Square square);

    public boolean isPathEmpty(Type type, Square start, Square end, Board board){
        ArrayList<Square> path = Move.generatePath(type,start,end,board);
        if(path.isEmpty()){
            return false;
        }
        else {
            for (int i = 0; i < path.size(); i++) {
                if (path.get(i).occupiedBy != null) {
                    return false;
                }
            }
            return true;
        }
    }
    public boolean finalSquareIsEmpty (Square end, Board board){
        if( board.board[end.x][end.y].occupiedBy == null){
            return true;
        }
        else return false;
    }



    public abstract boolean pawnCanCapture(Square finalSquare);

    public boolean checkSafeSquare(Square end, Board board, Colour colour){
        if (colour == Colour.WHITE){
            for (int i = 0; i < board.blackPieces.size(); i++){
                if(board.blackPieces.get(i).getType() == Type.BISHOP || board.blackPieces.get(i).getType() == Type.ROOK || board.blackPieces.get(i).getType() == Type.QUEEN) {
                    if (isPathEmpty(board.blackPieces.get(i).getType(), board.blackPieces.get(i).getSquare(), board.getSquareOfWhiteKing(), board)) {
                        return false;
                    }
                }
                else if (board.blackPieces.get(i).getType() == Type.KNIGHT){
                    if (board.blackPieces.get(i).isAllowedPath(end)){
                        return false;
                    }
                }
                else if (board.blackPieces.get(i).getType() == Type.KING){
                    if (board.blackPieces.get(i).isSurroundingSquare(end)){
                        return false;
                    }
                }
                else {
                    if (board.blackPieces.get(i).pawnCanCapture(end)){
                        return false;
                    }
                }
            }
        }
        else {
            for (int i = 0; i < board.whitePieces.size(); i++){
                if(board.whitePieces.get(i).getType() == Type.BISHOP || board.whitePieces.get(i).getType() == Type.ROOK || board.whitePieces.get(i).getType() == Type.QUEEN) {
                    if (isPathEmpty(board.whitePieces.get(i).getType(), board.whitePieces.get(i).getSquare(), board.getSquareOfBlackKing(), board)) {
                        return false;
                    }
                }
                else if (board.whitePieces.get(i).getType() == Type.KNIGHT){
                    if (board.whitePieces.get(i).isAllowedPath(end)){
                        return false;
                    }
                }
                else if (board.whitePieces.get(i).getType() == Type.KING){
                    if (board.whitePieces.get(i).isSurroundingSquare(end)){
                        return false;
                    }
                }
                else {
                    if (board.whitePieces.get(i).pawnCanCapture(end)){
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
