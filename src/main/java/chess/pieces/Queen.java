package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Queen class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type Queen.
 */
public class Queen extends Piece {

    final Type type = Type.QUEEN;

    /**
     * Constructor for creating a Queen piece.
     *
     * @param square The location of the Queen on the board.
     * @param colour The Colour associated with the Queen.
     */
    public Queen(Square square, Colour colour) {
        super(square, colour);
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
    public boolean hasNotMoved() {
        return this.notMoved;
    }

    @Override
    public void setNotMoved(boolean x) {
        this.notMoved = x;
    }

    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "Q";
        } else {
            return "q";
        }
    }

    /**
     * A function determining if the Queen is moving in a straight line in any direction
     * and doesn't stay on its original square.
     *
     * @param finalSquare The square where the Queen should move to.
     * @return boolean Returns 'true' if the Queen is moving in a straight line in any direction.
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (diff_x == 0 && diff_y == 0) {
            return false;
        }
        return diff_x == diff_y || diff_y == 0 || diff_x == 0;
    }

}
