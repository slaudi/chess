package chess.game;

import chess.pieces.Piece;

/**
 * Move class to calculate and save the movement of a Chess-Piece.
 */
public class Move {
    public Square startSquare;
    public Square finalSquare;
    public Piece movingPiece;

    /**
     * Constructor of Move-Class.
     *
     * @param start The Square the Movement of Chess-Piece starts at.
     * @param finish The Square the Movement goes to.
     */
    public Move(Square start, Square finish) {
        this.startSquare = start;
        this.finalSquare = finish;
        this.movingPiece = start.occupiedBy;
    }

    /**
     * Checks if Console Input is a syntactical correct Move.
     *
     * @param consoleInput Input of active Player as a String.
     *
     * @return boolean
     */
    public static boolean isValidMove(String consoleInput){
        if(consoleInput.length() > 4) {
            if (consoleInput.charAt(2) == '-') {
                return Label.contains(consoleInput.substring(0, 2)) && Label.contains(consoleInput.substring(3, 5));
            } else return false;
        } else return false;
    }


    public boolean isAllowedMove() {
        return (this.movingPiece.isAllowedPath(this.finalSquare) && isPathEmpty() );
    }


    public boolean isPathEmpty() {
        Square[][] path = this.movingPiece.drawMove(this.finalSquare);
        boolean emptyPath = true;

        for(int i = 0; i < path.length; i++) {
            if (path[0][i].occupiedBy == null){
                emptyPath = false;
                break;
            }
        }
        return emptyPath;
    }
}
