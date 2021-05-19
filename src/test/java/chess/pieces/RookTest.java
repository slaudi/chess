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
 * The RookTest class test the methods of the Rook class
 */
public class RookTest {
    public Game game;
    public Rook rook;
    public Square squareC4;
    public Square squareF4;
    public Square squareH6;
    public Square squareE6;

    /**
     * setUp for each RookTest
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = new Square(c4, 2, 4);
        rook = new Rook(squareC4, Colour.WHITE);
        squareF4 = new Square(f4, 5, 4);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
    }

    /**
     * tests if getter for square works
     */
    @Test
    public void getSquare() {
        assertEquals(squareC4, rook.getSquare());
    }

    /**
     * tests if setter for square works
     */
    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        rook.setSquare(squareC4);
        assertEquals(squareC4, rook.getSquare());
    }

    /**
     * tests if getter for colour works
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, rook.getColour());
    }

    /**
     * tests if getter for type works
     */
    @Test
    public void getType() {
        assertEquals(Type.ROOK, rook.getType());
    }

    /**
     * tests if getter for hasMoved works
     */
    @Test
    public void getHasMoved() {
        assertTrue(rook.hasNotMoved());
    }

    /**
     * tests setter for hasMoved
     */
    @Test
    public void setHasMoved() {
        rook.setNotMoved(false);
        assertFalse(rook.hasNotMoved());
    }

    /**
     * tests if piece is allowed to move this way
     */
    @Test
    public void isPiecesMove() {
        assertTrue(rook.isPiecesMove(squareF4, game.chessBoard));
    }

    /**
     * tests if piece has already moved
     */
    @Test
    public void isHasMoved() {
        rook.setNotMoved(false);
        assertFalse(rook.hasNotMoved());
    }

    /**
     * tests if toStringOutput works
     */
    @Test
    public void testToString() {
        assertEquals("R", rook.toString());
    }

}
