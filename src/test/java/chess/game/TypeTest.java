package chess.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static chess.game.Type.PAWN;

/**
 * The TypeTest class tests the methods of the Type class
 */
public class TypeTest {

    /**
     * tests output of Type values
     */
    @Test
    public void values() {
        assertEquals("PAWN", PAWN.name());
    }

    /**
     * tests output of Type values
     */
    @Test
    public void valueOf() {
        assertEquals(PAWN, Type.valueOf("PAWN"));
    }

}