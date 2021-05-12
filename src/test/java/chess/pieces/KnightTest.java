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
 * The KnightTest class test the methods of the Knight class
 */
public class KnightTest {
    private Knight knight;
    private Square square;

    @BeforeEach
    void setUp() {
        square = new Square(c4, 2, 4);
        knight = new Knight(square, Colour.WHITE);
    }

    @Test
    void getSquare() {
        assertEquals(square, knight.getSquare());
    }

    @Test
    void setSquare() {
        square = new Square(g5,7,3);
        knight.setSquare(square);
        assertEquals(square, knight.getSquare());
    }

    @Test
    void getColour() {
        assertEquals(Colour.WHITE, knight.getColour());
    }

    @Test
    void getType() {
        assertEquals(Type.KNIGHT, knight.getType());
    }

    @Test
    void getHasMoved() {
        assertFalse(knight.isHasMoved());
    }

    @Test
    void setHasMoved() {
        knight.setHasMoved(true);
        assertTrue(knight.isHasMoved());
    }

    // TODO: test moves of the pieces
    @Test
    void isPiecesMove() {

    }
}
