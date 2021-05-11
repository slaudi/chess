package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Queen extends Piece {

    Type type;

    /**
     * Constructor for a Queen
     *
     * @param square the location of the Queen
     * @param colour the Colour object associated with the Queen
     */
    public Queen(Square square, Colour colour) {
        super(square, colour);
        type = Type.QUEEN;
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
     * A function to determine if the Queen is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the Queen on the chess board
     */
    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "Q";
        } else return "q";
    }

    /**
     * Determines if the Queen is moving in a straight line in any direction
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isPiecesMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        return (diff_x == diff_y || diff_y == 0 || diff_x == 0);
    }

}
