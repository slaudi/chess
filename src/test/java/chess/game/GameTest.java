package chess.game;

import chess.pieces.King;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The GameTest class test the methods of the Game class
 */
public class GameTest {

    public Game game1;
    public Game game2;

    @BeforeEach
    public void setUp() {
        game1 = new Game();
        game2 = new Game();
    }

    @Test
    public void processMove() {
        game1.processMove(this.game1.chessBoard.getStartSquareFromInput("e2-e3"), this.game1.chessBoard.getFinalSquareFromInput("e2-e3"), 'Q');
        assertNotEquals(game1, game2);

    }

    @Test
    public void isMoveAllowed() {
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getMovingPieceFromInput("e1-e5"), game1.chessBoard.getFinalSquareFromInput("e1-e5")));
    }

    @Test
    public void isADraw() {
        assertFalse(game1.isADraw());
    }

    @Test
    public void isInCheck() {
        assertFalse(game1.isInCheck());
    }

    @Test
    public void isCheckMate() {
        assertFalse(game1.isCheckMate(game1.chessBoard.getFinalSquareFromInput("e2-e3")));
    }

    @Test
    public void changePlayer() {
        game1.changePlayer(game1.chessBoard.getFinalSquareFromInput("e2-e3"));
        assertEquals(game1.currentPlayer, game1.playerBlack);
    }
}