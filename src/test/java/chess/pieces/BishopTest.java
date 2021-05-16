package chess.pieces;

import chess.game.*;
import static chess.game.Label.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The BishopTest class test the methods of the Bishop class
 */
public class BishopTest {

    public Bishop bishop;
    public Square finalSquare;
    public Game game;
    public Square squareF4;
    public Square squareH6;
    public Square squareE6;
    public Square squareC4;
    public Square squareA1;
    public Square squareD4;


    /**
     * setUp for each bishopTest
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        bishop = new Bishop(game.chessBoard.getFinalSquareFromInput("a1-c4"), Colour.WHITE);
        squareA1 = new Square(a1,0,7);
        squareD4 = new Square(d4,3,4);
        squareC4 = new Square(c4,2,4);
        squareF4 = new Square(f4, 5, 4);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
    }

    /**
     * tests getter for square
     */
    @Test
    public void getSquare() {
        bishop.setSquare(squareC4);
        assertEquals(squareC4, bishop.getSquare());
    }

    /**
     * tests setter for square
     */
    @Test
    public void setSquare() {
        bishop.setSquare(squareF4);
        assertEquals(squareF4, bishop.getSquare());
        bishop.setSquare(squareC4);
        assertEquals(squareC4, bishop.getSquare());
    }

    /**
     * tests getter for colour
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, bishop.getColour());
    }

    /**
     * tests getter for type
     */
    @Test
    public void getType() {
        assertEquals(Type.BISHOP, bishop.getType());
    }

    /**
     * tests getter for movedPiece
     */
    @Test
    public void getHasMoved() {
        assertTrue(bishop.hasNotMoved());
    }

    /**
     * tests setter for MovedPiece
     */
    @Test
    public void setHasMoved() {
        bishop.setNotMoved(false);
        assertFalse(bishop.hasNotMoved());
    }

    /**
     * tests if upLeft Movement is possible
     */
    // Bishops movement
    @Test
    public void upLeftMovement() {
        finalSquare = new Square(a6,0,2);
        assertTrue(bishop.isPiecesMove(finalSquare, game.chessBoard));
    }

    /**
     * tests if upRight movement is possible
     */
    @Test
    public void upRightMovement() {
        finalSquare = new Square(f7,5,1);
        assertTrue(bishop.isPiecesMove(finalSquare, game.chessBoard));
    }

    /**
     * tests if downLeft movement is possible
     */
    @Test
    public void downLeftMovement() {
        finalSquare = new Square(a2,0,6);
        assertTrue(bishop.isPiecesMove(finalSquare, game.chessBoard));
    }

    /**
     * tests if downRight movement is possible
     */
    @Test
    public void downRightMovement() {
        finalSquare = new Square(f1,5,7);
        assertTrue(bishop.isPiecesMove(finalSquare, game.chessBoard));
    }

    /**
     * tests is vertical movement is possible
     */
    @Test
    public void notAllowedVerticalMovement() {
        finalSquare = new Square(c8,2,0);
        assertFalse(bishop.isPiecesMove(finalSquare, game.chessBoard));
    }

    /**
     * tests is horizontal movement is possible
     */
    @Test
    public void notAllowedHorizontalMovement() {
        finalSquare = new Square(a3,0,5);
        assertFalse(bishop.isPiecesMove(finalSquare, game.chessBoard));
    }

    /**
     * tests not allowed movement
     */
    @Test
    public void notAllowedLMovement() {
        finalSquare = new Square(b6,1,2);
        assertFalse(bishop.isPiecesMove(finalSquare, game.chessBoard));
    }

    /**
     * tests not allowed leaping
     */
    @Test
    public void notAllowedLeaping() {
        game = new Game();
        bishop.getSquare().setOccupiedBy(null);
        bishop = (Bishop) game.chessBoard.getBoard()[5][0].getOccupiedBy();
        finalSquare = new Square(a3,0,5);
        assertFalse(game.isMoveAllowed(bishop,finalSquare));
    }


    /**
     * tests if hasnt moved is true
     */
    @Test
    public void hasNotMoved() {
        bishop.setNotMoved(false);
        assertFalse(bishop.hasNotMoved());
    }

    /**
     * tests if toString works
     */
    @Test
    public void testToString() {
        assertEquals("B", bishop.toString());
    }


    /**
     * tests moving direction
     */
    @Test
    public void movingDirection() {
        int[][] testInt = bishop.movingDirection(squareE6);
        assertNotNull(testInt);
    }


}
