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

    /**
     * SetUp for each test
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        whiteBishop = (Bishop) game.chessBoard.getPieceAt(2, 7);
        blackBishop = (Bishop) game.chessBoard.getPieceAt(2, 0);
    }

    /**
     * sets Initial Pieces on Board
     */
    @Test
    public void startingFormation() {
        assertEquals(blackBishop, game.chessBoard.getPieceAt(2, 0));
    }

    /**
     * tests if consoleOutput works
     */
    @Test
    public void toConsole() {

    }

    /**
     * Gets Moving piece from inputString
     */
    @Test
    public void getMovingPieceFromInput() {
        String input = "b7-b6";
        assertEquals(this.game.chessBoard.getChessBoard()[1][1].getOccupiedBy(), this.game.chessBoard.getMovingPieceFromInput(input));
    }

    /**
     * gets starting square from inputString
     */
    @Test
    public void getStartSquareFromInput() {
        String input = "a8-a7";
        assertEquals(this.game.chessBoard.getChessBoard()[0][0], this.game.chessBoard.getStartSquareFromInput(input));
    }

    /**
     * gets final square from inputString
     */
    @Test
    public void getFinalSquareFromInput() {
        String input = "a7-a8";
        assertEquals(this.game.chessBoard.getChessBoard()[0][0], this.game.chessBoard.getFinalSquareFromInput(input));
    }

    /**
     * tests getter for kingSquare
     */
    @Test
    public void getSquareOfKing() {
        Square king = this.game.chessBoard.getChessBoard()[4][7];
        assertEquals(king, this.game.chessBoard.getSquareOfKing(Colour.WHITE));
    }

    /**
     * tests if king is there
     */
    @Test
    public void noSquareOfKing() {
        clearBoard();
        assertNull(this.game.chessBoard.getSquareOfKing(Colour.WHITE));
    }

    /**
     * tests getter for chessboard
     */
    @Test
    public void getChessBoard() {
        assertEquals(null, game.chessBoard.getChessBoard()[5][5].getOccupiedBy());

    }

    /**
     * tests getter for white allies
     */
    @Test
    public void getWhiteAlliance() {
        assertEquals(this.game.chessBoard.getWhiteAlliance(), this.game.chessBoard.getWhiteAlliance());
    }

    /**
     * tests getter for black allies
     */
    @Test
    public void getBlackAlliance() {
        assertEquals(this.game.chessBoard.getBlackAlliance(), this.game.chessBoard.getBlackAlliance());
    }

    /**
     * tests getter for promotionkey from string
     */
    @Test
    public void getPromotionKey() {
        String input = "e7-e8Q";
        assertEquals('Q', this.game.chessBoard.getPromotionKey(input));
    }

    /**
     * tests getter for promotionkey if there is no key
     */
    @Test
    public void getNoPromotionKey() {
        String input = "e7-e8";
        assertEquals(' ', this.game.chessBoard.getPromotionKey(input));
    }


    /**
     * tests getter for board
     */
    @Test
    public void getBoard() {
        assertEquals(whiteBishop, game.chessBoard.getBoard()[2][7].getOccupiedBy());
    }

    /**
     * tests getter for certain square coordinates
     */
    @Test
    public void getPieceAt() {
        assertEquals(whiteBishop, game.chessBoard.getPieceAt(2, 7));
    }

    /**
     * tests setter for piece at certain square
     */
    @Test
    public void setPieceAt() {
        game.chessBoard.setPieceAt(2, 0, whiteBishop);
        assertEquals(whiteBishop, game.chessBoard.getPieceAt(2, 0));
    }

    /**
     * Getter of Square at given Coordinates
     */
    @Test
    public void getSquareAt() {
        assertEquals(game.chessBoard.getBoard()[0][0], game.chessBoard.getSquareAt(0, 0));
    }

    /**
     * Tests if Board is cleared
     */
    @Test
    public void clearBoard() {
        game.chessBoard.clearBoard();
        for(int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                assertNull(game.chessBoard.getBoard()[x][y].getOccupiedBy());
            }
        }
    }

    /**
     * tests if white ally-list is empty
     */
    @Test
    public void clearWhiteAlliance() {
        game.chessBoard.clearWhiteAlliance();
        assertTrue(game.chessBoard.getWhiteAlliance().isEmpty());
    }

    /**
     * tests if black ally-list is empty
     */
    @Test
    public void clearBlackAlliance() {
        game.chessBoard.clearBlackAlliance();
        assertTrue(game.chessBoard.getBlackAlliance().isEmpty());
    }

    /**
     * tests adding
     */
    @Test
    public void addWhiteAlliance() {
        game.chessBoard.clearWhiteAlliance();
        game.chessBoard.addWhiteAlliance(whiteBishop);
        assertEquals(whiteBishop, game.chessBoard.getWhiteAlliance().get(0));
    }

    /**
     * tests adding
     */
    @Test
    public void addBlackAlliance() {
        game.chessBoard.clearBlackAlliance();
        game.chessBoard.addBlackAlliance(blackBishop);
        assertEquals(blackBishop, game.chessBoard.getBlackAlliance().get(0));
    }
}