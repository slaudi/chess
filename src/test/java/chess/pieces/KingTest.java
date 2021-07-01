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

    King king;
    Square square;
    Square squareC3;
    Game game;
    Game game1;
    List<Piece> blackEnemies;
    List<Piece> whiteEnemies;

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
        blackEnemies = game1.playerBlack.getEnemyPieces(game1.beatenPieces,game1.chessBoard);
        whiteEnemies = game1.playerWhite.getEnemyPieces(game1.beatenPieces,game1.chessBoard);
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
     * tests setter for hasn't moved
     */
    @Test
    public void setHasMoved() {
        king.setNotMoved(false);
        assertFalse(king.notMoved);
    }

    /**
     * tests if hasn#t moved is true
     */
    @Test
    public void hasNotMoved() {
        assertTrue(king.hasNotMoved());
    }

    /**
     * tests if piece is able to move as it should
     */
    @Test
    public void isPiecesMove() {
        assertTrue(king.isPiecesMove(squareC3, game.chessBoard));
        // to same square
        assertFalse(king.isPiecesMove(square, game.chessBoard));
        // too far away
        Square square = game.chessBoard.getSquareAt(0,1);
        assertFalse(king.isPiecesMove(square, game.chessBoard));
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
        assertFalse(((King)king).canDoCastling(game.chessBoard.getSquareAt(2, 7), blackEnemies, game1.chessBoard));
    }

    /**
     * tests castling white kingside
     */
    @Test
    public void canDoCastlingWhite() {
        Piece king = game1.chessBoard.getPieceAt(4,7);
        // kingside
        game1.chessBoard.setPieceAt(5, 7, null);
        game1.chessBoard.setPieceAt(6, 7, null);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6, 7), whiteEnemies, game1.chessBoard));
        // queenside
        game1.chessBoard.setPieceAt(1, 7, null);
        game1.chessBoard.setPieceAt(2, 7, null);
        game1.chessBoard.setPieceAt(3, 7, null);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 7), whiteEnemies, game1.chessBoard));
    }

    /**
     * tests castling black queenside
     */
    @Test
    public void canDoCastlingBlack() {
        List <Piece> enemies = game1.playerBlack.getEnemyPieces(game1.beatenPieces, game1.chessBoard);
        Piece king = game1.chessBoard.getPieceAt(4,0);
        // queenside
        game1.chessBoard.setPieceAt(1, 0, null);
        game1.chessBoard.setPieceAt(2, 0, null);
        game1.chessBoard.setPieceAt(3, 0, null);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 0), enemies, game1.chessBoard));
        // kingside
        game1.chessBoard.setPieceAt(5, 0, null);
        game1.chessBoard.setPieceAt(6, 0, null);
        assertTrue(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6, 0), enemies, game1.chessBoard));
    }

    /**
     * Tests if canDoCastling fails at the right times
     */
    @Test
    public void cannotCastle(){
        // Y is not null
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 1),blackEnemies, game1.chessBoard));
        // king already moved
        king.setNotMoved(false);
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 0),blackEnemies, game1.chessBoard));
        // castling path under attack
        game1.chessBoard.clearBoard();
        king = new King(game1.chessBoard.getSquareAt(4,7),Colour.WHITE);
        game1.chessBoard.setPieceAt(4,7,king);
        Piece rook = new Rook(game1.chessBoard.getSquareAt(7,7),Colour.WHITE);
        game1.chessBoard.setPieceAt(7,7,rook);
        Piece enemyRook = new Rook(game1.chessBoard.getSquareAt(5,3),Colour.BLACK);
        game1.chessBoard.setPieceAt(5,3,enemyRook);
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.addBlackAlliance(enemyRook);
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6,7),whiteEnemies,game1.chessBoard));
        // castling path under attack by pawn
        game1.chessBoard.clearBoard();
        game1.chessBoard.setPieceAt(4,7,king);
        game1.chessBoard.setPieceAt(7,7,rook);
        Piece enemyPawn = new Pawn(game1.chessBoard.getSquareAt(6,6),Colour.BLACK);
        game1.chessBoard.setPieceAt(6,6,enemyPawn);
        game1.chessBoard.clearBlackAlliance();
        game1.chessBoard.addBlackAlliance(enemyPawn);
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6,7),whiteEnemies,game1.chessBoard));
    }

    /**
     * Tests if certain moves by black King are rightfully not castle moves.
     */
    @Test
    public void cannotCastleBlack(){
        Piece king = game1.chessBoard.getPieceAt(4,0);
        // queenside, path not empty
        game1.chessBoard.setPieceAt(3, 0, game1.chessBoard.getPieceAt(0,7));
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 0),blackEnemies, game1.chessBoard));
        // kingside, path not empty
        game1.chessBoard.setPieceAt(5, 0, game1.chessBoard.getPieceAt(0,7));
        assertFalse(((King) king).canDoCastling(game1.chessBoard.getSquareAt(6,0),blackEnemies,game1.chessBoard));
        // queenside, rook already move
        game1.chessBoard.setPieceAt(1, 0, null);
        game1.chessBoard.setPieceAt(2, 0, null);
        game1.chessBoard.setPieceAt(3, 0, null);
        game1.chessBoard.getSquareAt(0, 0).getOccupiedBy().setNotMoved(false);
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 0),blackEnemies, game1.chessBoard));
        // kingside, rook already moved
        game1.chessBoard.setPieceAt(5, 0, null);
        game1.chessBoard.setPieceAt(6, 0, null);
        game1.chessBoard.getSquareAt(7, 0).getOccupiedBy().setNotMoved(false);
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6, 0),blackEnemies, game1.chessBoard));
    }

    /**
     * Tests if certain moves by black King are rightfully not castle moves.
     */
    @Test
    public void cannotCastleWhite(){
        Piece king = game1.chessBoard.getPieceAt(4,7);
        // queenside, path not empty
        game1.chessBoard.setPieceAt(3, 7, game1.chessBoard.getPieceAt(2,0));
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 7),blackEnemies, game1.chessBoard));
        // kingside, path not empty
        game1.chessBoard.setPieceAt(5, 7, game1.chessBoard.getPieceAt(2,0));
        assertFalse(((King) king).canDoCastling(game1.chessBoard.getSquareAt(6,7),blackEnemies,game1.chessBoard));
        // queenside, rook already move
        game1.chessBoard.setPieceAt(1, 7, null);
        game1.chessBoard.setPieceAt(2, 7, null);
        game1.chessBoard.setPieceAt(3, 7, null);
        game1.chessBoard.getSquareAt(0, 7).getOccupiedBy().setNotMoved(false);
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(2, 7),blackEnemies, game1.chessBoard));
        // kingside, rook already moved
        game1.chessBoard.setPieceAt(5, 7, null);
        game1.chessBoard.setPieceAt(6, 7, null);
        game1.chessBoard.getSquareAt(7, 7).getOccupiedBy().setNotMoved(false);
        assertFalse(((King)king).canDoCastling(game1.chessBoard.getSquareAt(6, 7),blackEnemies, game1.chessBoard));
    }

    /**
     * Tests if castling is possible for white while path is blocked
     */
    @Test
    public void cannotCastleWhiteKingside(){
        Piece king = game1.chessBoard.getPieceAt(4,7);
        game1.chessBoard.setPieceAt(5, 7, null);
        assertFalse(((King) king).canDoCastling(game1.chessBoard.getSquareAt(6,7),blackEnemies,game1.chessBoard));
    }

    /**
     * Tests if castling is possible for black while path is blocked
     */
    @Test
    public void cannotCastleBlackKingside(){
        Piece king = game1.chessBoard.getPieceAt(4,0);
        game1.chessBoard.setPieceAt(6, 0, null);
        assertFalse(((King) king).canDoCastling(game1.chessBoard.getSquareAt(6,0),whiteEnemies,game1.chessBoard));
    }

    /**
     * Tests if castling is possible for black while path is attacked by white pawn
     */
    @Test
    public void cannotCastleBlackKingsideBecausePawnAttacksPath(){
        Piece king = game1.chessBoard.getPieceAt(4,0);
        game1.chessBoard.setPieceAt(5, 0, null);
        game1.chessBoard.setPieceAt(6, 0, null);
        game1.chessBoard.setPieceAt(5, 1, game1.chessBoard.getPieceAt(0, 6));
        game1.chessBoard.getPieceAt(5, 1).setSquare(game1.chessBoard.getSquareAt(5, 1));
        assertFalse(((King) king).canDoCastling(game1.chessBoard.getSquareAt(6,0),blackEnemies,game1.chessBoard));
    }

    /**
     * Tests if castling is possible for black while path is not attacked by white pawn
     */
    @Test
    public void canCastleBlackKingsideBecausePawnNotAttacksPath(){
        Piece king = game1.chessBoard.getPieceAt(4,0);
        game1.chessBoard.setPieceAt(5, 0, null);
        game1.chessBoard.setPieceAt(6, 0, null);
        game1.chessBoard.setPieceAt(1, 1, game1.chessBoard.getPieceAt(0, 6));
        game1.chessBoard.getPieceAt(1, 1).setSquare(game1.chessBoard.getSquareAt(1, 1));
        assertTrue(((King) king).canDoCastling(game1.chessBoard.getSquareAt(6,0),blackEnemies,game1.chessBoard));
    }
}
