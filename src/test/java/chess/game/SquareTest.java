package chess.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void testToString() {
        Square square = new Square(Label.a1,3,4);
        assertEquals(" ", square.toString(),"Feld sollte leer sein");
    }

    @Test
    void getXFromLabel(Label label) {
        assertEquals(3, Square.getXFromLabel(Label.d5));
    }

    @Test
    void getYFromLabel() {
    }

    @Test
    void getXFromString() {
    }

    @Test
    void getYFromString() {
    }
}