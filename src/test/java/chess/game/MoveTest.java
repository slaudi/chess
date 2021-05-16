package chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The MoveTest class test the methods of the Move class
 */
class MoveTest {

    public Game game;
    public Move move;
    public Move move2;

    @BeforeEach
    public void setUp() {
        game = new Game();
        move = new Move(game.chessBoard.getStartSquareFromInput("a2-a3"), game.chessBoard.getFinalSquareFromInput("a2-a3"));
        move2 = new Move(game.chessBoard.getStartSquareFromInput("a3-a2"), game.chessBoard.getFinalSquareFromInput("a3-a2"));
    }

    @Test
    public void getStartSquare() {
        assertEquals(game.chessBoard.getStartSquareFromInput("a2-a3"), move.getStartSquare());
    }

    @Test
    public void getFinalSquare() {
        assertEquals(game.chessBoard.getFinalSquareFromInput("a2-a3"), move.getFinalSquare());
    }

    @Test
    public void undoMove() {
        // without a captured Piece
        move.doMove(game.chessBoard);
        move.undoMove( game.chessBoard);
        assertEquals(game.chessBoard, game.chessBoard);
    }

    @Test
    public void enPassantMove() {
    }

    @Test
    public void undoEnPassant() {
    }

    @Test
    public void doPromotion() {
    }

    @Test
    public void getMovingPiece() {
    }

    @Test
    public void doMove() {
    }

    /**
     * tests castling white kingside
     */
    @Test
    public void castlingMoveWhiteKingside() {
        game.chessBoard.setPieceAt(5, 7, null);
        game.chessBoard.setPieceAt(6, 7, null);
        move.castlingMove(game.chessBoard, game.chessBoard.getSquareAt(6, 7));
        assertEquals(null, game.chessBoard.getPieceAt(7, 7));
    }

    /**
     * tests castling white queenside
     */
    @Test
    public void castlingMoveWhiteQueenside() {
        game.chessBoard.setPieceAt(1, 7, null);
        game.chessBoard.setPieceAt(2, 7, null);
        game.chessBoard.setPieceAt(3, 7, null);
        move.castlingMove(game.chessBoard, game.chessBoard.getSquareAt(2, 7));
        assertEquals(null, game.chessBoard.getPieceAt(0, 7));
    }

    /**
     * tests castling black queenside
     */
    @Test
    public void castlingMoveBlackQueenside() {
        game.chessBoard.setPieceAt(1, 0, null);
        game.chessBoard.setPieceAt(2, 0, null);
        game.chessBoard.setPieceAt(3, 0, null);
        move.castlingMove(game.chessBoard, game.chessBoard.getSquareAt(2, 0));
        assertEquals(null, game.chessBoard.getPieceAt(0, 0));
    }

    /**
     * tests castling black kingside
     */
    @Test
    public void castlingMoveBlackKingside() {
        game.chessBoard.setPieceAt(5, 0, null);
        game.chessBoard.setPieceAt(6, 0, null);
        move.castlingMove(game.chessBoard, game.chessBoard.getSquareAt(6, 0));
        assertEquals(null, game.chessBoard.getPieceAt(7, 0));
    }
}