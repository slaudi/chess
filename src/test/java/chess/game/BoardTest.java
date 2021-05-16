package chess.game;



import chess.pieces.Bishop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The BoardTest class test the methods of the Board class
 */
public class BoardTest {

    public Game game;
    public Bishop whiteBishop;
    public Bishop blackBishop;

    @BeforeEach
    public void setUp() {
        game = new Game();
        whiteBishop = (Bishop) game.chessBoard.getPieceAt(2, 7);
        blackBishop = (Bishop) game.chessBoard.getPieceAt(2, 0);
    }

    @Test
    public void startingFormation() {
        assertEquals(blackBishop, game.chessBoard.getPieceAt(2, 0));
    }

    @Test
    public void toConsole() {
    }

    @Test
    public void getMovingPieceFromInput() {
        String input = "b7-b6";
        assertEquals(this.game.chessBoard.getChessBoard()[1][1].getOccupiedBy(), this.game.chessBoard.getMovingPieceFromInput(input));
    }

    @Test
    public void getStartSquareFromInput() {
        String input = "a8-a7";
        assertEquals(this.game.chessBoard.getChessBoard()[0][0], this.game.chessBoard.getStartSquareFromInput(input));
    }

    @Test
    public void getFinalSquareFromInput() {
        String input = "a7-a8";
        assertEquals(this.game.chessBoard.getChessBoard()[0][0], this.game.chessBoard.getFinalSquareFromInput(input));
    }

    @Test
    public void getSquareOfKing() {
        Square king = this.game.chessBoard.getChessBoard()[4][7];
        assertEquals(king, this.game.chessBoard.getSquareOfKing(Colour.WHITE));
    }

    @Test
    public void getChessBoard() {

    }

    @Test
    public void getWhiteAlliance() {
        assertEquals(this.game.chessBoard.getWhiteAlliance(), this.game.chessBoard.getWhiteAlliance());
    }

    @Test
    public void getBlackAlliance() {
        assertEquals(this.game.chessBoard.getBlackAlliance(), this.game.chessBoard.getBlackAlliance());
    }

    @Test
    public void getPromotionKey() {
        String input = "e7-e8Q";
        assertEquals('Q', this.game.chessBoard.getPromotionKey(input));
    }

    @Test
    public void getBoard() {
        assertEquals(whiteBishop, game.chessBoard.getBoard()[2][7].getOccupiedBy());
    }

    @Test
    public void getPieceAt() {
        assertEquals(whiteBishop, game.chessBoard.getPieceAt(2, 7));
    }

    @Test
    public void setPieceAt() {
        game.chessBoard.setPieceAt(2, 0, whiteBishop);
        assertEquals(whiteBishop, game.chessBoard.getPieceAt(2, 0));
    }

    @Test
    void getSquareAt() {
    }

    @Test
    void clearBoard() {
    }
}