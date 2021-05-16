package chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The PlayerTest class test the methods of the Player class
 */
class PlayerTest {
    public Player playerB;
    public Player playerW;
    public Game game;

    @BeforeEach
    public void setUp() {
        playerB = new Player(Colour.BLACK);
        playerW = new Player(Colour.WHITE);
        game = new Game();
    }

    @Test
    public void setColour() {
        playerB.setColour(Colour.WHITE);
        assertEquals(Colour.WHITE, playerB.getColour());
    }

    @Test
    public void setInCheck() {
        playerB.setInCheck(true);
        assertTrue(playerB.inCheck);
    }

    @Test
    public void setLoser() {
        playerW.setLoser(true);
        assertTrue(playerW.loser);
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, playerW.getColour());
    }

    @Test
    public void isInCheck() {
        playerW.setInCheck(true);
        assertTrue(playerW.isInCheck());
    }

    @Test
    public void isLoser() {
        playerB.setLoser(true);
        assertTrue(playerB.isLoser());
    }

    @Test
    public void getAlliedPieces() {
        assertNotEquals(playerW.getAlliedPieces(game.getBeatenPieces(), game.chessBoard), playerB.getAlliedPieces(game.getBeatenPieces(), game.chessBoard));
    }

    @Test
    public void getEnemyPieces() {
        assertNotEquals(playerW.getEnemyPieces(game.getBeatenPieces(), game.chessBoard), playerB.getEnemyPieces(game.getBeatenPieces(), game.chessBoard));
    }
}