package chess.pieces;

import chess.game.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static chess.game.Label.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PieceTest class tests the methods from the Piece class.
 */
public class PieceTest {

    Game game;
    Piece bishop;
    Square squareD3;
    Square squareD4;
    Square squareH5;
    Square squareE4;
    Piece queen;
    Piece rook;
    Piece knight;

    /**
     * Set up for the Piece class tests
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        squareD3 = new Square(d3,3,5);
        squareD4 = new Square(d4,3,4);
        squareH5 = new Square(h5,7,3);
        squareE4 = new Square(e4,5,4);
        bishop = new Bishop(squareD3, Colour.BLACK);
        queen = new Queen(squareD4, Colour.BLACK);
        rook = new Rook(squareH5, Colour.BLACK);
        knight = new Knight(squareE4, Colour.BLACK);
    }

    /**
     * Tests if a piece on the board can do any move.
     */
    @Test
    public void canPieceMove() {
        assertTrue(bishop.canPieceMove(game.chessBoard));
        Piece rook = game.chessBoard.getPieceAt(7,7);
        assertFalse(rook.canPieceMove(game.chessBoard));
    }

    /**
     * Tests if a square is directly next to another square.
     */
    @Test
    public void isSurroundingSquare() {
        assertTrue(Piece.isSurroundingSquare(squareD4,squareD3));
        // not surrounding square
        assertFalse(Piece.isSurroundingSquare(squareD4,squareH5));
    }

    /**
     * Tests 'generatePath' to check if the right squares are saved in the generated List (and not the first
     * and not the last).
     */
    @Test
    public void generatePath() {
        List<Square> path = bishop.generatePath(game.chessBoard.getBoard()[6][2], game.chessBoard);
        assertEquals(List.of(game.chessBoard.getBoard()[4][4], game.chessBoard.getBoard()[5][3]), path);
        // path is empty
        path = bishop.generatePath(squareE4, game.chessBoard);
        assertEquals(List.of(), path);
    }

    /**
     * Tests 'isPathEmpty' if the path is empty or not.
     */
    @Test
    public void isPathEmpty() {
        Square squareB4 = game.chessBoard.getSquareAt(1,4);
        Square squareH4 = game.chessBoard.getSquareAt(7,4);
        Piece rook = new Rook(squareB4, Colour.WHITE);
        squareB4.setOccupiedBy(rook);
        assertTrue(rook.isPathEmpty(squareH4, game.chessBoard));
        // path is not empty
        Piece king = game.chessBoard.getPieceAt(5,7);
        assertFalse(king.isPathEmpty(squareE4, game.chessBoard));
        // true if it's a Knight and not empty
        Piece knight = new Knight(squareH4, Colour.BLACK);
        Square squareA4 = game.chessBoard.getSquareAt(0,4);
        assertTrue(knight.isPathEmpty(squareA4, game.chessBoard));
    }


    /**
     * Tests if right Promotion-Char is returned
     */
    @Test
    public void getPromotionCharQueen() {
        assertEquals('Q', queen.getPromotionChar());
    }

    /**
     * Tests if right Promotion-Char is returned
     */
    @Test
    public void getPromotionCharRook() {
        assertEquals('R', rook.getPromotionChar());
    }

    /**
     * Tests if right Promotion-Char is returned
     */
    @Test
    public void getPromotionCharBishop() {
        assertEquals('B', bishop.getPromotionChar());
    }

    /**
     * Tests if right Promotion-Char is returned
     */
    @Test
    public void getPromotionCharKnight() {
        assertEquals('N', knight.getPromotionChar());
    }
}
