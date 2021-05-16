package chess.game;

import chess.pieces.Piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The PlayerTest class test the methods of the Player class
 */
public class PlayerTest {
    public Game game;
    public Piece pawnW;
    public Piece pawnB;
    public Piece rookW;
    public Piece rookB;

    @BeforeEach
    public void setUp() {
        game = new Game();
        pawnW = game.chessBoard.getChessBoard()[0][6].getOccupiedBy();
        rookW = game.chessBoard.getChessBoard()[0][7].getOccupiedBy();
        pawnB = game.chessBoard.getChessBoard()[0][1].getOccupiedBy();
        rookB = game.chessBoard.getChessBoard()[0][0].getOccupiedBy();
        game.getBeatenPieces().add(pawnW);
        game.getBeatenPieces().add(pawnB);
        game.getBeatenPieces().add(rookB);
        game.getBeatenPieces().add(rookW);
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, game.currentPlayer.getColour());
    }

    @Test
    public void setColour() {
        game.currentPlayer.setColour(Colour.WHITE);
        assertEquals(Colour.WHITE, game.currentPlayer.getColour());
    }

    @Test
    public void setInCheck() {
        game.currentPlayer.setInCheck(true);
        assertTrue(game.currentPlayer.inCheck);
    }

    @Test
    public void setLoser() {
        game.currentPlayer.setLoser(true);
        assertTrue(game.currentPlayer.loser);
    }

    @Test
    public void isInCheck() {
        game.currentPlayer.setInCheck(true);
        assertTrue(game.currentPlayer.isInCheck());
    }

    @Test
    public void isLoser() {
        assertFalse(game.currentPlayer.isLoser());
    }

    @Test
    public void notWhiteAlliedPieces() {
        assertNotEquals(game.playerWhite.getAlliedPieces(game.getBeatenPieces(), game.chessBoard),
                game.playerBlack.getAlliedPieces(game.getBeatenPieces(), game.chessBoard));
    }

    @Test
    public void notBlackAlliedPieces(){
        assertNotEquals(game.playerBlack.getAlliedPieces(game.getBeatenPieces(), game.chessBoard),
                game.playerWhite.getAlliedPieces(game.getBeatenPieces(), game.chessBoard));
    }

    @Test
    public void notWhiteEnemyPieces() {
        assertNotEquals(game.playerWhite.getEnemyPieces(game.getBeatenPieces(), game.chessBoard),
                game.playerBlack.getEnemyPieces(game.getBeatenPieces(), game.chessBoard));
    }

    @Test
    public void notBlackEnemyPieces(){
        assertNotEquals(game.playerBlack.getEnemyPieces(game.getBeatenPieces(), game.chessBoard),
                game.playerWhite.getEnemyPieces(game.getBeatenPieces(), game.chessBoard));
    }



}