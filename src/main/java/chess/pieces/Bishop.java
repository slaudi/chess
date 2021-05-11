package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Bishop class is a Subclass of the Piece class and represents a Piece of the Type Bishop
 */
public class Bishop extends Piece {

    Type type;
    /**
     * Constructor for a Bishop
     *
     * @param square the location of the Bishop
     * @param colour the Colour object associated with the Bishop
     */
    public Bishop(Square square, Colour colour) {
        super(square, colour);
        type = Type.BISHOP;
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
            return "B";
        } else {
            return "b";
        }
    }

    /**
     * Determines if the Bishop is moving diagonally
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */

    @Override
    public boolean isPiecesMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        return diff_x == diff_y;
    }

}
