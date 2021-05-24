package chess.game;

import chess.pieces.Rook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SquareTest class test the methods of the Square class
 */
public class SquareTest {

    Game game;
    Square square;
    Rook rook;

    /**
     * Set up for testing the Square class
     */
    @BeforeEach
    public void setUp() {
        game =  new Game();
        square = new Square(Label.a3,0,5);
    }

    /**
     * Tests if the Square has the right colour.
     */
    @Test
    public void getColour(){
        assertEquals(Colour.WHITE, game.chessBoard.getSquareAt(0,0).getColour());
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
        assertEquals(3, Square.getYFromString("b5"));
        assertEquals(2, Square.getYFromString("f6"));
        assertEquals(1, Square.getYFromString("c7"));
        assertEquals(0, Square.getYFromString("d8"));
        assertEquals(9, Square.getYFromString("j9"));
    }
}