package chess.pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.game.Label.c4;
import static chess.game.Label.g5;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PawnTest class test the methods of the Pawn class
 */
public class PawnTest {

    private Pawn pawn;
    private Square square;

    @BeforeEach
    void setUp() {
        square = new Square(c4, 2, 4);
        pawn = new Pawn(square, Colour.WHITE);
    }

    @Test
    void getSquare() {
        assertEquals(square, pawn.getSquare());
    }

    @Test
    void setSquare() {
        square = new Square(g5,7,3);
        pawn.setSquare(square);
        assertEquals(square, pawn.getSquare());
    }

    @Test
    void getColour() {
        assertEquals(Colour.WHITE, pawn.getColour());
    }

    @Test
    void getType() {
        assertEquals(Type.PAWN, pawn.getType());
    }

    @Test
    void getHasMoved() {
        assertFalse(pawn.isHasMoved());
    }

    @Test
    void setHasMoved() {
        pawn.setHasMoved(true);
        assertTrue(pawn.isHasMoved());
    }

    // TODO: test moves of the pieces
    @Test
    void isPiecesMove() {

    }
}
