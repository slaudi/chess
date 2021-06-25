package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Rook class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type Rook.
 */
public class Rook extends Piece {

    final Type type = Type.ROOK;

    /**
     * Constructor for creating a Rook piece.
     *
     * @param square The location of the Bishop on the board.
     * @param colour The Colour associated with the Rook.
     */
    public Rook(Square square, Colour colour) {
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
    public void setNotMoved(boolean x) {
        this.notMoved = x;
    }

    @Override
    public boolean getNotMoved() {
        return this.notMoved;
    }

    @Override
    public String toString() {
        if (this.colour == Colour.WHITE) {
            return "R";
        } else {
            return "r";
        }
    }

    /**
     * Determines if the Rook is moving only horizontally or vertically in any direction and doesn't
     * stay on its original square.
     *
     * @param finalSquare The square where the Bishop should move to.
     * @return boolean Returns 'true' if the Rook is moving only horizontally or vertically in any direction.
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (diff_x == diff_y) {
            return false;
        }
        return diff_y == 0 || diff_x == 0;
    }

    @Override
    public int getPositionalValue(int x, int y, boolean endgame) {
        int[][] rookSquareTable;
        if (this.colour == Colour.BLACK){
            rookSquareTable = new int[][]
                    {
                        { 0,  0,  0,  0,  0,  0,  0,  0},
                        { 5, 10, 10, 10, 10, 10, 10,  5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        { 0,  0,  0,  5,  5,  0,  0,  0}
                    };
        } else {
            rookSquareTable = new int[][]
                    {
                        { 0,  0,  0,  5,  5,  0,  0,  0},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        {-5,  0,  0,  0,  0,  0,  0, -5},
                        { 5, 10, 10, 10, 10, 10, 10,  5},
                        { 0,  0,  0,  0,  0,  0,  0,  0}
                    };
        }
        return rookSquareTable[y][x];
    }

}