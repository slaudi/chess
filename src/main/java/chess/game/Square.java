package chess.game;

import chess.pieces.Piece;

/**
 * Square class representing a single square of current Chess-Board.
 */
public class Square {
    private final int x;
    private final int y;
    Label label;
    private Piece occupiedBy = null;

    /**
     * Constructor for a single square instance when starting a new Game.
     *
     * @param label Name of Square.
     * @param x x coordinate of this Square (line from left to right).
     * @param y y oordinate of this Square (column from top to bottom).
     */
    public Square(Label label, int x, int y) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    @Override
    public String toString(){
        if(this.occupiedBy == null){
            return " ";
        }
        else {
            return this.occupiedBy.toString();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Piece getOccupiedBy() {
        return this.occupiedBy;
    }

    public void setOccupiedBy(Piece occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    /**
     * Get x-Coordinate from String
     * @param string String to extract x-Coordinate from.
     * @return x-Coordinate from String
     */
    public static int getXFromString(String string){
        char i = string.charAt(0);
        int result;
        if (i == 'a') {
            result = 0;
        } else if (i == 'b') {
            result = 1;
        } else if (i == 'c') {
            result = 2;
        } else if (i == 'd') {
            result = 3;
        } else if (i == 'e') {
            result = 4;
        } else if (i == 'f') {
            result = 5;
        } else if (i == 'g') {
            result = 6;
        } else if (i == 'h') {
            result = 7;
        } else {
            result = 9;
        }
        return result;
    }

    /**
     * Get y-Coordinate from String
     * @param string String to extract y-Coordinate from.
     * @return y-Coordinate from String
     */
    public static int getYFromString(String string){
        char i = string.charAt(1);
        int result;
        if (i == '8') {
            result = 0;
        } else if (i == '7') {
            result = 1;
        } else if (i == '6') {
            result = 2;
        } else if (i == '5') {
            result = 3;
        } else if (i == '4') {
            result = 4;
        } else if (i == '3') {
            result = 5;
        } else if (i == '2') {
            result = 6;
        } else if (i == '1') {
            result = 7;
        } else {
            result = 9;
        }
        return result;
    }
}