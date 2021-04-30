package chess.game;

/**
 * Move class to calculate and save the movement of a Chess-Piece.
 */
public class Move {
    public Square startSquare;
    public Square finalSquare;

    /**
     * Constructor of Move-Class.
     *
     * @param start The Square the Movement of Chess-Piece starts at.
     * @param finish The Square the Movement goes to.
     */
    public Move(Square start, Square finish) {
        this.startSquare = start;
        this.finalSquare = finish;
    }
}
