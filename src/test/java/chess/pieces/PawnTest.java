package chess.pieces;

import chess.game.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static chess.game.Label.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PawnTest class test the methods of the Pawn class
 */
public class PawnTest {
    public Game game;
    public Pawn pawnW;
    public Pawn pawnB;
    public Square squareC4;
    public Square squareH6;
    public Square squareE6;
    public Square squareC5;
    public Square squareC8;
    public Square squareC6;
    public Stack<Move> moveHistory;

    /**
     * setUp for each pawn test
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = new Square(c4, 2, 4);
        pawnW = new Pawn(squareC4, Colour.WHITE);
        squareC6 = game.chessBoard.getSquareAt(2,2);
        pawnB = new Pawn(squareC6, Colour.BLACK);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
        squareC5 = new Square(c5, 2, 3);
        squareC8 = new Square(c8, 2, 0);
        moveHistory = new Stack<>();
    }

    /**
     * tests getter for square
     */
    @Test
    public void getSquare() {
        assertEquals(squareC4, pawnW.getSquare());
    }

    /**
     * tests setter for square
     */
    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        pawnW.setSquare(squareC4);
        assertEquals(squareC4, pawnW.getSquare());
    }

    /**
     * tests getter for colour
     */
    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, pawnW.getColour());
    }

    /**
     * tests getter for type
     */
    @Test
    public void getType() {
        assertEquals(Type.PAWN, pawnW.getType());
    }

    /**
     * tests getter for has moved
     */
    @Test
    public void getHasMoved() {
        assertTrue(pawnW.hasNotMoved());
    }

    /**
     * tests setter for has moved
     */
    @Test
    public void setHasMoved() {
        pawnW.setNotMoved(false);
        assertFalse(pawnW.hasNotMoved());
    }

    /**
     * tests allowed moves for white piece
     */
    @Test
    public void isPiecesMoveWhite() {
        // white pawn
        assertTrue(pawnW.isPiecesMove(squareC5, game.chessBoard));
        // two squares
        assertTrue(pawnW.isPiecesMove(game.chessBoard.getBoard()[2][2], game.chessBoard));
    }

    /**
     * Tests not allowed moves for white piece
     */
    @Test
    public void isNotPiecesMoveWhite(){
        //second Move
        pawnW.setNotMoved(false);
        assertFalse(pawnW.isPiecesMove(game.chessBoard.getBoard()[2][2], game.chessBoard));
        assertTrue(pawnW.isPiecesMove(squareC5, game.chessBoard));
        // is not pieces move
        assertFalse(pawnW.isPiecesMove(squareH6, game.chessBoard));
        // same square
        assertFalse(pawnW.isPiecesMove(squareC4, game.chessBoard));
    }

    /**
     * tests allowed moves for black piece
     */
    @Test
    public void isPiecesMoveBlack(){
        // black Pawn
        assertTrue(pawnB.isPiecesMove(squareC5, game.chessBoard));
        // two squares
        assertTrue(pawnB.isPiecesMove(game.chessBoard.getBoard()[2][4], game.chessBoard));
    }

    /**
     * Test not allowed moves for black piece
     */
    @Test
    public void isNotPiecesMoveBlack(){
        // second move
        pawnB.setNotMoved(false);
        assertFalse(pawnB.isPiecesMove(game.chessBoard.getBoard()[2][4], game.chessBoard));
        assertTrue(pawnB.isPiecesMove(squareC5, game.chessBoard));
        // is not pieces move
        assertFalse(pawnB.isPiecesMove(squareH6, game.chessBoard));
        // same square
        assertFalse(pawnB.isPiecesMove(squareC6, game.chessBoard));
    }

    /**
     * tests if has moved works
     */
    @Test
    public void isHasMoved() {
        pawnW.setNotMoved(false);
        assertFalse(pawnW.hasNotMoved());
    }

    /**
     * tests if toStringOutput works
     */
    @Test
    public void testToString() {
        // white pawn
        assertEquals("P", pawnW.toString());
        // black pawn
        Piece pawn2 = game.chessBoard.getPieceAt(0,1);
        assertEquals("p", pawn2.toString());
    }

    /**
     * tests if white pawn is allowed to capture
     */
    @Test
    public void canCaptureWhite() {
        Square squareF4 = new Square(f4, 5, 4);
        Square squareB5 = game.chessBoard.getSquareAt(1,3);
        Piece enemy = new Pawn(squareB5, Colour.BLACK);
        squareB5.setOccupiedBy(enemy);

        assertTrue(pawnW.canCapture(squareB5));
        // to far away
        assertFalse(pawnW.canCapture(squareF4));
        // in front of Pawn
        Square squareC5 = game.chessBoard.getSquareAt(2,3);
        enemy = new Pawn(squareC5, Colour.BLACK);
        squareC5.setOccupiedBy(enemy);
        assertFalse(pawnW.canCapture(squareC5));
    }

    /**
     * Tests if black pawn is allowed to capture
     */
    @Test
    public void canCaptureBlack(){
        Square squareF4 = new Square(f4, 5, 4);
        Square squareB5 = game.chessBoard.getSquareAt(1,3);

        Piece enemy1 = new Pawn(squareB5, Colour.BLACK);
        squareB5.setOccupiedBy(enemy1);
        assertTrue(pawnB.canCapture(squareB5));
        // in front of Pawn
        squareC5.setOccupiedBy(enemy1);
        assertFalse(pawnB.canCapture(squareC5));
        // to far away
        assertFalse(pawnB.canCapture(squareF4));
    }

    /**
     * tests if enPassant is allowed for selected pawn
     */
    @Test
    public void isAllowedEnPassant(){
        Square squareA7 = game.chessBoard.getSquareAt(0,1);
        Square squareA5 = game.chessBoard.getSquareAt(0,3);
        Piece enemy = squareA7.getOccupiedBy();
        enemy.setSquare(squareA5);
        squareA5.setOccupiedBy(enemy);
        Move lastEnemyMove = new Move(squareA5,squareA7);

        Square squareB5 = game.chessBoard.getSquareAt(1,3);
        Pawn pawn = new Pawn(squareB5, Colour.WHITE);
        squareB5.setOccupiedBy(pawn);
        Square moveA6 = game.chessBoard.getSquareAt(0,2);

        assertTrue(pawn.isEnPassant(moveA6, lastEnemyMove));
    }


    /**
     * tests processing of enPassant
     */
    @Test
    public void isProcessEnPassant(){
        Square squareA7 = game.chessBoard.getSquareAt(0,1);
        Square squareA5 = game.chessBoard.getSquareAt(0,3);
        Move lastAllyMove = new Move(squareC4,squareC5);
        Move lastEnemyMove = new Move(squareA7,squareA5);
        moveHistory.add(lastAllyMove);
        moveHistory.add(lastEnemyMove);

        lastEnemyMove.doMove(game.chessBoard);

        Square squareB5 = game.chessBoard.getSquareAt(1,3);
        Pawn pawn = new Pawn(squareB5, Colour.WHITE);
        squareB5.setOccupiedBy(pawn);
        Square moveA6 = game.chessBoard.getSquareAt(0,2);

        assertTrue(pawn.isEnPassant(moveA6, moveHistory));

        // is not en passant
        assertFalse(pawn.isEnPassant(squareC5, moveHistory));
    }

    /**
     * tests if promotionPossibleQuery works
     */
    @Test
    public void promotionPossible() {
        // white pawn
        assertTrue(pawnW.promotionPossible(squareC8));
        //black Pawn
        Piece pawn2 = game.chessBoard.getPieceAt(0,1);
        Square squareA1 = game.chessBoard.getSquareAt(0,7);
        assertTrue(((Pawn)pawn2).promotionPossible(squareA1));
    }

    /**
     * tests the generation of moving direction
     */
    @Test
    public void movingDirection() {
        int[][] dir1 = game.chessBoard.getPieceAt(0, 6).piecesDirection(game.chessBoard.getSquareAt(0, 5));
        int[][] dir2 = game.chessBoard.getPieceAt(1, 6).piecesDirection(game.chessBoard.getSquareAt(1, 5));
        int y1 = dir1[0][1];
        int y2 = dir2[0][1];
        assertEquals(y1, y2);
    }
}
