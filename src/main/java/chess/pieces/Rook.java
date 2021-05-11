package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Rook class is a Subclass of the Piece class and represents a Piece of the Type Rook
 */
public class Rook extends Piece {

    Type type;

    /**
     * Constructor for a Rook
     *
     * @param square the location of the Rook
     * @param colour the Colour object associated with the Rook
     */
    public Rook(Square square, Colour colour) {
        super(square, colour);
        type = Type.ROOK;
    }

    @Override
    public Square getSquare() {
        return this.square;
    }

    @Override
    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public Colour getColour() {
        return this.colour;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public boolean isHasMoved() {
        return this.hasMoved;
    }

    @Override
    public void setHasMoved(boolean x) {
        this.hasMoved = x;
    }

    @Override
    public String toString() {
        if (this.colour == Colour.WHITE) {
            return "R";
        } else {
            return "r";
        }
    }

    /**
     * Determines if the Rook is moving only horizontally or vertically
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isPiecesMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        return diff_y == 0 || diff_x == 0;
    }

}