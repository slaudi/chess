package chess.pieces;

import chess.game.Board;
import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

public class Knight extends Piece {

    Type type;

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
    public boolean getMoved() {
        return this.moved;
    }

    @Override
    public void setMoved(boolean x) {
        this.moved = x;
    }

    /**
     * A function to determine if the Knight is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the Knight on the chess board
     */
    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "N";
        } else return "n";
    }

    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @return a boolean indicating if the move is allowed
     */

    @Override
    public boolean isAllowedPath(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());

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
    public Square[][] drawMove(Square finalSquare) {
        // a Knight is allowed to jump over pieces, doesn't need a path
        int squaresVisited = 0;
        return new Square[1][squaresVisited];
    }

    @Override
    public boolean isSurroundingSquare(Square square) {
        return false;
    }

    @Override
    public boolean pawnCanCapture(Square finalSquare) {
        return false;
    }
}
