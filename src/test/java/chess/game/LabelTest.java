package chess.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The LabelTest class test the methods of the Label class
 */
public class LabelTest {

    /**
     * Tests 'contains' if Label contains the element.
     */
    @Test
    public void contains() {
        assertTrue(Label.contains("e1"));
    }

    /**
     * Tests 'contains' if Label doesn't contain the element.
     */
    @Test
    public void containsNot(){
        assertFalse(Label.contains("i6"));
    }

    /**
     * Tests if a value is correctly assigned.
     */
    @Test
    public void sameValue() {
        assertEquals(Label.values()[0], Label.a8);
    }

    /**
     * Tests if a value is different values are recognised.
     */
    @Test
    public void notSameValue() {
        assertNotEquals(Label.valueOf("d6"), Label.a8);
    }
}