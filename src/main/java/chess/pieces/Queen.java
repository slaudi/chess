package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public class Queen extends Piece {

    public Type type;

    /**
     * Constructor for a Queen
     *
     * @param x      the x location of the Queen
     * @param y      the y location of the Queen
     * @param colour the Colour object associated with the Queen
     */
    public Queen(int x, int y, Colour colour) {
        super(x, y, colour);
        type = Type.QUEEN;
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
     * @param last_x the final x location
     * @param last_y the final y location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(int last_x, int last_y) {
        int diff_x = Math.abs(last_x - this.x);
        int diff_y = Math.abs(last_y - this.y);

        return (diff_x == diff_y || diff_x == 0 || diff_y == 0);
    }

    /**
     * Draws a path of the Queen's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param first_x   the first x position
     * @param first_y   the first y position
     * @param last_x    the final x position
     * @param last_y    the final y position
     * @return an array of the move's path
     */
    @Override
    public int[][] drawMove(int first_x, int first_y, int last_x, int last_y) {
        int squaresVisited;
        int dir_x = 0;
        int dir_y = 0;
        int diff_x = Math.abs(last_x - first_x);
        int diff_y = Math.abs(last_y - first_y);

        if(first_x == last_x) {
            // Queen moves vertically
            squaresVisited = diff_y;
            if(last_y - first_y < 0) {
                dir_y = -1; // Queen moves up
            } else {
                dir_y = 1; // Queen moves down
            }
        } else if(first_y == last_y) {
            // Queen moves horizontally
            squaresVisited = diff_x;
            if(last_x - first_x < 0) {
                dir_x = -1; // Queen moves to the left
            } else {
                dir_x = 1; // Queen moves to the right
            }
        } else {
            // Queen moves diagonally
            squaresVisited = diff_x;
            if(last_x - first_x < 0) {
                dir_x = -1; // Queen moves diagonally to the left
            } else {
                dir_x = 1; // Queen moves diagonally to the right
            }
            if(last_y - first_y < 0) {
                dir_y = 1; // Queen moves diagonally up
            } else {
                dir_y = -1; // Queen moves diagonally down
            }
        }

        int[][] move = new int[2][squaresVisited];

        // if Queen is moving more than one square
        if(squaresVisited - 1 > 0) {
            for (int i = 0; i < squaresVisited; i++) {
                move[0][i] = first_x + dir_x*(i+1); // storing the path in x direction
                move[1][i] = first_y + dir_y*(i+1); // storing the path in y direction
            }
        }

        return move;
    }
}
