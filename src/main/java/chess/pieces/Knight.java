package chess.pieces;

import chess.game.Colour;
import chess.game.Player;
import chess.game.Square;
import chess.game.Type;

public class Knight extends Piece {

    public Type type;

    /**
     * Constructor for a Piece
     *
     * @param x      the x location of the Bishop
     * @param y      the y location of the Bishop
     * @param colour the Colour object associated with the Bishop
     */
    public Knight(int x, int y, Colour colour) {
        super(x, y, colour);
        type = Type.KNIGHT;
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
     * @param last_x the final x location
     * @param last_y the final y location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(int last_x, int last_y) {
        int diff_x = Math.abs(last_x - this.x);
        int diff_y = Math.abs(last_y - this.y);

        return ((diff_x == 2 && diff_y == 1) || (diff_x == 1 && diff_y == 2));
    }

    /**
     * Draws a path of the Knight's move to and stores it
     * to later determine if another piece is in it's path
     *
     * @param first_x   the first x position
     * @param first_y   the first y position
     * @param last_x    the final x position
     * @param last_y    the final y position
     * @return a Square array of the path
     */
    @Override
    public int[][] drawMove(int first_x, int first_y, int last_x, int last_y) {
        // a Knight is allowed to jump over pieces, doesn't need a path
        int squares = 0;
        return new int[2][squares];
    }
}
