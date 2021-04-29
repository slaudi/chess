package chess.game;

import chess.pieces.Piece;

/**
 * Square class representing a single square of current Chess-Board.
 */
public class Square {
    public Label label;
    public boolean occupied;
    public Piece occupiedBy;

    /**
     * Create a single square instance when starting a new Game.
     *
     * @param label Name of Square.
     */
    public Square(Label label) {
        this.label = label;
        this.occupied = false;
        this.occupiedBy = null;
    }

    public String toString(){
        if(this.occupiedBy == null){
            return " ";
        }
        else {
            return this.occupiedBy.toString();
        }
    }
}