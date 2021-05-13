package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Bishop class is a Subclass of the Piece class and represents a Piece of the Type Bishop
 */
public class Bishop extends Piece implements MovingDirection {

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

    @Override
    public int[][] movingDirection(Square finalSquare) {
        int dir_x = 0;
        int dir_y = 0;

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
