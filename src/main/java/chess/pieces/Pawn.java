package chess.pieces;

import chess.game.*;

import java.util.Stack;

/**
 * The Pawn class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type Pawn.
 */
public class Pawn extends Piece implements MovingDirection {

    private final Type type = Type.PAWN;

    /**
     * Constructor for creating a Pawn piece.
     *
     * @param square The location of the Pawn on the board.
     * @param colour The Colour associated with the Pawn.
     */
    public Pawn(Square square, Colour colour) {
        super(square, colour);
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
    public boolean hasNotMoved() {
        return this.notMoved;
    }

    @Override
    public void setNotMoved(boolean x) {
        this.notMoved = x;
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
     * A function determining if the Pawn is moving only one Square up/down
     * OR two up/down during the first move (depending on it's Colour) and doesn't
     * stay on its original square..
     *
     * @param finalSquare The square where the Pawn should move to.
     * @return boolean  Returns 'true' if the Pawn is moving only one Square up/down
     *                  OR two up/down during the first move (depending on it's Colour).
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = finalSquare.getX() - this.square.getX();
        int diff_y = finalSquare.getY() - this.square.getY();
        if (Math.abs(diff_x) == Math.abs(diff_y) || finalSquare.getOccupiedBy() != null) {
            return false;
        }
        if (!notMoved && isPathEmpty(this, finalSquare, chessBoard)) {
            // Pawn can move one or two Squares
            if (this.colour == Colour.WHITE) {
                return diff_y == -1 || diff_y == -2 && diff_x == 0;
            } else {
                return diff_y == 1 || diff_y == 2 && diff_x == 0;
            }
        } else if (notMoved){
            // Pawn already moved
            if (this.colour == Colour.WHITE) {
                // Pawn can only move up
                return diff_y == -1 && diff_x == 0;
            } else {
                // Pawn can only move down
                return diff_y == 1 && diff_x == 0;
            }
        }
        return false;
    }


    /**
     * A function determining if the piece a Pawn wants to capture is standing on a square
     * one square diagonally away from the Pawn - if the Pawn is white diagonally up
     * and if it's black diagonally down.
     *
     * @param enemySquare The square on which the Pawn wants to capture a Piece.
     * @return boolean Returns 'true' if the capture is allowed.
     */
    public boolean canCapture(Square enemySquare) {
        int diff_x = enemySquare.getX() - this.square.getX();
        int diff_y = enemySquare.getY() - this.square.getY();
        if (enemySquare.getOccupiedBy() == null) {
            return false;
        }
        if (this.colour == Colour.WHITE) {
            return Math.abs(diff_x) == 1 && diff_y == -1;
        } else {
            return Math.abs(diff_x) == 1 && diff_y == 1;
        }
    }

    /**
     * The function is called from isMoveAllowed() before the new move is added to the
     * moveHistory-Stack and determines if a pawn can beat another pawn en passant.
     *
     * @param finalSquare   The square where the selected pawn should end up, here behind the pawn to beat.
     * @param lastEnemyMove The last move of the enemy before this one.
     * @return boolean Returning 'true': en passant is possible.
     */
    public boolean isEnPassant(Square finalSquare, Move lastEnemyMove) {
        Square end = lastEnemyMove.getFinalSquare();
        int diff_x = finalSquare.getX() - this.square.getX();
        int diff_y = finalSquare.getY() - this.square.getY();

        if (end.getOccupiedBy().getType() == Type.PAWN
                && end.getOccupiedBy().getColour() != this.colour) {
            if(this.colour == Colour.WHITE) {
                return Math.abs(diff_x) == 1 && diff_y == -1;
            }
            else {
                return Math.abs(diff_x) == 1 && diff_y == 1;
            }
        }
        return false;
    }

    /**
     * The function is called from processMove() after the current Move ist added to the
     * moveHistory-Stack and determines if a pawn can beat another pawn en passant.
     *
     * @param finalSquare   The square where the selected pawn should end up, here behind the pawn to beat.
     * @param history       A Stack which stores all previous moves.
     * @return boolean Returns 'true' if en passant is possible.
     */
    public boolean isEnPassant(Square finalSquare, Stack<Move> history) {
        if (history.size() > 1) {
            Move lastEnemyMove = history.peek();
            Square start = lastEnemyMove.getStartSquare();
            Square end = lastEnemyMove.getFinalSquare();
            int diff_x = finalSquare.getX() - this.square.getX();
            int diff_y = finalSquare.getY() - this.square.getY();
            int diff_enemy = start.getY() - end.getY();

            if (Math.abs(diff_enemy) == 2 && end.getOccupiedBy().getType() == Type.PAWN
                    && end.getOccupiedBy().getColour() != this.colour
                    && end.getY() == this.square.getY()) {
                if(this.colour == Colour.WHITE) {
                    return Math.abs(diff_x) == 1 && diff_y == -1;
                }
                else {
                    return Math.abs(diff_x) == 1 && diff_y == 1;
                }
            }
        }
        return false;
    }

    /**
     * A function determining if a Pawn has arrived on a Square on the far end of
     * the board so the a promotion to a Queen, Bishop, Knight or Rook is possible
     *
     * @param finalSquare The Square where the move of the Pawn ends.
     * @return boolean Returns 'true' if the move puts the Pawn on the last Square
     * on the board so that a promotion is possible.
     */
    public boolean promotionPossible(Square finalSquare) {
        if (this.colour == Colour.WHITE) {
            return finalSquare.getY() == 0;
        } else {
            return finalSquare.getY() == 7;
        }
    }

    @Override
    public int[][] movingDirection(Square finalSquare) {
        int dir_x = 0;
        int dir_y;

        if (this.colour == Colour.WHITE) {
            dir_y = -1;
        } else {
            dir_y = 1;
        }
        int[][] dir = new int[1][2];
        dir[0][0] = dir_x;
        dir[0][1] = dir_y;
        return dir;
    }
}
