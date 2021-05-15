package chess.pieces;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Square;
import chess.game.Type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.game.Label.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The KnightTest class test the methods of the Knight class
 */
public class KnightTest {
    public Game game;
    public Knight knight;
    public Square squareC4;
    public Square squareF4;
    public Square squareH6;
    public Square squareE6;
    public Square squareB2;

    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = new Square(c4, 2, 4);
        knight = new Knight(squareC4, Colour.WHITE);
        squareF4 = new Square(f4, 5, 4);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
        squareB2 = new Square(b2, 1, 6);
    }

    @Test
    public void getSquare() {
        assertEquals(squareC4, knight.getSquare());
    }

    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        knight.setSquare(squareC4);
        assertEquals(squareC4, knight.getSquare());
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, knight.getColour());
    }

    @Test
    public void getType() {
        assertEquals(Type.KNIGHT, knight.getType());
    }

    @Test
    public void getHasMoved() {
        assertFalse(knight.isHasMoved());
    }

    @Test
    public void setHasMoved() {
        knight.setHasMoved(true);
        assertTrue(knight.isHasMoved());
    }

    @Test
    public void isPiecesMove() {
        assertTrue(knight.isPiecesMove(squareB2));
    }

    @Test
    public void isHasMoved() {
        knight.setHasMoved(true);
        assertTrue(knight.isHasMoved());
    }

    @Test
    public void testToString() {
        assertEquals("N", knight.toString());
    }
}
