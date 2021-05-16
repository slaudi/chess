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
        game1.chessBoard.getPieceAt(0, 5).setSquare(game1.chessBoard.getSquareAt(0, 5));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getChessBoard()[5][5]));
    }

    /**
     * tests if Rook is allowed to move diagonally
     */
    @Test
    public void testRookMoveDiagonal() {
        game1.chessBoard.setPieceAt(1, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getChessBoard()[2][5]));
    }

    /**
     * tests if Rook is allowed to capture
     */
    @Test
    public void testRookCapture() {
        game1.chessBoard.setPieceAt(0, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getChessBoard()[0][6]));
    }

    /**
     * tests if Rook is allowed to capture ally
     */
    @Test
    public void testRookCaptureAlly() {
        game1.chessBoard.setPieceAt(0, 6, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getChessBoard()[0][6]));
    }

    /**
     * tests if Knight is allowed to leap
     */
    @Test
    public void testKnightLeap() {
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getChessBoard()[0][5]));
    }

    /**
     * tests if Knight is allowed to move vertically
     */
    @Test
    public void testKnightMoveVertical() {
        game1.chessBoard.setPieceAt(1, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getChessBoard()[1][4]));
    }

    /**
     * tests if Knight is allowed to move horizontally
     */
    @Test
    public void testKnightMoveHorizontal() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(1, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getChessBoard()[5][5]));
    }

    /**
     * tests if Knight is allowed to move diagonally
     */
    @Test
    public void testKnightMoveDiagonal() {
        game1.chessBoard.setPieceAt(2, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getChessBoard()[3][5]));
    }

    /**
     * tests if Knight is allowed to capture
     */
    @Test
    public void testKnightCapture() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getChessBoard()[0][5]));
    }

    /**
     * tests if Knight is allowed to capture ally
     */
    @Test
    public void testKnightCaptureAlly() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getChessBoard()[0][5]));
    }

    /**
     * tests if Bishop is allowed to leap
     */
    @Test
    public void testBishopLeap() {
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getChessBoard()[0][5]));
    }

    /**
     * tests if Bishop is allowed to move vertically
     */
    @Test
    public void testBishopMoveVertical() {
        game1.chessBoard.setPieceAt(2, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getChessBoard()[2][4]));
    }

    /**
     * tests if Bishop is allowed to move horizontally
     */
    @Test
    public void testBishopMoveHorizontal() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(2, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getChessBoard()[5][5]));
    }

    /**
     * tests if Bishop is allowed to move diagonally
     */
    @Test
    public void testBishopMoveDiagonal() {
        game1.chessBoard.setPieceAt(1, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getChessBoard()[0][5]));
    }

    /**
     * tests if Bishop is allowed to capture
     */
    @Test
    public void testBishopCapture() {
        game1.chessBoard.setPieceAt(1, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getChessBoard()[1][6]));
    }

    /**
     * tests if Bishop is allowed to capture ally
     */
    @Test
    public void testBishopCaptureAlly() {
        game1.chessBoard.setPieceAt(1, 6, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getChessBoard()[1][6]));
    }

    /**
     * tests if Queen is allowed to leap
     */
    @Test
    public void testQueenLeap() {
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getChessBoard()[3][3]));
    }

    /**
     * tests if Queen is allowed to move vertically
     */
    @Test
    public void testQueenMoveVertical() {
        game1.chessBoard.setPieceAt(3, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getChessBoard()[3][4]));
    }

    /**
     * tests if Queen is allowed to move horizontally
     */
    @Test
    public void testQueenMoveHorizontal() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(3, 7));
        game1.chessBoard.getPieceAt(0, 5).setSquare(game1.chessBoard.getSquareAt(0, 5));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getChessBoard()[5][5]));
    }

    /**
     * tests if Queen is allowed to move diagonally
     */
    @Test
    public void testQueenMoveDiagonal() {
        game1.chessBoard.setPieceAt(2, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getChessBoard()[0][4]));
    }

    /**
     * tests if Queen is allowed to capture
     */
    @Test
    public void testQueenCapture() {
        game1.chessBoard.setPieceAt(3, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getChessBoard()[3][6]));
    }

    /**
     * tests if Queen is allowed to capture ally
     */
    @Test
    public void testQueenCaptureAlly() {
        game1.chessBoard.setPieceAt(3, 6, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getChessBoard()[3][6]));
    }

    /**
     * tests if King is allowed to leap
     */
    @Test
    public void testKingLeap() {
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[3][5]));
    }

    /**
     * tests if King is allowed to move vertically
     */
    @Test
    public void testKingMoveVertical() {
        game1.chessBoard.setPieceAt(4, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[4][6]));
    }

    /**
     * tests if King is allowed to move horizontally
     */
    @Test
    public void testKingMoveHorizontal() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(4, 7));
        game1.chessBoard.getPieceAt(0, 5).setSquare(game1.chessBoard.getSquareAt(0, 5));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getChessBoard()[1][5]));
    }

    /**
     * tests if King is allowed to move diagonally
     */
    @Test
    public void testKingMoveDiagonal() {
        game1.chessBoard.setPieceAt(3, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[3][6]));
    }

    /**
     * tests if King is allowed to capture
     */
    @Test
    public void testKingCapture() {
        game1.chessBoard.setPieceAt(4, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[4][6]));
    }

    /**
     * tests if King is allowed to capture ally
     */
    @Test
    public void testKingCaptureAlly() {
        game1.chessBoard.setPieceAt(4, 6, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[4][6]));
    }

    /**
     * tests if King is allowed to move on endangered square
     */
    @Test
    public void testKingMoveIntoDanger() {
        game1.chessBoard.setPieceAt(0, 3, game1.chessBoard.getPieceAt(4, 7));
        game1.chessBoard.getPieceAt(0, 3).setSquare(game1.chessBoard.getSquareAt(0, 3));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 3), game1.chessBoard.getChessBoard()[0][2]));
    }

    /**
     * tests if King is allowed to move on endangered square by capturing
     */
    @Test
    public void testKingCaptureIntoDanger() {
        game1.chessBoard.setPieceAt(4, 6, game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.getPieceAt(4, 6).setSquare(game1.chessBoard.getSquareAt(4, 6));
        game1.chessBoard.setPieceAt(4, 5, game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.getPieceAt(4, 5).setSquare(game1.chessBoard.getSquareAt(4, 5));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[4][6]));
    }

}