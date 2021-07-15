package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

/**
 * The Queen class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type Queen.
 */
public class Queen extends Piece {
    // CPD-OFF
    final Type type = Type.QUEEN;

    /**
     * Constructor for creating a Queen piece.
     *
     * @param square The location of the Queen on the board.
     * @param colour The Colour associated with the Queen.
     */
    //it's overriding abstract methods from super class
    public Queen(Square square, Colour colour) {
        super(square, colour);
    }

    @Override
    //it's overriding abstract methods from super class
    public Square getSquare() {
        return this.square;
    }

    @Override
    //it's overriding abstract methods from super class
    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    //it's overriding abstract methods from super class
    public Colour getColour() {
        return this.colour;
    }

    @Override
    //it's overriding abstract methods from super class
    public Type getType() {
        return this.type;
    }

    @Override
    //it's overriding abstract methods from super class
    public void setNotMoved(boolean x) {
        this.notMoved = x;
    }

    @Override
    //it's overriding abstract methods from super class
    public boolean hasNotMoved() {
        return this.notMoved;
    }

    @Override
    //it's overriding abstract methods from super class
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "Q";
        } else {
            return "q";
        }
    }

    /**
     * A function determining if the Queen is moving in a straight line in any direction
     * and doesn't stay on its original square.
     *
     * @param finalSquare The square where the Queen should move to.
     * @return boolean Returns 'true' if the Queen is moving in a straight line in any direction.
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

        if (diff_x == 0 && diff_y == 0) {
            return false;
        }
        return diff_x == diff_y || diff_y == 0 || diff_x == 0;
    }

    @Override
    public int getPositionalValue(int x, int y, boolean endgame) {
        int[][] queenSquareTable;
        if (this.colour == Colour.BLACK){
            queenSquareTable = new int[][]
                    {
                        {-20,-10,-10, -5, -5,-10,-10,-20},
                        {-10,  0,  0,  0,  0,  0,  0,-10},
                        {-10,  0,  5,  5,  5,  5,  0,-10},
                        { -5,  0,  5,  5,  5,  5,  0, -5},
                        {  0,  0,  5,  5,  5,  5,  0, -5},
                        {-10,  5,  5,  5,  5,  5,  0,-10},
                        {-10,  0,  5,  0,  0,  0,  0,-10},
                        {-20,-10,-10, -5, -5,-10,-10,-20}
                    };
        } else {
            queenSquareTable = new int[][]
                    {
                        {-20,-10,-10, -5, -5,-10,-10,-20},
                        {-10,  0,  5,  0,  0,  0,  0,-10},
                        {-10,  5,  5,  5,  5,  5,  0,-10},
                        {  0,  0,  5,  5,  5,  5,  0, -5},
                        { -5,  0,  5,  5,  5,  5,  0, -5},
                        {-10,  0,  5,  5,  5,  5,  0,-10},
                        {-10,  0,  0,  0,  0,  0,  0,-10},
                        {-20,-10,-10, -5, -5,-10,-10,-20}
                    };
        }
        return queenSquareTable[y][x];
    }
    //CPD-ON
}
