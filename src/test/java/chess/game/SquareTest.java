package chess.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The SquareTest class test the methods of the Square class
 */
class SquareTest {

    @Test
    void testToString() {
        Square square = new Square(Label.a1,3,4);
        Assertions.assertEquals(" ", square.toString(),"Feld sollte leer sein");
    }

    @Test
    void getXFromLabel() {

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