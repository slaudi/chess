package chess.game;

import chess.pieces.Piece;

/**
 * The Square class represents a single square of the current chess board. It knows its
 * coordinates in the Board array and if it is occupied by a figure (and which one) or not.
 */
public class Square {
    private final int x;
    private final int y;
    private Piece occupiedBy = null;
    private final Label label;

    /**
     * Constructor for creating a single square when starting a new Game.
     *
     * @param label The Label for the Square. Containing a letter and a digit.
     * @param x The x coordinate of this Square in the Board array (line from left to right).
     * @param y The y coordinate of this Square in the Board array (column from top to bottom).
     */
    public Square(Label label, int x, int y) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public Label getLabel(){
        return this.label;
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
     * A function determining the x-coordinate of a selected square from the console input.
     *
     * @param input The console input as a String.
     * @return int The x-coordinate of the selected square.
     */
    public static int getXFromString(String input){
        char i = input.charAt(0);
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
     * A function determining the y-coordinate of a selected square from the console input.
     *
     * @param input The console input as a String.
     * @return int The y-coordinate of the selected square.
     */
    public static int getYFromString(String input) {
        char i = input.charAt(1);
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

    /**
     *
     * Helper-Method generating a Character from Squares Y-Coordinate
     * @return returns a Character which names line on chessboard
     */
    public char getCharFromY() {
        char y;
        if (this.y == 0) {
            y = '8';
        } else if (this.y == 1) {
            y = '7';
        } else if (this.y == 2) {
            y = '6';
        } else if (this.y == 3) {
            y = '5';
        } else if (this.y == 4) {
            y = '4';
        } else if (this.y == 5) {
            y = '3';
        } else if (this.y == 6) {
            y = '2';
        } else {
            y = '1';
        }
        return y;
    }

    /**
     * Helper-Method generating a Character from Squares X-Coordinate
     * @return returns a Character which names column on chessboard
     */
    public char getCharFromX() {
        char x;
        if (this.x == 0) {
            x = 'a';
        } else if (this.x == 1) {
            x = 'b';
        } else if (this.x == 2) {
            x = 'c';
        } else if (this.x == 3) {
            x = 'd';
        } else if (this.x == 4) {
            x = 'e';
        } else if (this.x == 5) {
            x = 'f';
        } else if (this.x == 6) {
            x = 'g';
        } else {
            x = 'h';
        }
        return x;
    }
}