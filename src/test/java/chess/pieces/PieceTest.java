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

    public Game game;
    public Board board;
    public Piece bishop;
    public Square squareD3;
    public Square squareD4;
    public Square squareH5;
    public Square squareE4;


    @BeforeEach
    public void setUp() {
        game = new Game();
        board = new Board();
        squareD3 = new Square(d3,3,5);
        squareD4 = new Square(d4,3,4);
        squareH5 = new Square(h5,7,3);
        squareE4 = new Square(e4,5,4);
        bishop = new Bishop(squareD3, Colour.WHITE);
    }

    /**
     * Tests if a piece on the board can do any move.
     */
    @Test
    public void canPieceMove() {
        assertTrue(bishop.canPieceMove(game.chessBoard));
    }

    /**
     * Tests if a square is directly next to another square.
     */
    @Test
    public void isSurroundingSquare() {
        assertTrue(Piece.isSurroundingSquare(squareD4,squareD3));
    }

    /**
     * Tests if a square is not directly next to another square.
     */
    @Test
    public void notSurroundingSquare() {
        assertFalse(Piece.isSurroundingSquare(squareD4,squareH5));
    }

    /**
     * Tests 'generatePath' to check if only moving one square doesn't generate a path.
     */
    @Test
    public void generateNoPath() {
        List<Square> path = bishop.generatePath(squareE4, game.chessBoard);
        assertEquals(List.of(), path);
    }

    /**
     * Tests 'generatePath' to check if the right squares are saved in the generated List (and not the first
     * and not the last).
     */
    @Test
    public void generatePath() {
        List<Square> path = bishop.generatePath(game.chessBoard.getChessBoard()[6][2], game.chessBoard);
        assertEquals(List.of(game.chessBoard.getChessBoard()[4][4], game.chessBoard.getChessBoard()[5][3]), path);
    }

    /**
     * Tests 'isPathEmpty' if the path is not empty.
     */
    @Test
    public void isPathNotEmpty() {
        Piece king = game.chessBoard.getPieceAt(5,7);
        assertFalse(king.isPathEmpty(squareE4, game.chessBoard));
    }


}
