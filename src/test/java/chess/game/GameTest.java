package chess.game;

import chess.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The GameTest class test the methods of the Game class
 */
public class GameTest {//NOPMD Game class controls the game, needs to be tested more

    Game game1;
    Board board1;
    Game game2;
    Board board2;

    /**
     * setUp for each test
     */
    @BeforeEach
    public void setUp() {
        game1 = new Game();
        board1 = game1.chessBoard;
        game2 = new Game();
        board2 = game2.chessBoard;
    }


    /**
     * tests processing of moves
     */
    @Test
    public void processMove() {
        game1.processMove(board1.getStartSquareFromInput("e2-e4"), board1.getFinalSquareFromInput("e2-e3"), 'Q');
        assertNotEquals(Arrays.deepToString(board1.getBoard()), Arrays.deepToString(board2.getBoard()));
    }

    /**
     * tests if certain move is allowed
     */
    @Test
    public void isMoveAllowed() {
        assertFalse(game1.isMoveAllowed(board1.getMovingPieceFromInput("e1-e5"), board1.getFinalSquareFromInput("e1-e5")));
        // selected Piece is null
        assertFalse(game1.isMoveAllowed(board1.getSquareAt(0,3).getOccupiedBy(), board1.getSquareAt(0,4)));
    }

    /**
     * tests if isInCheck works
     */
    @Test
    public void isInCheck() {
        assertFalse(game1.isInCheck());
    }


    /**
     * tests if changing players at end of move works
     */
    @Test
    public void changePlayer() {
        game1.changePlayer();
        assertEquals(game1.playerBlack, game1.currentPlayer);
        game1.changePlayer();
        assertEquals(game1.playerWhite, game1.currentPlayer);
    }

