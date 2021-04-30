package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Pawn extends Piece {

    public Type type;

    /**
     * Constructor for a Pawn
     *
     * @param square the location of the Pawn
     * @param colour the Colour object associated with the Pawn
     */
    public Pawn(Square square, Colour colour) {
        super(square, colour);
        type = Type.PAWN;
    }

    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "P";
        } else return "p";
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
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        // determines if it is the Pawn's first move to let it move two Squares up/down
        if(pawnIsFirstMove(finalSquare)) {
           return true;
        }
        // determines if a Pawn moves only one Square diagonally and only if there is an enemy piece on it
        if(pawnCanCapture(finalSquare)) {
            return true;
        }
        // determines if a Piece in front of the Pawn, if not the Pawn is allowed to move forward
        if(pawnIsAllowedForward(finalSquare)) {
            return true;
        } else return false;
    }

    public boolean pawnIsFirstMove(Square finalSquare) {
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        if((this.colour == Colour.WHITE && this.square.y == 6 /*board[] statt 6? */ )
                || (this.colour == Colour.BLACK && this.square.y == 1) ) {
            return true;
        } else return false;
    }

    public boolean pawnCanCapture(Square finalSquare) {
        // en passant auch noch beachten beim enemy pawn
        return false;
    }

    public boolean pawnIsAllowedForward(Square finalSquare) {
        return true;
    }

    /**
     * Draws a path of the Pawn's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final location
     * @return a Square array of the path
     */
    @Override
    public int[][] drawMove(Square finalSquare) {
        // Pawn has it's own method to determine if a Piece is in it's path
        int square = 0;
        return new int[0][square];
    }
}
