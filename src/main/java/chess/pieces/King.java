package chess.pieces;
import chess.game.*;


public class King extends Piece implements Castling {

    Type type;

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
     * A function to determine if the King is printed on the chess board in upper or lower case
     * depending on the colour of it
     *
     * @return a String representing the King on the chess board
     */
    @Override
    public String toString() {
        if(this.colour == Colour.WHITE){
            return "K";
        } else return "k";
    }

    /**
     * Determines if the King is moving only one Square in any direction
     *
     * @param finalSquare the final location
     * @return a boolean indicating if the move is allowed
     */
    @Override
    public boolean isAllowedPath(Square finalSquare) {

        // TODO nicht im Schach: !isChecked()
        // Wenn nicht
        // TODO wenn noch kein Move + Rook kein Move + kein Piece im Weg + kein Angriff auf Felder => canCastle()

        return false;
    }
    @Override
    public boolean isSurroundingSquare(Square square){
        int diffX = this.square.getX() - square.getX();
        int diffY = this.square.getY() - square.getY();
        if(diffX < 2 && diffY < 2){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean pawnCanCapture(Square finalSquare) {
        return false;
    }


    public boolean pathToNotCheck(Square finalSquare) {
        int diff_x = Math.abs(finalSquare.getX() - this.square.getX());
        int diff_y = Math.abs(finalSquare.getY() - this.square.getY());
        return false;
    }


    public boolean canAttackKing(Piece enemy, Square location) {

        return false;
    }


    public boolean canCastle(Piece rook) {
        if (!this.moved){
            return false;
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
    public Square[][] drawMove(Square finalSquare) {
        // King has no path, moves only one Square at a time
        int squaresVisited = 0;
        return new Square[1][squaresVisited];
    }

}
