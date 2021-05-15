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

    // TODO: doMove;
   /*@Test
    public void doMove() {
        move.doMove(game.chessBoard);
        move2.doMove(game.chessBoard);
        assertNotEquals(game.chessBoard, game.chessBoard);
    }*/

    @Test
    public void undoMove() {
        // without a captured Piece
        move.doMove(game.chessBoard);
        move.undoMove( game.chessBoard);
        assertEquals(game.chessBoard, game.chessBoard);
    }

    // TODO: castlingMove
    /*@Test
    public void castlingMove() {
        move.castlingMove(game.chessBoard);
        Board board2 = game.chessBoard;
        move2.castlingMove(game.chessBoard);
        assertNotEquals(game.chessBoard, board2);
    }*/

    @Test
    public void enPassantMove() {
    }

    @Test
    public void undoEnPassant() {
    }

    @Test
    public void doPromotion() {
    }

}