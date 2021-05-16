package chess.game;

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

    /**
     * tests if Pawn is allowed to make single step
     */
    @Test
    public void testPawnSingleStep() {
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getChessBoard()[0][5]));
    }

    /**
     * tests if Pawn is allowed to make double step
     */
    @Test
    public void testPawnDoubleStep() {
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getChessBoard()[0][4]));
    }

    /**
     * tests if Pawn is allowed to make triple step
     */
    @Test
    public void testPawnTripleStep() {
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getChessBoard()[0][3]));
    }

    /**
     * tests if Pawn is allowed to capture correctly
     */
    @Test
    public void testPawnCapture() {
        game1.chessBoard.setPieceAt(1, 5, game1.chessBoard.getPieceAt(0, 1));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getChessBoard()[1][5]));
    }

    /**
     * tests if Pawn is allowed to capture in front of him
     */
    @Test
    public void testPawnCaptureInFront() {
        game1.chessBoard.setPieceAt(1, 5, game1.chessBoard.getPieceAt(0, 1));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 6), game1.chessBoard.getChessBoard()[1][5]));
    }

    /**
     * tests if Pawn is allowed to capture ally
     */
    @Test
    public void testPawnCaptureAlly() {
        game1.chessBoard.setPieceAt(1, 5, game1.chessBoard.getPieceAt(0, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getChessBoard()[1][5]));
    }

    /**
     * tests if Rook is allowed to leap
     */
    @Test
    public void testRookLeap() {
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getChessBoard()[0][4]));
    }

    /**
     * tests if Rook is allowed to move vertically
     */
    @Test
    public void testRookMoveVertical() {
        game1.chessBoard.setPieceAt(0, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getChessBoard()[0][4]));
    }

    /**
     * tests if Rook is allowed to move horizontally
     */
    @Test
    public void testRookMoveHorizontal() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(0, 7));
        System.out.println(game1.chessBoard.getPieceAt(0,5).toString());
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getChessBoard()[5][5]));
    }
}