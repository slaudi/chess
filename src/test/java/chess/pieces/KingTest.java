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
    public Game game;

    /**
     * setUp for each KingTest
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        square = new Square(c4, 2, 4);
        king = new King(square, Colour.WHITE);
        squareC3 = new Square(c3,2, 5);
    }

    /**
     * tests getter for square
     */
    @Test
    public void getSquare() {
        assertEquals(square, king.getSquare());
    }

    /**
     * tests setter for square
     */
    @Test
    public void setSquare() {
        square = new Square(g5,7,3);
        king.setSquare(square);
        assertEquals(square, king.getSquare());
    }

    /**
     * tests getter for colour
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, king.getColour());
    }

    /**
     * tests typeGetter
     */
    @Test
    public void getType() {
        assertEquals(Type.KING, king.getType());
    }

    /**
     * tests if hasnt moved works
     */
    @Test
    public void hasNotMoved() {
        assertTrue(king.hasNotMoved());
    }

    /**
     * tests setter for hasnt moved
     */
    @Test
    public void setHasMoved() {
        king.setNotMoved(false);
        assertFalse(king.hasNotMoved());
    }

    /**
     * tests if piece is able to move as it should
     */
    @Test
    public void isPiecesMove() {
        assertTrue(king.isPiecesMove(squareC3, game.chessBoard));
    }

    /**
     * tests is two squares are the same
     */
    @Test
    public void isSameSquare(){
        assertFalse(king.isPiecesMove(square, game.chessBoard));
    }

    /**
     * tests if piece is really not allowed to move
     */
    @Test
    public void isNotPiecesMove(){
        Square square = game.chessBoard.getSquareAt(0,1);
        assertFalse(king.isPiecesMove(square, game.chessBoard));
    }

    /**
     * tests if piece has moved
     */
    @Test
    public void isHasMoved() {
        king.setNotMoved(false);
        assertFalse(king.hasNotMoved());
    }

    /**
     * tests if output to string works
     */
    @Test
    public void testToString() {
        assertEquals("K", king.toString());
    }

    /**
     * tests if movingDirection is true
     */
    @Test
    public void movingDirection() {
        int[][] testInt = king.movingDirection(squareC3);
        assertNotNull(testInt);
    }

}
