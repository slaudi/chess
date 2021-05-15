package chess.game;

import chess.pieces.Rook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SquareTest class test the methods of the Square class
 */
public class SquareTest {
    public Square square;

    @BeforeEach
    public void setUp() {
        square = new Square(Label.a1, 0, 7);
    }

    @Test
    public void testToString() {
        Square square = new Square(Label.a1,3,4);
        assertEquals(" ", square.toString(),"Feld sollte leer sein");
    }

    @Test
    public void getX() {
        assertEquals(0, square.getX());
    }

    @Test
    public void getY() {
        assertEquals(7, square.getY());
    }

    @Test
    public void getOccupiedBy() {
        assertNull(square.getOccupiedBy());
    }

    @Test
    public void setOccupiedBy() {
        Rook rook = new Rook(square, Colour.WHITE);
        square.setOccupiedBy(rook);
        assertEquals(rook, square.getOccupiedBy());
    }

    @Test
    public void getXFromString() {
        assertEquals(3, Square.getXFromString("d4"));
    }

    @Test
    public void getYFromString() {
        assertEquals(7, Square.getYFromString("a1"));
    }
}