package chess.pieces;

import chess.game.*;

public class Rook extends Piece {

    Type type;

    /**
     * Constructor for a Rook
     *
     * @param square the location of the Rook
     * @param colour the Colour object associated with the Rook
     */
    public Rook(Square square, Colour colour) {
        super(square, colour);
        type = Type.ROOK;
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
     * A function to determine if the Rook is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the Rook on the chess board
     */
    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "R";
        } else return "r";
    }

    /**
     * Determines if a move is valid based on the type of the Piece
     *
     * @param finalSquare the final location
     * @param board
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedPath(Square finalSquare, Board board) {
        return true;
    }

    /**
     * Determines if the Rook is moving only horizontally or vertically
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedPath(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        return(diff_y == 0 || diff_x == 0);
    }

    /**
     * Draws a path of the Rook's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final location
     * @return a Square array of the path
     */
    @Override
    public Square[][] drawMove(Square finalSquare) {
        int squaresVisited;
        int dir_x = 0;
        int dir_y = 0;
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);

        if(finalSquare.x == this.square.x) {
            // Rook moves vertically
            squaresVisited = diff_y;
            if(finalSquare.y - this.square.y < 0) {
                dir_y = -1; // Rook moves up
            } else {
                dir_x = 1; // Rook moves down
            }
        } else {
            // Rook moves horizontally
            squaresVisited = diff_x;
            if(finalSquare.x - this.square.x < 0) {
                dir_x = -1; // Rook moves to the left
            } else {
                dir_x = 1; // Rook moves to the right
            }
        }

        Square[][] move = new Square[1][squaresVisited];

        if(squaresVisited > 1) {
            // Rook moves more than one Square
            for(int i = 0; i < squaresVisited - 1; i++) {
                // stores squares except start and final square
                int x = this.square.x + dir_x*(i+1);
                int y = this.square.y + dir_y*(i+1);
                move[x][y] = new Square(Label.values()[(squaresVisited*x+y)], x, y);
            }
        }
        return move;
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