    /**
     * tests if Pawn is allowed to make single step
     */
    @Test
    public void testPawn() {
        // one step
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getBoard()[0][5]));
        // double step
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getBoard()[0][4]));
        // triple step
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getBoard()[0][3]));
    }

    /**
     * a few pawn-move tests
     */
    @Test
    public void testPawnWithMoveHistory()
    {
        Game currentGame = new Game();

        // first move
        {
            Square startSquare = currentGame.chessBoard.getBoard()[0][6];
            Square finalSquare = currentGame.chessBoard.getBoard()[0][5];
            currentGame.processMove(startSquare, finalSquare, ' ');
        }

        // second move
        {
            Square startSquare = currentGame.chessBoard.getBoard()[0][1];
            Square finalSquare = currentGame.chessBoard.getBoard()[0][2];
            currentGame.processMove(startSquare, finalSquare, ' ');
        }

        Piece selectedPiece = currentGame.chessBoard.getPieceAt(0, 5);
        Square finalSquare = currentGame.chessBoard.getBoard()[0][4];
        assertTrue(currentGame.isMoveAllowed(selectedPiece, finalSquare));
    }

    /**
     * tests for pawn-enPassantMoves
     */
    @Test
    public void testPawnEnPassant()
    {
        Game currentGame = new Game();
        //CPD-OFF

        // only to switch player
        {
            Square startSquare = currentGame.chessBoard.getBoard()[7][6];
            Square finalSquare = currentGame.chessBoard.getBoard()[7][5];
            currentGame.processMove(startSquare, finalSquare, ' ');
        }

        {
            Square startSquare = currentGame.chessBoard.getBoard()[1][1];
            Square finalSquare = currentGame.chessBoard.getBoard()[1][3];
            currentGame.processMove(startSquare, finalSquare, ' ');
        }

        // only to switch player
        {
            Square startSquare = currentGame.chessBoard.getBoard()[7][5];
            Square finalSquare = currentGame.chessBoard.getBoard()[7][4];
            currentGame.processMove(startSquare, finalSquare, ' ');
        }

        {
            Square startSquare = currentGame.chessBoard.getBoard()[1][3];
            Square finalSquare = currentGame.chessBoard.getBoard()[1][4];
            currentGame.processMove(startSquare, finalSquare, ' ');
        }

        {
            Square startSquare = currentGame.chessBoard.getBoard()[0][6];
            Square finalSquare = currentGame.chessBoard.getBoard()[0][4];
            currentGame.processMove(startSquare, finalSquare, ' ');
        }

        Piece selectedPiece = currentGame.chessBoard.getPieceAt(1, 4);
        Square startSquare = currentGame.chessBoard.getBoard()[1][4];
        Square finalSquare = currentGame.chessBoard.getBoard()[0][5];
        assertTrue(currentGame.isMoveAllowed(selectedPiece, finalSquare));
        assertTrue(currentGame.processMove(startSquare, finalSquare, ' '));
        //CPD-ON
    }

    /**
     * tests if Pawn is allowed to capture correctly
     */
    @Test
    public void testPawnCapture() {
        game1.chessBoard.setPieceAt(1, 5, game1.chessBoard.getPieceAt(0, 1));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getBoard()[1][5]));
        // in front of pawn
        game1.chessBoard.setPieceAt(1, 5, game1.chessBoard.getPieceAt(0, 1));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 6), game1.chessBoard.getBoard()[1][5]));
        // capture ally
        game1.chessBoard.setPieceAt(1, 5, game1.chessBoard.getPieceAt(0, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 6), game1.chessBoard.getBoard()[1][5]));
    }

    /**
     * tests if Rook is allowed to leap
     */
    @Test
    public void testRook() {
        // leap CPD-OFF
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getBoard()[0][4]));
        // vertically
        game1.chessBoard.setPieceAt(0, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getBoard()[0][4]));
        // horizontally
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(0, 7));
        game1.chessBoard.getPieceAt(0, 5).setSquare(game1.chessBoard.getSquareAt(0, 5));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getBoard()[5][5]));
        // diagonally
        game1 = new Game();
        game1.chessBoard.setPieceAt(1, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getBoard()[2][5]));
        //CPD-ON
    }

    /**
     * tests if Rook is allowed to capture
     */
    @Test
    public void testRookCapture() {
        game1.chessBoard.setPieceAt(0, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getBoard()[0][6]));
        // try to capture ally
        game1.chessBoard.setPieceAt(0, 6, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 7), game1.chessBoard.getBoard()[0][6]));
    }

    /**
     * tests if Knight is allowed to leap
     */
    @Test
    public void testKnight() {
        // leap
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getBoard()[0][5]));
        // vertically
        game1.chessBoard.setPieceAt(1, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getBoard()[1][4]));
        // horizontally
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(1, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getBoard()[5][5]));
        // diagonally
        game1.chessBoard.setPieceAt(2, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getBoard()[3][5]));
    }

    /**
     * tests if Knight is allowed to capture
     */
    @Test
    public void testKnightCapture() {
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getBoard()[0][5]));
        // try to capture ally
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(1, 7), game1.chessBoard.getBoard()[0][5]));
    }

    /**
     * tests if Bishop is allowed to leap
     */
    @Test
    public void testBishop() {
        // leap
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getBoard()[0][5]));
        // vertically
        game1.chessBoard.setPieceAt(2, 6, null);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getBoard()[2][4]));
        // horizontally
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(2, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getBoard()[5][5]));
        //diagonally
        game1 = new Game();
        game1.chessBoard.setPieceAt(1, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getBoard()[0][5]));
    }

    /**
     * tests if Bishop is allowed to capture
     */
    @Test
    public void testBishopCapture() {
        game1.chessBoard.setPieceAt(1, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getBoard()[1][6]));
        // try to capture ally
        game1.chessBoard.setPieceAt(1, 6, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(2, 7), game1.chessBoard.getBoard()[1][6]));
    }

    /**
     * tests if Queen is allowed to leap
     */
    @Test
    public void testQueen() {
        // leap
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getBoard()[3][3]));
        // vertically
        game1.chessBoard.setPieceAt(3, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getBoard()[3][4]));
        // horizontally
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(3, 7));
        game1.chessBoard.getPieceAt(0, 5).setSquare(game1.chessBoard.getSquareAt(0, 5));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getBoard()[5][5]));
        // diagonally
        game1.chessBoard.setPieceAt(2, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getBoard()[0][4]));
    }

    /**
     * tests if Queen is allowed to capture
     */
    @Test
    public void testQueenCapture() {
        game1.chessBoard.setPieceAt(3, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getBoard()[3][6]));
        // try to capture ally
        game1.chessBoard.setPieceAt(3, 6, game1.chessBoard.getPieceAt(7, 7));
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(3, 7), game1.chessBoard.getBoard()[3][6]));
    }

    /**
     * tests if King is allowed to leap
     */
    @Test
    public void testKing() {
        // leap
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getBoard()[3][5]));
        // vertically
        game1.chessBoard.setPieceAt(4, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getBoard()[4][6]));
        // horizontally
        game1.chessBoard.setPieceAt(0, 5, game1.chessBoard.getPieceAt(4, 7));
        game1.chessBoard.getPieceAt(0, 5).setSquare(game1.chessBoard.getSquareAt(0, 5));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 5), game1.chessBoard.getBoard()[1][5]));
        // diagonally
        game1 = new Game();
        game1.chessBoard.setPieceAt(3, 6, null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getBoard()[3][6]));
    }

    /**
     * tests if King is allowed to capture
     */
    @Test
    public void testKingCapture() {
        game1.chessBoard.setPieceAt(4, 6, game1.chessBoard.getPieceAt(0, 0));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getBoard()[4][6]));
        // try to capture ally
        game1.chessBoard.setPieceAt(4, 6, game1.chessBoard.getPieceAt(7, 7));
        game1.chessBoard.getPieceAt(4, 6).setNotMoved(false);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getBoard()[4][6]));
    }

    /**
     * tests if King is allowed to move on endangered square
     */
    @Test
    public void testKingMoveIntoDanger() {
        game1.chessBoard.setPieceAt(0, 3, game1.chessBoard.getPieceAt(4, 7));
        game1.chessBoard.getPieceAt(0, 3).setSquare(game1.chessBoard.getSquareAt(0, 3));
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 3), game1.chessBoard.getBoard()[0][2]));//true because checkChess is in another method

        game1 = new Game();
        // moving into danger while capturing
        game1.chessBoard.setPieceAt(4, 6, game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.getPieceAt(4, 6).setSquare(game1.chessBoard.getSquareAt(4, 6));
        game1.chessBoard.setPieceAt(0, 0,null);
        game1.chessBoard.setPieceAt(4, 5, game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.getPieceAt(4, 5).setSquare(game1.chessBoard.getSquareAt(4, 5));
        game1.chessBoard.setPieceAt(7, 0,null);
        assertTrue(game1.isMoveAllowed(game1.chessBoard.getPieceAt(4, 7), game1.chessBoard.getBoard()[4][6])); //true because checkChess is in another method
    }

    /**
     * tests if king is allowed to move
     */
    @Test
    public void testKingMovement() {
        //CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 0, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 1, new Rook(game1.chessBoard.getSquareAt(4, 1), Colour.BLACK));
        assertFalse(game1.canKingMove());
        //CPD-OFF
    }

    /**
     * tests if current game is draw
     */
    @Test
    public void testDrawFalse() {
        //king can move CPD-OFF
        game1.chessBoard.setPieceAt(3, 7, null);
        assertFalse(game1.isADraw());

        //checkMate
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 1, new Rook(game1.chessBoard.getSquareAt(6, 1), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 1));
        assertFalse(game1.isADraw());
        //CPD-ON
    }

    /**
     * tests if current game is a real draw
     */
    @Test
    public void testDrawWhileTrue() {
        // CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 7, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 1, new Rook(game1.chessBoard.getSquareAt(6, 1), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(1, 7));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 1));
        assertTrue(game1.isADraw());
        // CPD-ON
    }

    /**
     * tests if current game is draw while there is one ally who cannot move
     */
    @Test
    public void testDrawWithAlly() {
        //CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 7, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 1, new Rook(game1.chessBoard.getSquareAt(6, 1), Colour.BLACK));
        game1.chessBoard.setPieceAt(3, 4, new Pawn(game1.chessBoard.getSquareAt(3, 4), Colour.WHITE));
        game1.chessBoard.setPieceAt(3, 3, new Pawn(game1.chessBoard.getSquareAt(3, 3), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(3, 4));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(3, 3));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(1, 7));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 1));
        assertTrue(game1.isADraw());
        //CPD-ON
    }

    /**
     * tests if current game is draw
     */
    @Test
    public void testDrawWhileNoDraw() {
        //CPD-OFF
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
        //CPD-ON
    }

    /**
     * tests if current game is draw while checkmate
     */
    @Test
    public void testDrawWhileCheckmate() {
        //CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 0, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game1.chessBoard.setPieceAt(5, 1, new Rook(game1.chessBoard.getSquareAt(4, 1), Colour.BLACK));
        assertFalse(game1.isADraw());
        //CPD-ON
    }

    /**
     * tests promotion
     */
    @Test
    public void testPromotion(){
        //CPD-OFF
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
        //CPD-ON
    }

    /**
     * tests if defending King-Method is working
     */
    @Test
    public void testCanDefendKing(){
        //attacking Piece can be beaten CPD-OFF
        game1.chessBoard.setPieceAt(3, 6, game1.chessBoard.getPieceAt(0, 1));
        game1.chessBoard.setPieceAt(0, 1, null);
        game1.chessBoard.getPieceAt(3, 6).setSquare(game1.chessBoard.getSquareAt(3, 6));
        assertTrue(game1.canDefendKing(game1.chessBoard.getPieceAt(3, 6)));

        //attacking pieces path cam be blocked
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 1, new Rook(game1.chessBoard.getSquareAt(1, 1), Colour.WHITE));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(1, 1));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        assertTrue(game1.canDefendKing(game1.chessBoard.getPieceAt(6, 0)));

        //cant defend king
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        assertFalse(game1.canDefendKing(game1.chessBoard.getPieceAt(6, 0)));
        // CPD-ON
    }

    /**
     * tests checkmate-method
     */
    @Test
    public void testCheckMate() {
        //CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 1, new Rook(game1.chessBoard.getSquareAt(6, 1), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 1));
        assertTrue(game1.isCheckMate());
        //CPD-ON
    }

    /**
     * tests checkmate-method while checked King can be saved by Pawn
     */
    @Test
    public void testCheckMateRescueByRook(){
        //one last white move possible(Rook) CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 1, new Rook(game1.chessBoard.getSquareAt(6, 1), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 7, new Rook(game1.chessBoard.getSquareAt(1, 7), Colour.WHITE));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(1, 7));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 1));
        assertFalse(game1.isCheckMate());
        //CPD-ON
    }
    /**
     * tests checkmate-method while checked King can be saved by Pawn
     */
    @Test
    public void testCheckMateRescueByPawn(){
        //one last white move possible(Pawn) CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 1, new Rook(game1.chessBoard.getSquareAt(6, 1), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 1, new Pawn(game1.chessBoard.getSquareAt(6, 1), Colour.WHITE));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(6, 1));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 1));
        assertFalse(game1.isCheckMate());
        //CPD-ON
    }

    /**
     * tests checkmate-method when King is attacked by pawn
     */
    @Test
    public void testCheckMateWhileAttackingPawn(){
        //white can beat attacking black pawn CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(2, 2, new King(game1.chessBoard.getSquareAt(2, 2), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 1, new Pawn(game1.chessBoard.getSquareAt(1, 1), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(2, 2));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(1, 1));
        assertFalse(game1.isCheckMate());
        // CPD-ON
    }

    /**
     * tests canMoveStay
     */
    @Test
    public void testCanMoveStay() {
        //CPD-OFF
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 0, new Rook(game1.chessBoard.getSquareAt(1, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(7, 1, new Rook(game1.chessBoard.getSquareAt(7, 1), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(1, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 1));
        game1.moveHistory.add(0, new Move(game1.chessBoard.getSquareAt(0, 0), game1.chessBoard.getSquareAt(0, 1)));
        game1.beatenPieces.add(0, new Rook(game1.chessBoard.getSquareAt(0, 1), Colour.BLACK));
        game1.currentPlayer.setInCheck(true);
        game1.playerBlack.setInCheck(true);
        assertFalse(game1.canMoveStay(game1.beatenPieces.get(0), game1.moveHistory.get(0)));

        //same procedure as above but without beaten an enemy-piece
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(6, 0, new Rook(game1.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 0, new Rook(game1.chessBoard.getSquareAt(1, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(7, 1, new Rook(game1.chessBoard.getSquareAt(7, 1), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(6, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(1, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 1));
        game1.moveHistory.add(0, new Move(game1.chessBoard.getSquareAt(0, 0), game1.chessBoard.getSquareAt(0, 1)));
        game1.currentPlayer.setInCheck(true);
        game1.playerBlack.setInCheck(true);
        assertFalse(game1.canMoveStay(null, game1.moveHistory.get(0)));
        //CPD-ON
    }

    /**
     * tests MoveProcessing
     */
    @Test
    public void testProcessMove() {
        //castling Move CPD-OFF
        game1.chessBoard.setPieceAt(1, 7, null);
        game1.chessBoard.setPieceAt(2, 7, null);
        game1.chessBoard.setPieceAt(3, 7, null);
        assertTrue(game1.processMove(game1.chessBoard.getSquareAt(4, 7), game1.chessBoard.getSquareAt(2, 7), 'Q'));

        //beating enemyPiece
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(0, 0, new King(game1.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 0, new King(game1.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(1, 0, new Rook(game1.chessBoard.getSquareAt(1, 0), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(0, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(1, 0));
        assertTrue(game1.processMove(game1.chessBoard.getSquareAt(0, 0), game1.chessBoard.getSquareAt(1, 0), 'Q'));

        //not possible enPassantMove
        game1.chessBoard.clearBoard();
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.clearWhiteAlliance();
        game1.chessBoard.setPieceAt(3, 7, new King(game1.chessBoard.getSquareAt(3, 7), Colour.WHITE));
        game1.chessBoard.setPieceAt(3, 0, new King(game1.chessBoard.getSquareAt(3, 0), Colour.BLACK));
        game1.chessBoard.setPieceAt(3, 1, new Rook(game1.chessBoard.getSquareAt(3, 1), Colour.BLACK));
        game1.chessBoard.setPieceAt(2, 3, new Pawn(game1.chessBoard.getSquareAt(2, 3), Colour.BLACK));
        game1.chessBoard.setPieceAt(3, 3, new Pawn(game1.chessBoard.getSquareAt(3, 3), Colour.WHITE));
        game1.chessBoard.setPieceAt(7, 7, new Rook(game1.chessBoard.getSquareAt(7, 7), Colour.BLACK));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(3, 7));
        game1.chessBoard.addWhiteAlliance(game1.chessBoard.getPieceAt(3, 3));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(3, 0));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(3, 1));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(2, 3));
        game1.chessBoard.addBlackAlliance(game1.chessBoard.getPieceAt(7, 7));
        game1.moveHistory.add(0, new Move(game1.chessBoard.getSquareAt(2, 1), game1.chessBoard.getSquareAt(2, 3)));
        game1.playerBlack.setInCheck(true);
        game1.currentPlayer.setInCheck(true);
        assertFalse(game1.processMove(game1.chessBoard.getSquareAt(3, 3), game1.chessBoard.getSquareAt(2, 2), 'Q'));
        //CPD-ON
    }

    /**
     * Tests Setter and Getter for drawn
     */
    @Test
    public void isDrawn() {
        game1.setDrawn(true);
        assertTrue(game1.isDrawn());
    }

    /**
     * Tests not allowed move if artificial enemy moves enemy piece artificial enemy-move
     */
    @Test
    public void aiEnemyPiece() {
        game1.setArtificialEnemy(true);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 1), game1.chessBoard.getSquareAt(0, 2)));
    }

    /**
     * Tests not allowed move if artificial enemy tries to move a piece where no piece is
     */
    @Test
    public void aiEmptySquare() {
        game1.setArtificialEnemy(true);
        assertFalse(game1.isMoveAllowed(game1.chessBoard.getPieceAt(0, 4), game1.chessBoard.getSquareAt(0, 5)));
    }


}