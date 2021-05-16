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
 * The QueenTest class test the methods of the Queen class
 */
public class QueenTest {
    public Game game;
    public Queen queen;
    public Square squareC4;
    public Square squareF4;
    public Square squareH6;
    public Square squareE6;
    public Square squareB2;

    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = new Square(c4, 2, 4);
        queen = new Queen(squareC4, Colour.WHITE);
        squareF4 = new Square(f4, 5, 4);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
        squareB2 = new Square(b2, 1, 6);
    }

    @Test
    public void getSquare() {
        assertEquals(squareC4, queen.getSquare());
    }

    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        queen.setSquare(squareC4);
        assertEquals(squareC4, queen.getSquare());
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, queen.getColour());
    }

    @Test
    public void getType() {
        assertEquals(Type.QUEEN, queen.getType());
    }

    @Test
    public void getHasMoved() {
        assertTrue(queen.hasNotMoved());
    }

    @Test
    public void setHasMoved() {
        queen.setNotMoved(false);
        assertFalse(queen.hasNotMoved());
    }

    @Test
    public void isPiecesMove() {
        assertTrue(queen.isPiecesMove(squareE6, game.chessBoard));
    }

    @Test
    public void isHasMoved() {
        queen.setNotMoved(true);
        assertTrue(queen.hasNotMoved());
    }

    @Test
    public void testToString() {
        assertEquals("Q", queen.toString());
    }

    @Test
    public void movingDirection() {
        int[][] testInt = queen.movingDirection(squareE6);
        assertTrue(testInt instanceof int[][]);
    }
}
