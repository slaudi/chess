package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public abstract class Piece {

    public Square square;
    public Colour colour;

    /**
     * Constructor for a Piece
     * @param square the location of the Piece
     * @param colour the Colour object associated with the Piece
     */
    public Piece(Square square, Colour colour) {
        this.square = square;
        this.colour = colour;
    }

    public abstract String toString();

    public abstract Type getType();

    public abstract Colour getColour();


    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    public abstract boolean isAllowedMove(Square finalSquare);
    // kein Piece darf ziehen, wenn der eigene King im Angriff steht oder dadurch einem Angriff ausgesetzt wird

    /**
     * Draws a path of a move based on the type of the Piece and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final position
     * @return an array of the path
     */
    public abstract int[][] drawMove(Square finalSquare);

}
