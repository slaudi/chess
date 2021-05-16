package chess.pieces;

import chess.game.Colour;
import chess.game.Game;
import chess.game.Square;
import chess.game.Type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static chess.game.Label.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The KingTest class test the methods of the King class
 */
public class KingTest {

    public King king;
    public Square square;
    public Square squareC3;
    public Game game;
    public Game game1;
    List<Piece> enemies;

    /**
     * setUp for each KingTest
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        square = new Square(c4, 2, 4);
        king = new King(square, Colour.WHITE);
        squareC3 = new Square(c3,2, 5);
        game1 = new Game();
        enemies = game1.currentPlayer.getEnemyPieces(game1.beatenPieces, game1.chessBoard);
    }

    /**
     * tests getter for square
     */
    @Test
    public void getSquare() {
        assertEquals(square, king.getSquare());
    }

    /**
     * tests setter for square
     */
    @Test
    public void setSquare() {
        square = new Square(g5,7,3);
        king.setSquare(square);
        assertEquals(square, king.getSquare());
    }

    /**
     * tests getter for colour
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, king.getColour());
    }

    /**
     * tests typeGetter
     */
    @Test
    public void getType() {
        assertEquals(Type.KING, king.getType());
    }

    /**
     * tests if hasn't moved works
     */
    @Test
    public void hasNotMoved() {
        assertTrue(king.hasNotMoved());
    }

    /**
     * tests setter for hasn't moved
     */
    @Test
    public void setHasMoved() {
        king.setNotMoved(false);
        assertFalse(king.hasNotMoved());
    }

    /**
     * tests if piece is able to move as it should
     */
    @Test
    public void isPiecesMove() {
        assertTrue(king.isPiecesMove(squareC3, game.chessBoard));
    }

    /**
     * tests is two squares are the same
     */
    @Test
    public void isSameSquare(){
        assertFalse(king.isPiecesMove(square, game.chessBoard));
    }

    /**
     * tests if piece is really not allowed to move
     */
    @Test
    public void isNotPiecesMove(){
        Square square = game.chessBoard.getSquareAt(0,1);
        assertFalse(king.isPiecesMove(square, game.chessBoard));
    }

    /**
     * tests if piece has moved
     */
    @Test
    public void isHasMoved() {
        king.setNotMoved(false);
        assertFalse(king.hasNotMoved());
    }

    /**
     * tests if output to string works
     */
    @Test
    public void testToString() {
        assertEquals("K", king.toString());
    }

    /**
     * tests castling while path isn't empty
     */
    @Test
    public void canDoCastlingWhilePathBlocked() {
        Piece king = game1.chessBoard.getPieceAt(4,7);
        assertFalse(((King)king).canDoCastling(game.chessBoard.getSquareAt(2, 7), enemies, game1.chessBoard, game1));
    }

    /**
     * tests castling white kingside
     */
    @Test
    public void canDoCastlingWhiteKingside() {
        game1.chessBoard.setPieceAt(5, 7, null);
        game1.chessBoard.setPieceAt(6, 7, null);
        Piece king = game1.chessBoard.getPieceAt(4,7);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6, 7), enemies, game1.chessBoard, game1));
    }

    /**
     * tests castling white queenside
     */
    @Test
    public void canDoCastlingWhiteQueenside() {
        game1.chessBoard.setPieceAt(1, 7, null);
        game1.chessBoard.setPieceAt(2, 7, null);
        game1.chessBoard.setPieceAt(3, 7, null);
        Piece king = game1.chessBoard.getPieceAt(4,7);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 7), enemies, game1.chessBoard, game1));
    }

    /**
     * tests castling black queenside
     */
    @Test
    public void canDoCastlingBlackQueenside() {
        game1.chessBoard.setPieceAt(1, 0, null);
        game1.chessBoard.setPieceAt(2, 0, null);
        game1.chessBoard.setPieceAt(3, 0, null);
        Piece king = game1.chessBoard.getPieceAt(4,0);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 0), enemies, game1.chessBoard, game1));
    }

    /**
     * tests castling black kingside
     */
    @Test
    public void canDoCastlingBlackKingside() {
        game1.chessBoard.setPieceAt(5, 0, null);
        game1.chessBoard.setPieceAt(6, 0, null);
        Piece king = game1.chessBoard.getPieceAt(4,0);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6, 0), enemies, game1.chessBoard, game1));
    }

}
