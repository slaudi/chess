package chess.game;

import chess.pieces.Piece;

/**
 * Square class representing a single square of current Chess-Board.
 */
public class Square {
    public int x;
    public int y;
    public Label label;
    public Piece occupiedBy = null;

    /**
     * Create a single square instance when starting a new Game.
     *
     * @param label Name of Square.
     * @param x     X-Coordinate of this Square (line from left to right).
     * @param y     Y-Coordinate of this Square (column from top to bottom).
     */
    public Square(Label label, int x, int y) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public String toString(){
        if(this.occupiedBy == null){
            return " ";
        }
        else {
            return this.occupiedBy.toString();
        }
    }

    public static int getXFromLabel(Label label){
        char i = Label.toString(label).charAt(0);
        int result;
        if (i == 'a') result = 0;
        else if (i == 'b') result = 1;
        else if (i == 'c') result = 2;
        else if (i == 'd') result = 3;
        else if (i == 'e') result = 4;
        else if (i == 'f') result = 5;
        else if (i == 'g') result = 6;
        else if (i == 'h') result = 7;
        else return 9;
        return result;
    }

    public static int getYFromLabel(Label label){
        char i = Label.toString(label).charAt(1);
        int result;
        if (i == '8') result = 0;
        else if (i == '7') result = 1;
        else if (i == '6') result = 2;
        else if (i == '5') result = 3;
        else if (i == '4') result = 4;
        else if (i == '3') result = 5;
        else if (i == '2') result = 6;
        else if (i == '1') result = 7;
        else result = 9;
        return result;
    }
    public static int getXFromString(String string){
        char i = string.charAt(0);
        int result;
        if (i == 'a') result = 0;
        else if (i == 'b') result = 1;
        else if (i == 'c') result = 2;
        else if (i == 'd') result = 3;
        else if (i == 'e') result = 4;
        else if (i == 'f') result = 5;
        else if (i == 'g') result = 6;
        else if (i == 'h') result = 7;
        else result = 9;
        return result;
    }

    public static int getYFromString(String string){
        char i = string.charAt(1);
        int result;
        if (i == '8') result = 0;
        else if (i == '7') result = 1;
        else if (i == '6') result = 2;
        else if (i == '5') result = 3;
        else if (i == '4') result = 4;
        else if (i == '3') result = 5;
        else if (i == '2') result = 6;
        else if (i == '1') result = 7;
        else result = 9;
        return result;
    }
}