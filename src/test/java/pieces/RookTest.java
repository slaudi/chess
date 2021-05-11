package pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;
import chess.pieces.Rook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.game.Label.c4;
import static chess.game.Label.g5;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The RookTest class test the methods of the Rook class
 */
public class RookTest {

    private Rook rook;
    private Square square;

    @BeforeEach
    void setUp() {
        square = new Square(c4, 2, 4);
        rook = new Rook(square, Colour.WHITE);
    }

    @Test
    void getSquare() {
        assertEquals(square, rook.getSquare());
    }

    @Test
    void setSquare() {
        square = new Square(g5,7,3);
        rook.setSquare(square);
        assertEquals(square, rook.getSquare());
    }

    @Test
    void getColour() {
        assertEquals(Colour.WHITE, rook.getColour());
    }

    @Test
    void getType() {
        assertEquals(Type.ROOK, rook.getType());
    }

    @Test
    void getHasMoved() {
        assertFalse(rook.isHasMoved());
    }

    @Test
    void setHasMoved() {
        rook.setHasMoved(true);
        assertTrue(rook.isHasMoved());
    }

    // TODO: test moves of the pieces
    @Test
    void isPiecesMove() {

    }
}
