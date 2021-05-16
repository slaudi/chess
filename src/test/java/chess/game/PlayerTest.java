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

    /**
     * setUp for each PlayerTest
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        pawnW = game.chessBoard.getChessBoard()[0][6].getOccupiedBy();
        rookW = game.chessBoard.getChessBoard()[0][7].getOccupiedBy();
        pawnB = game.chessBoard.getChessBoard()[0][1].getOccupiedBy();
        rookB = game.chessBoard.getChessBoard()[0][0].getOccupiedBy();
        game.beatenPieces.add(pawnW);
        game.beatenPieces.add(pawnB);
        game.beatenPieces.add(rookB);
        game.beatenPieces.add(rookW);
    }

    /**
     * tests getter for colour
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, game.currentPlayer.getColour());
    }

    /**
     * tests setter for colour
     */
    @Test
    public void setColour() {
        game.currentPlayer.setColour(Colour.WHITE);
        assertEquals(Colour.WHITE, game.currentPlayer.getColour());
    }

    /**
     * tests setter for incheck
     */
    @Test
    public void setInCheck() {
        game.currentPlayer.setInCheck(true);
        assertTrue(game.currentPlayer.inCheck);
    }

    /**
     * tests setter for loser
     */
    @Test
    public void setLoser() {
        game.currentPlayer.setLoser(true);
        assertTrue(game.currentPlayer.loser);
    }

    /**
     * tests if isincheck works
     */
    @Test
    public void isInCheck() {
        game.currentPlayer.setInCheck(true);
        assertTrue(game.currentPlayer.isInCheck());
    }

    /**
     * tests if isloser works
     */
    @Test
    public void isLoser() {
        assertFalse(game.currentPlayer.isLoser());
    }

    /**
     * tests if there are no white allied pieces
     */
    @Test
    public void notWhiteAlliedPieces() {
        assertNotEquals(game.playerWhite.getAlliedPieces(game.beatenPieces, game.chessBoard),
                game.playerBlack.getAlliedPieces(game.beatenPieces, game.chessBoard));
    }

    /**
     * tests if there are no black allied pieces
     */
    @Test
    public void notBlackAlliedPieces(){
        assertNotEquals(game.playerBlack.getAlliedPieces(game.beatenPieces, game.chessBoard),
                game.playerWhite.getAlliedPieces(game.beatenPieces, game.chessBoard));
    }

    /**
     * tests if white has no enemypieces
     */
    @Test
    public void notWhiteEnemyPieces() {
        assertNotEquals(game.playerWhite.getEnemyPieces(game.beatenPieces, game.chessBoard),
                game.playerBlack.getEnemyPieces(game.beatenPieces, game.chessBoard));
    }

    /**
     * tests if black has no enemypieces
     */
    @Test
    public void notBlackEnemyPieces(){
        assertNotEquals(game.playerBlack.getEnemyPieces(game.beatenPieces, game.chessBoard),
                game.playerWhite.getEnemyPieces(game.beatenPieces, game.chessBoard));
    }



}