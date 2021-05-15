package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The King class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type King.
 */
public class King extends Piece implements MovingDirection {

    private final Type type = Type.KING;

    /**
     * Constructor for creating a King piece.
     *
     * @param square The location of the King on the board.
     * @param colour The Colour associated with the King.
     */
    public King(Square square, Colour colour) {
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
        if (this.colour == Colour.WHITE) {
            return "K";
        } else {
            return "k";
        }
    }

    /**
     * A function determining if the King is moving only one Square in any direction and doesn't
     * stay on its original square.
     *
     * @param finalSquare The square where the Bishop should move to.
     * @return boolean Returns 'true' if the move is only one Square in any direction.
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(this.square.getX() - finalSquare.getX());
        int diff_y = Math.abs(this.square.getY() - finalSquare.getY());
        if (diff_x == 0 && diff_y == 0) {
            return false;
        }
        return diff_x < 2 && diff_y < 2;
    }

    @Override
    public int[][] movingDirection(Square finalSquare) {
        int dir_x;
        int dir_y = 0;
        int diff = finalSquare.getX() - this.square.getX();

        if (diff < 0) {
            // queenside castling
            dir_x = -1;
        } else {
            // kingside castling
            dir_x = 1;
        }
        int[][] dir = new int[1][2];
        dir[0][0] = dir_x;
        dir[0][1] = dir_y;
        return dir;
    }

}
