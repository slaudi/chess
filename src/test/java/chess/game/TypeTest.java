package chess.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static chess.game.Type.PAWN;

/**
 * The TypeTest class test the methods of the Type class
 */
public class TypeTest {

    /**
     * tests output of typeValues
     */
    @Test
    public void values() {
        assertEquals("PAWN", PAWN.name());
    }

    /**
     * tests output of typevalues
     */
    @Test
    public void valueOf() {
        assertEquals(PAWN, Type.valueOf("PAWN"));
    }

}