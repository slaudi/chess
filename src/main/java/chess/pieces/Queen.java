package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Queen class is a Subclass of the Piece class and represents a Piece of the Type Queen
 */
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
            return "Q";
        } else {
            return "q";
        }
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

        return diff_x == diff_y || diff_y == 0 || diff_x == 0;
    }

    @Override
    public int[][] movingDirection(Square finalSquare) {
        int dir_x = 0;
        int dir_y = 0;
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (this.square.getX() == finalSquare.getX()) {
            // Queen moves vertically
            if (finalSquare.getY() - this.square.getY() < 0) {
                dir_y = -1; // Queen moves up
            } else {
                dir_y = 1; // Queen moves down
            }
        } else if (this.square.getY() == finalSquare.getY()) {
            // Queen moves horizontally
            if (finalSquare.getX() - this.square.getX() < 0) {
                dir_x = -1; // Queen moves to the left
            } else {
                dir_x = 1; // Queen moves to the right
            }
        } else {
            // Queen moves diagonally
            if (finalSquare.getX() - this.square.getX() < 0) {
                dir_x = -1; // Queen moves diagonally to the left
            } else {
                dir_x = 1; // Queen moves diagonally to the right
            }
            if (finalSquare.getY() - this.square.getY() < 0) {
                dir_y = 1; // Queen moves diagonally up
            } else {
                dir_y = -1; // Queen moves diagonally down
            }
        }
        int[][] dir = new int[1][2];
        dir[0][0] = dir_x;
        dir[0][1] = dir_y;
        return dir;
    }

}
