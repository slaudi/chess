package chess.engine;

import chess.game.Colour;
import chess.game.Game;
import chess.pieces.King;
import chess.pieces.Rook;
import chess.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some Tests for AI-Computation
 */
public class EngineTest {
    Game game;

    /**
     * a simple setUp for each Test
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /**
     * A Test if NextBestMove-Method works
     */
    @Test
    public void nextBestMove() {
        // CPD-OFF
        game.chessBoard.clearBoard();
        game.chessBoard.clearBlackAlliance();
        game.chessBoard.clearWhiteAlliance();
        game.chessBoard.setPieceAt(0, 0, new King(game.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game.chessBoard.setPieceAt(7, 0, new King(game.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game.chessBoard.setPieceAt(1, 7, new Rook(game.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game.chessBoard.addWhiteAlliance(game.chessBoard.getPieceAt(0, 0));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(7, 0));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(1, 7));
        assertEquals(1, Objects.requireNonNull(Engine.nextBestMove(game)).getFinalSquare().getY());
        // CPD-ON
    }

    /**
     * A Test if NextBestMove-Method works with more than just one White king
     */
    @Test
    public void nextBestMoveWithPawn() {
        // CPD-OFF
        game.chessBoard.clearBoard();
        game.chessBoard.clearBlackAlliance();
        game.chessBoard.clearWhiteAlliance();
        game.chessBoard.setPieceAt(0, 0, new King(game.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game.chessBoard.setPieceAt(7, 5, new Pawn(game.chessBoard.getSquareAt(7, 5), Colour.WHITE));
        game.chessBoard.setPieceAt(7, 0, new King(game.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game.chessBoard.setPieceAt(1, 7, new Rook(game.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game.chessBoard.setPieceAt(6, 0, new Rook(game.chessBoard.getSquareAt(6, 0), Colour.BLACK));
        game.chessBoard.addWhiteAlliance(game.chessBoard.getPieceAt(0, 0));
        game.chessBoard.addWhiteAlliance(game.chessBoard.getPieceAt(7, 5));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(7, 0));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(1, 7));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(6, 0));
        assertEquals(1, Objects.requireNonNull(Engine.nextBestMove(game)).getFinalSquare().getY());
        //CPD-ON
    }
}