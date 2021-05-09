package chess.pieces;

import chess.game.*;

public abstract class Piece {

    Square square;
    Colour colour;
    boolean hasMoved;

    /**
     * Constructor for a Piece
     * @param square the location of the Piece
     * @param colour the Colour object associated with the Piece
     */
    public Piece(Square square, Colour colour) {
        this.square = square;
        this.colour = colour;
        this.hasMoved = false;
    }

    public Square getSquare(){
        return this.square;
    }

    public abstract void setSquare(Square square);

    public abstract Colour getColour();

    public abstract Type getType();

    public abstract boolean getHasMoved();

    public abstract void setHasMoved(boolean x);

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
    public abstract boolean isPiecesMove(Square finalSquare);
    // kein Piece darf ziehen, wenn der eigene King im Angriff steht oder dadurch einem Angriff ausgesetzt wird


}
