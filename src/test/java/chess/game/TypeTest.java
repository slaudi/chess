package chess.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static chess.game.Type.PAWN;

/**
 * The TypeTest class test the methods of the Type class
 */
class TypeTest {

    @Test
    void values() {
        assertEquals("PAWN", PAWN.name());
    }

    @Test
    void valueOf() {
        assertEquals(PAWN, Type.valueOf("PAWN"));
    }

}