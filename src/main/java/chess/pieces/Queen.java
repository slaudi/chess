package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Queen extends Piece {

    public Type type;

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
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "Q";
        } else return "q";
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Colour getColour() {
        return this.colour;
    }

    /**
     * Determines if the Queen is moving in a straight line in any direction
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

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
    public int[][] drawMove(Square finalSquare) {
        int squaresVisited;
        int dir_x = 0;
        int dir_y = 0;
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        if(this.square.x == finalSquare.x) {
            // Queen moves vertically
            squaresVisited = diff_y;
            if(finalSquare.y - this.square.y < 0) {
                dir_y = -1; // Queen moves up
            } else {
                dir_y = 1; // Queen moves down
            }
        } else if(this.square.y == finalSquare.y) {
            // Queen moves horizontally
            squaresVisited = diff_x;
            if(finalSquare.x - this.square.x < 0) {
                dir_x = -1; // Queen moves to the left
            } else {
                dir_x = 1; // Queen moves to the right
            }
        } else {
            // Queen moves diagonally
            squaresVisited = diff_x;
            if(finalSquare.x - this.square.x < 0) {
                dir_x = -1; // Queen moves diagonally to the left
            } else {
                dir_x = 1; // Queen moves diagonally to the right
            }
            if(finalSquare.y - this.square.y < 0) {
                dir_y = 1; // Queen moves diagonally up
            } else {
                dir_y = -1; // Queen moves diagonally down
            }
        }

        int[][] move = new int[2][squaresVisited];

        if(squaresVisited > 1) {
            // Queen moves more than one square
            for (int i = 0; i < squaresVisited - 1; i++) {
                // stores squares except start and final square
                move[0][i] = this.square.x + dir_x*(i+1); // storing the path in x direction
                move[1][i] = this.square.y + dir_y*(i+1); // storing the path in y direction
            }
        }

        return move;
    }
}
