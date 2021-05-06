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


    public boolean finalSquareIsEmpty (Square end, Board board){
        if(board.getBoard()[end.getX()][end.getY()].getOccupiedBy() == null){
            return true;
        }
        else return false;
    }

    public abstract boolean pawnCanCapture(Square finalSquare);




}
