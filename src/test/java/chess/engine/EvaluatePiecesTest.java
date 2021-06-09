package chess.engine;

import chess.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EvaluatePiecesTest {

    Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /**
     *
     */
    @Test
    public void evaluateBoard() {
        EvaluatePieces.evaluateBoard(game, game.currentPlayer.getColour());
        assertEquals(20, EvaluatePieces.evaluateBoard(game, game.currentPlayer.getColour()));
    }
}
