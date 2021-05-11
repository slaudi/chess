package chess.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The TypeTest class test the methods of the Type class
 */
import static chess.game.Type.PAWN;

import static org.junit.jupiter.api.Assertions.*;

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