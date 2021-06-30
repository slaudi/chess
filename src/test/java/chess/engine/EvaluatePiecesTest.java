package chess.engine;

import chess.game.Colour;
import chess.game.Game;
import chess.pieces.King;
import chess.pieces.Queen;
import chess.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * A Class in which Tests are combined to test the PieceEvaluation
 */
public class EvaluatePiecesTest {

    Game game;

    /**
     * Just a simple SetUp for each Test: an initialisation of an new game to test on
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /**
     *a simple evaluation of a new game
     */
    @Test
    public void evaluateBoard() {
        EvaluatePieces.evaluateBoard(game, game.currentPlayer.getColour());
        assertEquals(0, EvaluatePieces.evaluateBoard(game, game.currentPlayer.getColour()));

    }

    /**
     * an evaluation of a nearly empty board with only a King and a Rook for each Player
     */
    @Test
    public void evaluateBoardWithOnlyKing(){
        game.chessBoard.clearBoard();
        game.chessBoard.clearBlackAlliance();
        game.chessBoard.clearWhiteAlliance();
        game.chessBoard.setPieceAt(0, 0, new King(game.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game.chessBoard.setPieceAt(7, 0, new King(game.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game.chessBoard.setPieceAt(1, 7, new Rook(game.chessBoard.getSquareAt(1, 7), Colour.BLACK));
        game.chessBoard.setPieceAt(6, 1, new Rook(game.chessBoard.getSquareAt(6, 1), Colour.WHITE));
        game.chessBoard.addWhiteAlliance(game.chessBoard.getPieceAt(0, 0));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(7, 0));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(1, 7));
        game.chessBoard.addWhiteAlliance(game.chessBoard.getPieceAt(6, 1));
        assertEquals(-1, EvaluatePieces.evaluateBoard(game, game.currentPlayer.getColour()));
    }

    /**
     * an evaluation of a nearly empty board with only a King and a Rook for each Player. White King in Check.
     */
    @Test
    public void evaluateBoardinCheck(){
        game.chessBoard.clearBoard();
        game.chessBoard.clearBlackAlliance();
        game.chessBoard.clearWhiteAlliance();
        game.chessBoard.setPieceAt(0, 0, new King(game.chessBoard.getSquareAt(0, 0), Colour.WHITE));
        game.chessBoard.setPieceAt(7, 0, new King(game.chessBoard.getSquareAt(7, 0), Colour.BLACK));
        game.chessBoard.setPieceAt(0, 7, new Rook(game.chessBoard.getSquareAt(0, 7), Colour.BLACK));
        game.chessBoard.setPieceAt(6, 1, new Rook(game.chessBoard.getSquareAt(6, 1), Colour.WHITE));
        game.chessBoard.addWhiteAlliance(game.chessBoard.getPieceAt(0, 0));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(7, 0));
        game.chessBoard.addBlackAlliance(game.chessBoard.getPieceAt(0, 7));
        game.chessBoard.addWhiteAlliance(game.chessBoard.getPieceAt(6, 1));
        assertEquals(99, EvaluatePieces.evaluateBoard(game, game.currentPlayer.getColour()));
    }

    /**
     * an evaluation of a Queen.
     */
    @Test
    public void piecesValueQueen(){
        assertEquals(900, EvaluatePieces.piecesValue(new Queen(game.chessBoard.getSquareAt(5, 5), Colour.BLACK)));
    }

    /**
     * an evaluation of an empty Square.
     */
    @Test
    public void piecesValueNull(){
        assertEquals(0, EvaluatePieces.piecesValue(null));
    }
}
