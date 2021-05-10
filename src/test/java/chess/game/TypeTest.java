package chess.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static chess.game.Type.PAWN;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void values() {
        Assertions.assertEquals("PAWN", PAWN.name());
    }

    @Test
    void valueOf() {
        Assertions.assertEquals(PAWN, Type.valueOf("PAWN"));
    }

}