package chess.pieces;
import chess.game.*;

public class King extends Piece {

    public Type type;

    /**
     * Constructor for a King
     *
     * @param x      the x location of the King
     * @param y      the y location of the King
     * @param colour the Colour object associated with the King
     */
    public King(int x, int y, Colour colour) {
        super(x, y, colour);
        type = Type.KING;
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
     * @param last_x the final x location
     * @param last_y the final y location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedMove(int last_x, int last_y) {
        int diff_x = Math.abs(last_x - this.x);
        int diff_y = Math.abs(last_y - this.y);
        // nicht im Schach: !isChecked()
        // wenn noch kein Move + Rook kein Move + kein Piece im Weg + kein Angriff auf Felder => darf Castling
        if(!isChecked()){
            return (diff_x == 1 || diff_y == 1);
        }
        return false;
    }

    /**
     * Draws a path of the King's move and stores it
     * to later determine if another piece is in it's path
     *
     * @param first_x   the first x position
     * @param first_y   the first y position
     * @param last_x   the final x position
     * @param last_y   the final y position
     * @return a Square array of the path
     */
    @Override
    public int[][] drawMove(int first_x, int first_y, int last_x, int last_y) {
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
