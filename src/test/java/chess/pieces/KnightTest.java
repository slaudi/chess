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
    Game game;
    Knight knight;
    Square squareC4;
    Square squareF4;
    Square squareH6;
    Square squareE6;
    Square squareB2;

    /**
     * setUp for each Knight test
     */
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

    /**
     * tests getter for square
     */
    @Test
    public void getSquare() {
        assertEquals(squareC4, knight.getSquare());
    }

    /**
     * tests setter for square
     */
    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        knight.setSquare(squareC4);
        assertEquals(squareC4, knight.getSquare());
    }

    /**
     * tests getter for colour
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, knight.getColour());
    }

    /**
     * tests getter for type
     */
    @Test
    public void getType() {
        assertEquals(Type.KNIGHT, knight.getType());
    }

    /**
     * tests getter for hasMoved
     */
    @Test
    public void getHasMoved() {
        assertTrue(knight.hasNotMoved());
    }

    /**
     * tests setter for hasMoved
     */
    @Test
    public void setHasMoved() {
        knight.setNotMoved(false);
        assertFalse(knight.notMoved);
    }

    /**
     * tests if movement is allowed for selected piece
     */
    @Test
    public void isPiecesMove() {
        assertTrue(knight.isPiecesMove(squareB2, game.chessBoard));
        assertTrue(knight.isPiecesMove(game.chessBoard.getSquareAt(4,3), game.chessBoard));
        assertFalse(knight.isPiecesMove(squareF4, game.chessBoard));
        // same square
        assertFalse(knight.isPiecesMove(squareC4, game.chessBoard));
    }

    /**
     * tests is piece has moved
     */
    @Test
    public void isHasMoved() {
        knight.setNotMoved(false);
        assertFalse(knight.hasNotMoved());
    }

    /**
     * tests if toStringOutput works
     */
    @Test
    public void testToString() {
        assertEquals("N", knight.toString());
    }
}
