package pieces;

import chess.game.*;
import chess.pieces.Bishop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.game.Label.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The BishopTest class test the methods of the Bishop class
 */
public class BishopTest {

    private Bishop bishop;
    private Square square;
    private Square finalSquare;
    private Game game;


    @BeforeEach
    void setUp() {
        game = new Game();
        square = new Square(c4, 2, 4);
        bishop = new Bishop(square, Colour.WHITE);
    }

    @Test
    void getSquare() {
        Assertions.assertEquals(square, bishop.getSquare());
    }

    @Test
    void setSquare() {
        square = new Square(g5,7,3);
        bishop.setSquare(square);
        Assertions.assertEquals(square, bishop.getSquare());
    }

    @Test
    void getColour() {
        Assertions.assertEquals(Colour.WHITE, bishop.getColour());
    }

    @Test
    void getType() {
        Assertions.assertEquals(Type.BISHOP, bishop.getType());
    }

    @Test
    void getHasMoved() {
        assertFalse(bishop.isHasMoved());
    }

    @Test
    void setHasMoved() {
        bishop.setHasMoved(true);
        assertTrue(bishop.isHasMoved());
    }

    // Bishops movement
    @Test
    void upLeftMovement() {
        finalSquare = new Square(a6,0,2);
        Assertions.assertTrue(bishop.isPiecesMove(finalSquare));
    }

    @Test
    void upRightMovement() {
        finalSquare = new Square(f7,5,1);
        Assertions.assertTrue(bishop.isPiecesMove(finalSquare));
    }

    @Test
    void downLeftMovement() {
        finalSquare = new Square(a2,0,6);
        Assertions.assertTrue(bishop.isPiecesMove(finalSquare));
    }

    @Test
    void downRightMovement() {
        finalSquare = new Square(f1,5,7);
        Assertions.assertTrue(bishop.isPiecesMove(finalSquare));
    }

    @Test
    void notAllowedVerticalMovement() {
        finalSquare = new Square(c8,2,0);
        Assertions.assertFalse(bishop.isPiecesMove(finalSquare));
    }

    @Test
    void notAllowedHorizontalMovement() {
        finalSquare = new Square(a3,0,5);
        Assertions.assertFalse(bishop.isPiecesMove(finalSquare));
    }

    @Test
    void notAllowedLMovement() {
        finalSquare = new Square(b6,1,2);
        Assertions.assertFalse(bishop.isPiecesMove(finalSquare));
    }

    @Test
    void notAllowedLeaping() {
        bishop.getSquare().setOccupiedBy(null);
        bishop = ((Bishop) game.board.getChessBoard()[5][0].getOccupiedBy());
        finalSquare = new Square(a3,0,5);
        Assertions.assertFalse(game.isMoveAllowed(bishop,finalSquare));
    }

}
