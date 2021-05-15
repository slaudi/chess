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
 * The KingTest class test the methods of the King class
 */
public class KingTest {

    public King king;
    public Square square;
    public Square squareC3;
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        square = new Square(c4, 2, 4);
        king = new King(square, Colour.WHITE);
        squareC3 = new Square(c3,2, 5);
    }

    @Test
    public void getSquare() {
        assertEquals(square, king.getSquare());
    }

    @Test
    public void setSquare() {
        square = new Square(g5,7,3);
        king.setSquare(square);
        assertEquals(square, king.getSquare());
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, king.getColour());
    }

    @Test
    public void getType() {
        assertEquals(Type.KING, king.getType());
    }

    @Test
    public void hasNotMoved() {
        assertTrue(king.hasNotMoved());
    }

    @Test
    public void setHasMoved() {
        king.setNotMoved(false);
        assertFalse(king.hasNotMoved());
    }

    @Test
    public void isPiecesMove() {
        assertTrue(king.isPiecesMove(squareC3, game.chessBoard));
    }

    @Test
    public void isHasMoved() {
        king.setNotMoved(false);
        assertFalse(king.hasNotMoved());
    }

    @Test
    public void testToString() {
        assertEquals("K", king.toString());
    }
}
