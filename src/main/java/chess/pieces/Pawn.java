package chess.pieces;

import chess.game.Colour;
import chess.game.Type;

public class Pawn extends Piece {

    public Type type;

    /**
     * Constructor for a Pawn
     *
     * @param x      the x location of the Pawn
     * @param y      the y location of the Pawn
     * @param colour the Colour object associated with the Pawn
     */
    public Pawn(int x, int y, Colour colour) {
        super(x, y, colour);
        type = Type.PAWN;
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
     * Determines if the Pawn is moving only one Square up/down depending on the Colour of the Pawn
     * OR two up/down during the first move
     * OR one diagonally up/down to capture an enemy piece
     *
     * @param last_x the final x location
     * @param last_y the final y location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(int last_x, int last_y) {
        int diff_x = Math.abs(last_x - this.x);
        // determines if it is the Pawn's first move to let it move two Squares up/down
        if(pawnIsFirstMove(last_x, last_y)) {
           return true;
        }
        // determines if a Pawn moves only one Square diagonally and only if there is an enemy piece on it
        if(pawnCanCapture(last_x, last_y)) {
            return true;
        }
        // determines if a Piece in front of the Pawn, if not the Pawn is allowed to move forward
        if(pawnIsAllowedForward(last_x, last_y)) {
            return true;
        } else return false;
    }

    public boolean pawnIsFirstMove(int last_x, int last_y) {
        int diff_y = Math.abs(last_y - this.y);

        if((this.colour == Colour.WHITE && this.y == 6 /*board[] statt 6? */ )
                || (this.colour == Colour.BLACK && this.y == 1) ) {
            return true;
        } else return false;
    }

    public boolean pawnCanCapture(int last_x, int last_y) {
        // en passant auch noch beachten beim enemy pawn
        return false;
    }

    public boolean pawnIsAllowedForward(int last_x, int last_y) {
        return true;
    }

    /**
     * Draws a path of the Pawn's move and stores it
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
        // Pawn has it's own method to determine if a Piece is in it's path
        int square = 0;
        return new int[0][square];
    }
}
