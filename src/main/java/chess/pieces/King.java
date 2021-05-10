package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;


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
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    @Override
    public void setHasMoved(boolean x) {
        this.hasMoved = x;
    }

    /**
     * A function to determine if the King is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the King on the chess board
     */
    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "K";
        } else return "k";
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
