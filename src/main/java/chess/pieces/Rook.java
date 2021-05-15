package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Rook class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type Rook.
 */
public class Rook extends Piece implements MovingDirection {

    private final Type type = Type.ROOK;

    /**
     * Constructor for creating a Rook piece.
     *
     * @param square The location of the Bishop on the board.
     * @param colour The Colour associated with the Rook.
     */
    public Rook(Square square, Colour colour) {
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
            return "R";
        } else {
            return "r";
        }
    }

    /**
     * Determines if the Rook is moving only horizontally or vertically in any direction and doesn't
     * stay on its original square.
     *
     * @param finalSquare The square where the Bishop should move to.
     * @return boolean Returns 'true' if the Rook is moving only horizontally or vertically in any direction.
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (diff_x == diff_y) {
            return false;
        }
        return diff_y == 0 || diff_x == 0;
    }

    @Override
    public int[][] movingDirection(Square finalSquare) {
        int dir_x = 0;
        int dir_y = 0;

        if(finalSquare.getX() == this.square.getX()) {
            // Rook moves vertically
            if(finalSquare.getY() - this.square.getY() < 0) {
                dir_y = -1; // Rook moves up
            } else {
                dir_y = 1; // Rook moves down
            }
        } else {
            // Rook moves horizontally
            if(finalSquare.getX() - this.square.getX() < 0) {
                dir_x = -1; // Rook moves to the left
            } else {
                dir_x = 1; // Rook moves to the right
            }
        }

        int[][] dir = new int[1][2];
        dir[0][0] = dir_x;
        dir[0][1] = dir_y;
        return dir;
    }
}