package chess.game;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static chess.game.Colour.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class ColourTest {


    @Test
    void values() {
        Assertions.assertEquals("WHITE", WHITE.name());
    }

    @Test
    void valueOf() {
        Assertions.assertEquals(Colour.WHITE, Colour.valueOf("WHITE"));
    }
}