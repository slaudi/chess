package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Bishop extends Piece {

    public Type type;
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
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "B";
        } else return "b";
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
     * Determines if the Bishop is moving diagonally
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        return diff_x == diff_y;
    }

    /**
     * Draws a path of the Bishop's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final position
     * @return a Square array of the path
     */
    @Override
    public int[][] drawMove(Square finalSquare) {
        int dir_x = 0;
        int dir_y = 0;
        int diff = Math.abs(finalSquare.x - this.square.x);

        int squaresVisited = diff;
        if(finalSquare.x - this.square.x < 0) {
            // Bishop moves to the left
            if(finalSquare.y - this.square.y < 0) {
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
            if(finalSquare.y - this.square.y < 0) {
                // Bishop moves right up
                dir_x = 1;
                dir_y = -1;
            } else {
                // Bishop moves right down
                dir_x = 1;
                dir_y = 1;
            }
        }

        int[][] move = new int[2][squaresVisited];

        if(squaresVisited > 1) {
            // Bishop moves more than one square
            for(int i = 0; i < squaresVisited - 1; i++) {
                // stores squares except start and final square
                move[0][i] = this.square.x + dir_x*(i+1);
                move[0][i] = this.square.y + dir_y*(i+1);
            }
        }

        return move;
    }
}
