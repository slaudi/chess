package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Knight class is a Subclass of the Piece class and represents a Piece of the Type Knight.
 */
public class Knight extends Piece {

    final Type type = Type.KNIGHT;

    /**
     * Constructor for creating a Knight piece.
     *
     * @param square The location of the Knight on the board.
     * @param colour The Colour associated with the Knight.
     */
    public Knight(Square square, Colour colour) {
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
            return "N";
        } else {
            return "n";
        }
    }

    /**
     * A function determining if the Knight is only moving in the shape of an L and doesn't
     * stay on its original square.
     *
     * @return boolean Returns 'true' if the move is in the shape of an L.
     */

    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (diff_x == 0 && diff_y == 0) {
            return false;
        }
        return diff_x == 2 && diff_y == 1 || diff_x == 1 && diff_y == 2;
    }

    @Override
    public int getPositionalValue(int x, int y) {
        int[][] pawnSquareTable;
        if (this.colour == Colour.BLACK){
            pawnSquareTable = new int[][]
                    {
                        {-50,-40,-30,-30,-30,-30,-40,-50},
                        {-40,-20,  0,  0,  0,  0,-20,-40},
                        {-30,  0, 10, 15, 15, 10,  0,-30},
                        {-30,  5, 15, 20, 20, 15,  5,-30},
                        {-30,  0, 15, 20, 20, 15,  0,-30},
                        {-30,  5, 10, 15, 15, 10,  5,-30},
                        {-40,-20,  0,  5,  5,  0,-20,-40},
                        {-50,-40,-30,-30,-30,-30,-40,-50}
                    };
        } else {
            pawnSquareTable = new int[][]
                    {
                        {-50,-40,-30,-30,-30,-30,-40,-50},
                        {-40,-20,  0,  5,  5,  0,-20,-40},
                        {-30,  5, 10, 15, 15, 10,  5,-30},
                        {-30,  0, 15, 20, 20, 15,  0,-30},
                        {-30,  5, 15, 20, 20, 15,  5,-30},
                        {-30,  0, 10, 15, 15, 10,  0,-30},
                        {-40,-20,  0,  0,  0,  0,-20,-40},
                        {-50,-40,-30,-30,-30,-30,-40,-50}
                    };
        }
        return pawnSquareTable[y][x];
    }
}
