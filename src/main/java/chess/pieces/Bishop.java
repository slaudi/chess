package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Bishop class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type Bishop.
 */
public class Bishop extends Piece implements MovingDirection {

    private final Type type = Type.BISHOP;

    /**
     * Constructor for creating a Bishop piece.
     *
     * @param square The location of the Bishop on the board.
     * @param colour The Colour associated with the Bishop.
     */
    public Bishop(Square square, Colour colour) {
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
     * A function determining if the Bishop is only moving diagonally in any direction and doesn't
     * stay on its original square.
     *
     * @param finalSquare The square where the Bishop should move to.
     * @return boolean Returns 'true' if the move is diagonal.
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (diff_x == 0 && diff_y == 0) {
            return false;
        }
        return diff_x == diff_y;
    }

    @Override
    public int[][] movingDirection(Square finalSquare) {
        int dir_x;
        int dir_y;

        if(finalSquare.getX() - this.square.getX() < 0) {
            // Bishop moves to the left
            if(finalSquare.getY() - this.square.getY() < 0) {
                //Bishop moves left up
                dir_x = -1;
                dir_y = -1;
            } else {
                // Bishop moves left down
                dir_x = -1;
                dir_y = 1;
            }
        } else {
            // Bishop moves to the right
            if(finalSquare.getY() - this.square.getY() < 0) {
                // Bishop moves right up
                dir_x = 1;
                dir_y = -1;
            } else {
                // Bishop moves right down
                dir_x = 1;
                dir_y = 1;
            }
        }
        int[][] dir = new int[1][2];
        dir[0][0] = dir_x;
        dir[0][1] = dir_y;
        return dir;
    }

}
