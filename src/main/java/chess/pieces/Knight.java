package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Knight extends Piece {

    public Type type;

    /**
     * Constructor for a Knight
     *
     * @param square the location of the Knight
     * @param colour the Colour object associated with the Knight
     */
    public Knight(Square square, Colour colour) {
        super(square, colour);
        type = Type.KNIGHT;
    }

    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "N";
        } else return "n";
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
     * Determines if the Knight is moving in the shape of an L
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        return ((diff_x == 2 && diff_y == 1) || (diff_x == 1 && diff_y == 2));
    }

    /**
     * Draws a path of the Knight's move to and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final location
     * @return a Square array of the path
     */
    @Override
    public int[][] drawMove(Square finalSquare) {
        // a Knight is allowed to jump over pieces, doesn't need a path
        int squares = 0;
        return new int[2][squares];
    }
}
