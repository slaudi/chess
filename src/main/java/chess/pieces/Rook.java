package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Rook class is a Subclass of the Piece class and represents a Piece of the Type Rook
 */
public class Rook extends Piece implements MovingDirection {

    Type type;

    /**
     * Constructor for a Rook
     *
     * @param square the location of the Rook
     * @param colour the Colour object associated with the Rook
     */
    public Rook(Square square, Colour colour) {
        super(square, colour);
        type = Type.ROOK;
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
        if (this.colour == Colour.WHITE) {
            return "R";
        } else {
            return "r";
        }
    }

    /**
     * Determines if the Rook is moving only horizontally or vertically
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isPiecesMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

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