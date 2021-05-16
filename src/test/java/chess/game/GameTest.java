package chess.game;

import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import chess.cli.Cli;

import static chess.game.Label.f3;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The GameTest class test the methods of the Game class
 */
public class GameTest {

    public Game game1;
    public Game game2;

    /**
     * setUp for each test
     */
    @BeforeEach
    public void setUp() {
        game1 = new Game();
        game2 = new Game();
    }

    /**
     * tests processing of moves
     */
    @Test
    public void processMove() {
        game1.processMove(this.game1.chessBoard.getStartSquareFromInput("e2-e3"), this.game1.chessBoard.getFinalSquareFromInput("e2-e3"), 'Q');
        assertNotEquals(game1, game2);

    }

    /**
     * tests if certain move is allowed
     */
    @Test
    public void isMoveAllowed() {
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getMovingPieceFromInput("e1-e5"), game1.chessBoard.getFinalSquareFromInput("e1-e5")));
    }

    /**
     * tests drawImplementation
     */
    @Test
    public void isADraw() {
        assertFalse(game1.isADraw());
    }

    /**
     * tests if isInCheck works
     */
    @Test
    public void isInCheck() {
        assertFalse(game1.isInCheck());
    }

    /**
     * tests if current player is Checkmate
     */
    @Test
    public void isCheckMate() {
        assertFalse(game1.isCheckMate(game1.chessBoard.getFinalSquareFromInput("e2-e3")));
    }

    /**
     * tests if changing players at end of move works
     */
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
        game1.chessBoard.getPieceAt(4, 6).setNotMoved(false);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[4][6]));
    }

    /**
     * tests if King is allowed to move on endangered square
     */
    @Test
    public void testKingMoveIntoDanger() {
        game1.chessBoard.setPieceAt(0, 3, game1.chessBoard.getPieceAt(4, 7));
        game1.chessBoard.getPieceAt(0, 3).setSquare(game1.chessBoard.getSquareAt(0, 3));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 3), game1.chessBoard.getChessBoard()[0][2]));//true because checkChess is in another method
    }

    /**
     * tests if King is allowed to move on endangered square by capturing
     */
    @Test
    public void testKingCaptureIntoDanger() {
        game1.chessBoard.setPieceAt(4, 6, game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.getPieceAt(4, 6).setSquare(game1.chessBoard.getSquareAt(4, 6));
        game1.chessBoard.setPieceAt(0, 0,null);
        game1.chessBoard.setPieceAt(4, 5, game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.getPieceAt(4, 5).setSquare(game1.chessBoard.getSquareAt(4, 5));
        game1.chessBoard.setPieceAt(7, 0,null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getChessBoard()[4][6])); //true because checkChess is in another method
    }

    /**
     * tests if current game is draw
     */
    @Test
    public void testDraw() {
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(7, 1, new Pawn(game1.chessBoard.getSquareAt(7, 1), Colour.WHITE));
        game1.chessBoard.setPieceAt(1, 7, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game1.chessBoard.setPieceAt(4, 1, new Rook(game1.chessBoard.getSquareAt(4, 1), Colour.BLACK));
        game1.chessBoard.setPieceAt(7, 4, new Pawn(game1.chessBoard.getSquareAt(7, 4), Colour.WHITE));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(7, 1));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(7, 4));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(1, 7));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(4, 1));
        assertFalse(game1.isADraw());
    }

    /**
     * tests if current game is draw while actually it is check mate
     */
    @Test
    public void testDrawWhileCheckMate() {
        game1.chessBoard.clearBoard();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 0, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 1, new Rook(game1.chessBoard.getSquareAt(4, 1), Colour.BLACK));
        assertFalse(game1.isADraw());
    }

    /**
     * tests if king is allowed to move
     */
    @Test
    public void testKingMovement() {
        game1.chessBoard.clearBoard();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 0, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 1, new Rook(game1.chessBoard.getSquareAt(4, 1), Colour.BLACK));
        assertFalse(game1.canKingMove());
    }

    /**
     * tests promotion
     */
    @Test
    public void testPromotion(){
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(3, 1, new Pawn(game1.chessBoard.getSquareAt(3, 1), Colour.WHITE));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(3, 1));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        assertTrue(game1.processMove(game1.chessBoard.getSquareAt(3, 1), game1.chessBoard.getSquareAt(3, 0), 'Q'));
    }

    /**
     * tests getter for beaten pieces
     */
    @Test
    public void getBeatenPieces() {
        assertTrue(game1.getBeatenPieces().isEmpty());
    }

    /**
     * tests if king is able to move
     */
    @Test
    public void canKingMove() {
        assertFalse(game1.canKingMove());
    }

    /**
     * tests castling while path isnt empty
     */
    @Test
    public void canDoCastlingWhilePathBlocked() {
        assertFalse(game1.canDoCastling(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getSquareAt(2, 7)));
    }

    /**
     * tests castling white kingside
     */
    @Test
    public void canDoCastlingWhiteKingside() {
        game1.chessBoard.setPieceAt(5, 7, null);
        game1.chessBoard.setPieceAt(6, 7, null);
        assertTrue(game1.canDoCastling(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getSquareAt(6, 7)));
    }

    /**
     * tests castling white queenside
     */
    @Test
    public void canDoCastlingWhiteQueenside() {
        game1.chessBoard.setPieceAt(1, 7, null);
        game1.chessBoard.setPieceAt(2, 7, null);
        game1.chessBoard.setPieceAt(3, 7, null);
        assertTrue(game1.canDoCastling(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getSquareAt(2, 7)));
    }

    /**
     * tests castling black queenside
     */
    @Test
    public void canDoCastlingBlackQueenside() {
        game1.chessBoard.setPieceAt(1, 0, null);
        game1.chessBoard.setPieceAt(2, 0, null);
        game1.chessBoard.setPieceAt(3, 0, null);
        assertTrue(game1.canDoCastling(game1.chessBoard.getPieceAt(4, 0), game1.chessBoard.getSquareAt(2, 0)));
    }

    /**
     * tests castling black kingside
     */
    @Test
    public void canDoCastlingBlackKingside() {
        game1.chessBoard.setPieceAt(5, 0, null);
        game1.chessBoard.setPieceAt(6, 0, null);
        assertTrue(game1.canDoCastling(game1.chessBoard.getPieceAt(4, 0), game1.chessBoard.getSquareAt(6, 0)));
    }
}