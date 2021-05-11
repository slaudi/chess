package chess.pieces;

import chess.game.*;

import java.util.Stack;

/**
 * The Pawn class is a Subclass of the Piece class and represents a Piece of the Type Pawn
 */
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
    public boolean isHasMoved() {
        return this.hasMoved;
    }

    @Override
    public void setHasMoved(boolean x) {
        this.hasMoved = x;
    }

    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "P";
        } else {
            return "p";
        }
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
        int diff_x = finalSquare.getX() - this.square.getX();
        int diff_y = finalSquare.getY() - this.square.getY();
        if (!hasMoved) {
            // Pawn can move one or two Squares
            if (this.colour == Colour.WHITE) {
                return diff_y == -1 || diff_y == -2 && diff_x == 0;
            } else {
                return diff_y == 1 || diff_y == 2 && diff_x == 0;
            }
        } else {
            if (this.colour == Colour.WHITE) {
                // Pawn can only move up
                return diff_y == -1 && diff_x == 0;
            } else {
                // Pawn can only move down
                return diff_y == 1 && diff_x == 0;
            }
        }
    }

    /**
     * A function determining if a move to capture another Piece is allowed for the Pawn
     *
     * @param finalSquare the Square where the enemy Piece is located on
     * @return a boolean indicating if a capture is allowed
     */
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

    /**
     * a function determining if a pawn can beat another pawn en passant
     *
     * @param finalSquare   the square where the selected pawn should end up, here behind the pawn to beat
     * @param history       a stach which stores all previous moves
     * @return a boolean indicating if en passant is possible
     */
    public boolean isEnPassant(Square finalSquare, Stack<Move> history) {
        if (!history.isEmpty()) {
            Move lastMove = history.pop();
            Square start = lastMove.getStartSquare();
            Square end = lastMove.getFinalSquare();
            history.add(lastMove);
            int diff = Math.abs(start.getY() - end.getY());

            if (diff == 2 && end.getOccupiedBy().getType() == this.type
                    && end.getOccupiedBy().getColour() != this.colour
                    && end.getY() == this.square.getY()) {
                return canCapture(finalSquare);
            }
        }
        return false;
    }

    /**
     * A function determining if a promotion is possible
     *
     * @param finalSquare the Square where the move of the Pawn ends up
     * @return a boolean indicating if a promotion is possible
     */
    public boolean promotionPossible(Square finalSquare) {
        if (this.colour == Colour.WHITE) {
            return finalSquare.getY() == 0;
        } else {
            return finalSquare.getY() == 7;
        }
    }

}
