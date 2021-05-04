package chess.pieces;
import chess.game.*;

public class King extends Piece {

    public Type type;

    /**
     * Constructor for a King
     *
     * @param square the location of the King
     * @param colour the Colour object associated with the King
     */
    public King(Square square, Colour colour) {
        super(square, colour);
        type = Type.KING;
    }

    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "K";
        } else return "k";
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
     * Determines if the King is moving only one Square in any direction
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.x - this.square.x);
        int diff_y = Math.abs(finalSquare.y - this.square.y);
        // nicht im Schach: !isChecked()
        // wenn noch kein Move + Rook kein Move + kein Piece im Weg + kein Angriff auf Felder => darf Castling
        if(!isChecked()){
            return diff_x == 1 || diff_y == 1;
        }
        return false;
    }

    /**
     * Draws a path of the King's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param finalSquare the final location
     * @return a Square array of the path
     */
    @Override
    public int[][] drawMove(Square finalSquare) {
        // King has no path, moves only one Square at a time
        int squares = 0;
        return new int[2][squares];
    }

    public boolean isChecked() {
        return false;
    }

    public boolean isCheckMate() {
        // egal welcher Move mit irgendeinem Piece, der King ist immer noch isChecked():true
        return false;
    }

    public boolean hasMoved() {
        return false;
    }

    

}
