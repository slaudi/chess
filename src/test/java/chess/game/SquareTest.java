package chess.game;

import chess.pieces.Piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SquareTest class test the methods of the Square class
 */
public class SquareTest {

    Game game;
    Square square;
    Square a1;
    Square b2;
    Square c3;
    Square d4;
    Square e5;
    Square f6;
    Square g7;
    Square h8;

    /**
     * Set up for testing the Square class
     */
    @BeforeEach
    public void setUp() {
        game =  new Game();
        square = new Square(Label.a3,0,5);
        a1 = new Square(Label.a1, 0, 7);
        b2 = new Square(Label.b2, 1, 6);
        c3 = new Square(Label.c3, 2, 5);
        d4 = new Square(Label.d4, 3, 4);
        e5 = new Square(Label.e5, 4, 3);
        f6 = new Square(Label.f6, 5, 2);
        g7 = new Square(Label.g7, 6, 1);
        h8 = new Square(Label.h8, 7, 0);
    }

    /**
     * Tests if square has the right label.
     */
    @Test
    public void getLabel(){
        Label labelA3 = Label.a3;
        assertEquals(labelA3,square.getLabel());
    }


    /**
     * Tests 'toString' if an empty Square is printed empty.
     */
    @Test
    public void printEmptySquare() {
        assertEquals(" ", game.chessBoard.getBoard()[0][4].toString());
    }

    /**
     * Tests 'toString' if an occupied square is printed with the Piece on it.
     */
    @Test
    public void printSquareWithPiece(){
        assertEquals("R", game.chessBoard.getBoard()[0][7].toString());
    }


    /**
     * Tests 'getX' if it returns the right x coordinate for a square.
     */
    @Test
    public void getX() {
        assertEquals(0, game.chessBoard.getBoard()[0][7].getX());
    }

    /**
     * Tests 'getY' if it returns the right y coordinate for a square.
     */
    @Test
    public void getY() {
        assertEquals(7, game.chessBoard.getBoard()[0][7].getY());
    }

    /**
     * Tests 'getOccupiedBy' if it returns the right piece or null if empty.
     */
    @Test
    public void getOccupiedBy() {
        assertNull(game.chessBoard.getBoard()[0][4].getOccupiedBy());
    }

    /**
     * Tests 'setOccupiedBy' if it sets the square to be occupied by a certain other
     * piece.
     */
    @Test
    public void setOccupiedBy() {
        Piece rook = game.chessBoard.getPieceAt(0,0);
        square.setOccupiedBy(rook);
        assertEquals(rook, square.getOccupiedBy());
    }

    /**
     * Tests 'getXFromString' if it gets the right x coordinate from the command line
     * input.
     */
    @Test
    public void getXFromString() {
        assertEquals(7, Square.getXFromString("h4"));
        assertEquals(6, Square.getXFromString("g5"));
        assertEquals(5, Square.getXFromString("f3"));
        assertEquals(4, Square.getXFromString("e3"));
    }

    /**
     * Tests 'getXFromString' if it gets the right x coordinate from the command line
     * input.
     */
    @Test
    public void getXFromString2() {
        assertEquals(3, Square.getXFromString("d2"));
        assertEquals(2, Square.getXFromString("c1"));
        assertEquals(1, Square.getXFromString("b8"));
        assertEquals(0, Square.getXFromString("a4"));
        assertEquals(9, Square.getXFromString("j9"));
    }

    /**
     * Tests 'getYFromString' if it gets the right y coordinate from the command line
     * input.
     */
    @Test
    public void getYFromString() {
        assertEquals(7, Square.getYFromString("a1"));
        assertEquals(6, Square.getYFromString("e2"));
        assertEquals(5, Square.getYFromString("f3"));
        assertEquals(4, Square.getYFromString("h4"));
    }

    /**
     * Tests 'getYFromString' if it gets the right y coordinate from the command line
     * input.
     */
    @Test
    public void getYFromString2() {
        assertEquals(3, Square.getYFromString("b5"));
        assertEquals(2, Square.getYFromString("f6"));
        assertEquals(1, Square.getYFromString("c7"));
        assertEquals(0, Square.getYFromString("d8"));
        assertEquals(9, Square.getYFromString("j9"));
    }

    /**
     * Tests if the method returns the right char from certain square for the y axes.
     */
    @Test
    public void getCharFromY() {
        assertEquals('1', a1.getCharFromY());
        assertEquals('2', b2.getCharFromY());
        assertEquals('3', c3.getCharFromY());
        assertEquals('4', d4.getCharFromY());
    }

    /**
     * Tests if the method returns the right char from certain square for the y axes.
     */
    @Test
    public void getCharFromY2() {
        assertEquals('5', e5.getCharFromY());
        assertEquals('6', f6.getCharFromY());
        assertEquals('7', g7.getCharFromY());
        assertEquals('8', h8.getCharFromY());
    }

    /**
     * Tests if the method returns the right char from certain square for the x axes.
     */
    @Test
    public void getCharFromX() {
        assertEquals('a', a1.getCharFromX());
        assertEquals('b', b2.getCharFromX());
        assertEquals('c', c3.getCharFromX());
        assertEquals('d', d4.getCharFromX());
    }

    /**
     * Tests if the method returns the right char from certain square for the x axes.
     */
    @Test
    public void getCharFromX2() {
        assertEquals('e', e5.getCharFromX());
        assertEquals('f', f6.getCharFromX());
        assertEquals('g', g7.getCharFromX());
        assertEquals('h', h8.getCharFromX());
    }
}