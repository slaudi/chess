package pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;
import chess.pieces.King;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.game.Label.c4;
import static chess.game.Label.g5;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The KingTest class test the methods of the King class
 */
public class KingTest {

    private King king;
    private Square square;

    @BeforeEach
    void setUp() {
        square = new Square(c4, 2, 4);
        king = new King(square, Colour.WHITE);
    }

    @Test
    void getSquare() {
        Assertions.assertEquals(square, king.getSquare());
    }

    @Test
    void setSquare() {
        square = new Square(g5,7,3);
        king.setSquare(square);
        Assertions.assertEquals(square, king.getSquare());
    }

    @Test
    void getColour() {
        Assertions.assertEquals(Colour.WHITE, king.getColour());
    }

    @Test
    void getType() {
        Assertions.assertEquals(Type.KING, king.getType());
    }

    @Test
    void getHasMoved() {
        assertFalse(king.getHasMoved());
    }

    @Test
    void setHasMoved() {
        king.setHasMoved(true);
        assertTrue(king.getHasMoved());
    }

    // TODO: test moves of the pieces
    @Test
    void isPiecesMove() {

    }

}
