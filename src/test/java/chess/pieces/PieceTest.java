package chess.pieces;

import chess.game.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public Square squareD2;
    public Square squareH5;


    @BeforeEach
    public void setUp() {
        game = new Game();
        //board = new Board();
        bishop = new Bishop(squareD3, Colour.WHITE) ;
        squareD3 = new Square(d3,3,5);
        squareD2 = new Square(d2,3,6);
        squareH5 = new Square(h5,7,3);
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
        assertTrue(Piece.isSurroundingSquare(squareD2,squareD3));
    }

    /**
     * Tests if a square is not directly next to another square.
     */
    @Test
    public void notSurroundingSquare() {
        assertFalse(Piece.isSurroundingSquare(squareD2,squareH5));
    }




}
