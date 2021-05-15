package chess.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The LabelTest class test the methods of the Label class
 */
public class LabelTest {

    @Test
    public void contains() {
        assertTrue(Label.contains("e1"));
    }

    @Test
    public void values() {
        assertEquals(Label.values()[0], Label.a8);
    }

    @Test
    public void valueOf() {
        assertEquals(Label.valueOf("a8"), Label.a8);
    }
}