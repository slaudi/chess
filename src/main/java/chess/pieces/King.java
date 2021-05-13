package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The King class is a Subclass of the Piece class and represents a Piece of the Type King
 */
public class King extends Piece {

    Type type;

    /**
     * Constructor for a King
     *
     * @param square the location of the King
     * @param colour the Colour object associated with the King
     */
    public King(Square square, Colour colour) {
        super(square, colour);
        type = Type.KING;
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
        if(this.colour == Colour.WHITE){
            return "K";
        } else {
            return "k";
        }
    }

    /**
     * Determines if the King is moving only one Square in any direction
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isPiecesMove(Square finalSquare) {
        int diffX = Math.abs(this.square.getX() - finalSquare.getX());
        int diffY = Math.abs(this.square.getY() - finalSquare.getY());
        return diffX < 2 && diffY < 2;
    }

}
