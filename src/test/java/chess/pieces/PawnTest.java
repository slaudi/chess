package chess.pieces;

import chess.game.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static chess.game.Label.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PawnTest class test the methods of the Pawn class
 */
public class PawnTest {
    public Game game;
    public Pawn pawn;
    public Square squareC4;
    public Square squareF4;
    public Square squareH6;
    public Square squareE6;
    public Square squareC5;
    public Square squareC8;
    public Stack<Move> moveHistory;

    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = new Square(c4, 2, 4);
        pawn = new Pawn(squareC4, Colour.WHITE);
        squareF4 = new Square(f4, 5, 4);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
        squareC5 = new Square(c5, 2, 3);
        squareC8 = new Square(c8, 2, 0);
        moveHistory = new Stack<>();
    }

    @Test
    public void getSquare() {
        assertEquals(squareC4, pawn.getSquare());
    }

    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        pawn.setSquare(squareC4);
        assertEquals(squareC4, pawn.getSquare());
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, pawn.getColour());
    }

    @Test
    public void getType() {
        assertEquals(Type.PAWN, pawn.getType());
    }

    @Test
    public void getHasMoved() {
        assertFalse(pawn.isHasMoved());
    }

    @Test
    public void setHasMoved() {
        pawn.setHasMoved(true);
        assertTrue(pawn.isHasMoved());
    }

    @Test
    public void isPiecesMove() {
        assertTrue(pawn.isPiecesMove(squareC5));
    }

    @Test
    public void isHasMoved() {
        pawn.setHasMoved(true);
        assertTrue(pawn.isHasMoved());
    }

    @Test
    public void testToString() {
        assertEquals("P", pawn.toString());
    }

    @Test
    public void canCapture() {
        assertFalse(pawn.canCapture(moveHistory));
    }

    @Test
    public void isEnPassant() {
        assertFalse(pawn.isEnPassant(squareC5, moveHistory));
    }

    @Test
    public void promotionPossible() {
        assertTrue(pawn.promotionPossible(squareC8));
    }
}
