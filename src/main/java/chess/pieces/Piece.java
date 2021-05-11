package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Piece class is the Superclass of the chess pieces on the board of the game
 */
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

    /**
     * Getter for the position of the Piece.
     * @return Square The location of the Piece
     */
    public abstract Square getSquare();

    /**
     * Setter for the location of a Piece, used to update the location after a move.
     * @param square The new location of a Piece after a move
     */
    public abstract void setSquare(Square square);

    /**
     * Getter for the colour of a Piece
     * @return Colour The colour of the Piece
     */
    public abstract Colour getColour();

    /**
     * Getter for the type of a Piece
     * @return Type The type of the Piece
     */
    public abstract Type getType();

    /**
     * Getter for the variable 'hasMoved', used to determine if the Piece has moved already for castling or en passant
     * @return boolean A boolean indicating if the Piece has moved yet
     */
    public abstract boolean isHasMoved();

    /**
     * Setter for the variable 'hasMoved', is set to true after the first move of a Piece
     * @param x The boolean 'true' after a Piece has moved
     */
    public abstract void setHasMoved(boolean x);

    /**
     * A function to determine if a Piece is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the Piece on the chess board
     */
    @Override
    public abstract String toString();

    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    public abstract boolean isPiecesMove(Square finalSquare);

}
