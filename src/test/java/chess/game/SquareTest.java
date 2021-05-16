package chess.game;

import chess.pieces.Rook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SquareTest class test the methods of the Square class
 */
public class SquareTest {

    public Game game;
    public Square square;
    public Rook rook;

    /**
     * Set up for testing the Square class
     */
    @BeforeEach
    public void setUp() {
        game =  new Game();
        square = new Square(Label.a3,0,5);
    }

    /**
     * Tests 'toString' if an empty Square is printed empty.
     */
    @Test
    public void printEmptySquare() {
        assertEquals(" ", game.chessBoard.getBoard()[0][4].toString(),"Feld sollte leer sein");
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
    }

    /**
     * Tests 'getXFromString' if it returns 9 if the command line input is not square
     * on the board.
     */
    @Test
    public void getXFromWrongString(){
        assertEquals(9, Square.getXFromString("j9"));
    }

    /**
     * Tests 'getYFromString' if it gets the right y coordinate from the command line
     * input.
     */
    @Test
    public void getYFromString() {
        assertEquals(7, Square.getYFromString("a1"));
    }

    /**
     * Tests 'getYFromString' if it returns 9 if the command line input is not square
     * on the board.
     */
    @Test
    public void getYFromWrongString(){
        assertEquals(9, Square.getXFromString("j9"));
    }
}