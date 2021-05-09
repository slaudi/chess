package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Pawn extends Piece {

    Type type;

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
     * A function to determine if the Pawn is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the Pawn on the chess board
     */
    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "P";
        } else return "p";
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
    public boolean isPiecesMove(Square finalSquare) {
        int diff_y = finalSquare.getY() - this.square.getY();
        if (!hasMoved) {
            // Pawn can move one or two Squares
            if (this.colour == Colour.WHITE) {
                return diff_y == -1 || diff_y == -2;
            } else {
                return diff_y == 1 || diff_y == 2;
            }
        } else {
            if (this.colour == Colour.WHITE) {
                // Pawn can only move up
                return diff_y == -1;
            } else {
                // Pawn can only move down
                return diff_y == 1;
            }
        }
    }


    // TODO en passant
    public boolean canCapture(Square finalSquare) {
        int diffX = finalSquare.getX() - this.square.getX();
        int diffY = finalSquare.getY() - this.square.getY();
        if(this.colour == Colour.WHITE) {
            return Math.abs(diffX) == 1 && diffY == -1;
        }
        else {
            return Math.abs(diffX) == 1 && diffY == 1;
        }
    }

    public boolean pawnIsAllowedForward(Square finalSquare) {
        return true;
    }


}
