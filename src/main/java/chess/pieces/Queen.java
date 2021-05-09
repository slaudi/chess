package chess.pieces;

import chess.game.*;

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

        return (diff_x == diff_y || diff_x == 0 || diff_y == 0);
    }

    /**
     * Computes the direction of the Queen to determine its path
     *
     * @param finalSquare the final location of the Queen
     */
    @Override
    public Square[][] getDirection(Square finalSquare) {
        int squaresVisited;
        int dir_x = 0;
        int dir_y = 0;
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (this.square.getX() == finalSquare.getX()) {
            // Queen moves vertically
            squaresVisited = diff_y;
            if (finalSquare.getY() - this.square.getY() < 0) {
                dir_y = -1; // Queen moves up
            } else {
                dir_y = 1; // Queen moves down
            }
        } else if (this.square.getY() == finalSquare.getY()) {
            // Queen moves horizontally
            squaresVisited = diff_x;
            if (finalSquare.getX() - this.square.getX() < 0) {
                dir_x = -1; // Queen moves to the left
            } else {
                dir_x = 1; // Queen moves to the right
            }
        } else {
            // Queen moves diagonally
            squaresVisited = diff_x;
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

        int[][] direction = new int[1][2];
        direction[0][0] = dir_x;
        direction[0][1] = dir_y;
        direction[0][2] = squaresVisited;

        return super.generatePath(direction);
    }


}
