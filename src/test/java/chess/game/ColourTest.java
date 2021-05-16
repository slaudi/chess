package chess.game;

import org.junit.jupiter.api.Test;

import static chess.game.Colour.WHITE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The ColourTest class test the methods of the Colour class
 */
public class ColourTest {


    /**
     * tests output of values
     */
    @Test
    public void values() {
        assertEquals("WHITE", WHITE.name());
    }

    /**
     * tests output of values
     */
    @Test
    public void valueOf() {
        assertEquals(WHITE, Colour.valueOf("WHITE"));
    }
}