package pieces;

import chess.game.Colour;
import chess.game.Square;
import chess.game.Type;
import chess.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static chess.game.Label.c4;
import static chess.game.Label.g5;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    private Queen queen;
    private Square square;

    @BeforeEach
    void setUp() {
        square = new Square(c4, 2, 4);
        queen = new Queen(square, Colour.WHITE);
    }

    @Test
    void getSquare() {
        Assertions.assertEquals(square, queen.getSquare());
    }

    @Test
    void setSquare() {
        square = new Square(g5,7,3);
        queen.setSquare(square);
        Assertions.assertEquals(square, queen.getSquare());
    }

    @Test
    void getColour() {
        Assertions.assertEquals(Colour.WHITE, queen.getColour());
    }

    @Test
    void getType() {
        Assertions.assertEquals(Type.QUEEN, queen.getType());
    }

    @Test
    void getHasMoved() {
        assertFalse(queen.getHasMoved());
    }

    @Test
    void setHasMoved() {
        queen.setHasMoved(true);
        assertTrue(queen.getHasMoved());
    }

    // TODO: test moves of the pieces
    @Test
    void isPiecesMove() {

    }
}
