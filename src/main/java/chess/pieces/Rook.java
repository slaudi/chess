package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Rook extends Piece {

    public Type type;

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
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "R";
        } else return "r";
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
     * Determines if the Rook is moving only horizontally or vertically
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        return(diff_y == 0 || diff_x == 0);
    }

    /**
     * Draws a path of the Rook's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final location
     * @return a Square array of the path
     */
    @Override
    public int[][] drawMove(Square finalSquare) {
        int squaresVisited = 0;
        int dir_x = 0;
        int dir_y = 0;
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        if(finalSquare.x == this.square.x) {
            // Rook moves vertically
            squaresVisited = diff_y;
            if(finalSquare.y - this.square.y < 0) {
                dir_y = -1; // Rook moves up
            } else {
                dir_x = 1; // Rook moves down
            }
        } else {
            // Rook moves horizontally
            squaresVisited = diff_x;
            if(finalSquare.x - this.square.x < 0) {
                dir_x = -1; // Rook moves to the left
            } else {
                dir_x = 1; // Rook moves to the right
            }
        }

        int[][] move = new int[2][squaresVisited];

        if(squaresVisited > 1) {
            // Rook moves more than one Square
            for(int i = 0; i < squaresVisited - 1; i++) {
                // stores squares except start and final square
                move[0][i] = this.square.x + dir_x*(i+1);
                move[1][i] = this.square.y + dir_y*(i+1);
            }
        }

        return move;
    }
}
