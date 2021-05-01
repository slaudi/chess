package chess.game;

import chess.pieces.Piece;

/**
 * Square class representing a single square of current Chess-Board.
 */
public class Square {
    public int x;
    public int y;
    public Label label;
    public boolean occupied;
    public Piece occupiedBy;

    /**
     * Create a single square instance when starting a new Game.
     *
     * @param label Name of Square.
     * @param x X-Coordinate of this Square (line from left to right).
     * @param y Y-Coordinate of this Square (column from top to bottom).
     */
    public Square(Label label, int x, int y) {
        this.x = x;
        this.y = y;
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