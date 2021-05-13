package chess.game;

import chess.pieces.Pawn;
import chess.pieces.Piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The BoardTest class test the methods of the Board class
 */
public class BoardTest {

    public Square[][] board;
    public Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void startingFormation() {
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
        assertEquals(this.board[0][0], this.game.chessBoard.getStartSquareFromInput(input));
    }

    @Test
    public void getFinalSquareFromInput() {
        String input = "a7-a8";
        assertEquals(this.board[0][0], this.game.chessBoard.getFinalSquareFromInput(input));
    }

    @Test
    public void getSquareOfKing() {
        Square king = this.board[4][7];
        assertEquals(king, this.game.chessBoard.getSquareOfKing(Colour.WHITE));
    }

    @Test
    public void getChessBoard() {

    }

    @Test
    public void getWhiteAlliance() {
        assertEquals(this.game.chessBoard.whitePieces, this.game.chessBoard.getWhiteAlliance());
    }

    @Test
    public void getBlackAlliance() {
        assertEquals(this.game.chessBoard.blackPieces, this.game.chessBoard.getBlackAlliance());
    }

    @Test
    public void getPromotionKey() {
        String input = "e7-e8Q";
        assertEquals('Q', this.game.chessBoard.getPromotionKey(input));
    }
}