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

    /**
     * setUp for each QueenTest
     */
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

    /**
     * tests if getter for square works
     */
    @Test
    public void getSquare() {
        assertEquals(squareC4, queen.getSquare());
    }

    /**
     * tests setter for square
     */
    @Test
    public void setSquare() {
        Square squareH5 = new Square(h5,7,3);
        queen.setSquare(squareH5);
        assertEquals(squareH5, queen.getSquare());
    }

    /**
     * tests getter for colour
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, queen.getColour());
    }

    /**
     * tests getter for type
     */
    @Test
    public void getType() {
        assertEquals(Type.QUEEN, queen.getType());
    }

    /**
     * tests if hasMoved works
     */
    @Test
    public void getHasMoved() {
        assertTrue(queen.hasNotMoved());
    }

    /**
     * tests setter for hasMoved
     */
    @Test
    public void setHasMoved() {
        queen.setNotMoved(false);
        assertFalse(queen.hasNotMoved());
    }

    /**
     * tests if piece is allowed to move this way
     */
    @Test
    public void isPiecesMove() {
        assertTrue(queen.isPiecesMove(squareE6, game.chessBoard));
    }

    /**
     * tests if piece already moved
     */
    @Test
    public void isHasMoved() {
        queen.setNotMoved(true);
        assertTrue(queen.hasNotMoved());
    }

    /**
     * tests if toStringOutput works
     */
    @Test
    public void testToString() {
        assertEquals("Q", queen.toString());
    }

    /**
     * tests computation of moving direction
     */
    @Test
    public void movingDirection() {
        int[][] testInt = queen.movingDirection(squareE6);
        assertNotNull(testInt);

        Square squareH4 = new Square(a4,7,4);
        testInt = queen.movingDirection(squareH4);

        //horizontally right
        int[][] test = new int[1][2];
        test[0][0] = 1;
        test[0][1] = 0;

        //horizontally left
        Square squareA4 = new Square(a4,0,4);
        testInt = queen.movingDirection(squareA4);
        //horizontally left
        test = new int[1][2];
        test[0][0] = -1;
        test[0][1] = 0;
        assertArrayEquals(test, testInt);

        //vertically up
        Square squareC6 = new Square(c6,2,2);
        testInt = queen.movingDirection(squareC6);
        test[0][0] = 0;
        test[0][1] = -1;
        assertArrayEquals(test, testInt);

        //diagonally left down
        Square squareA2 = new Square(a2,0,6);
        testInt = queen.movingDirection(squareA2);
        test[0][0] = -1;
        test[0][1] = 1;
        assertArrayEquals(test, testInt);
    }
}
