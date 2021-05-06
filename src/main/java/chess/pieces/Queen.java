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
    public boolean getMoved() {
        return this.moved;
    }

    @Override
    public void setMoved(boolean x) {
        this.moved = x;
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
    public boolean isAllowedPath(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        return (diff_x == diff_y || diff_x == 0 || diff_y == 0);
    }

    /**
     * Draws a path of the Queen's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final location of the Queen
     * @return an array of the move's path
     */
    @Override
    public Square[][] drawMove(Square finalSquare) {
        int squaresVisited;
        int dir_x = 0;
        int dir_y = 0;
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if(this.square.getX() == finalSquare.getX()) {
            // Queen moves vertically
            squaresVisited = diff_y;
            if(finalSquare.getY() - this.square.getY() < 0) {
                dir_y = -1; // Queen moves up
            } else {
                dir_y = 1; // Queen moves down
            }
        } else if(this.square.getY() == finalSquare.getY()) {
            // Queen moves horizontally
            squaresVisited = diff_x;
            if(finalSquare.getX() - this.square.getX() < 0) {
                dir_x = -1; // Queen moves to the left
            } else {
                dir_x = 1; // Queen moves to the right
            }
        } else {
            // Queen moves diagonally
            squaresVisited = diff_x;
            if(finalSquare.getX() - this.square.getX() < 0) {
                dir_x = -1; // Queen moves diagonally to the left
            } else {
                dir_x = 1; // Queen moves diagonally to the right
            }
            if(finalSquare.getY() - this.square.getY() < 0) {
                dir_y = 1; // Queen moves diagonally up
            } else {
                dir_y = -1; // Queen moves diagonally down
            }
        }

        Square[][] move = new Square[1][squaresVisited];

        if(squaresVisited > 1) {
            // Queen moves more than one square
            for (int i = 0; i < squaresVisited - 1; i++) {
                // stores squares except start and final square
                int x = this.square.getX() + dir_x*(i+1);
                int y = this.square.getY() + dir_y*(i+1);
                move[x][y] = new Square(Label.values()[(squaresVisited*x+y)], x, y);
            }
        }

        return move;
    }

    @Override
    public boolean isSurroundingSquare(Square square) {
        return false;
    }

    @Override
    public boolean pawnCanCapture(Square finalSquare) {
        return false;
    }
}
