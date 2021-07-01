package chess.pieces;

import chess.game.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static chess.game.Label.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PawnTest class test the methods of the Pawn class
 */
public class PawnTest {
    Game game;
    Pawn pawnW;
    Pawn pawnB;
    Square squareC4;
    Square squareH6;
    Square squareE6;
    Square squareC5;
    Square squareC8;
    Square squareC6;
    List<Move> moveHistory;

    /**
     * setUp for each pawn test
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = game.chessBoard.getSquareAt(2, 4);
        pawnW = new Pawn(squareC4, Colour.WHITE);
        squareC6 = game.chessBoard.getSquareAt(2,2);
        pawnB = new Pawn(squareC6, Colour.BLACK);
        squareH6 = game.chessBoard.getSquareAt(7, 2);
        squareE6 = game.chessBoard.getSquareAt(4, 2);
        squareC5 = game.chessBoard.getSquareAt(2, 3);
        squareC8 = game.chessBoard.getSquareAt(2, 0);
        moveHistory = new ArrayList<>();
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
     * tests setter for has moved
     */
    @Test
    public void setHasMoved() {
        pawnW.setNotMoved(false);
        assertFalse(pawnW.notMoved);
    }

    /**
     * tests if hasn#t moved is true
     */
    @Test
    public void hasNotMoved() {
        assertTrue(pawnB.hasNotMoved());
    }

    /**
     * tests allowed moves for white piece
     */
    @Test
    public void isPiecesMoveWhite() {
        // one square first move
        assertTrue(pawnW.isPiecesMove(squareC5, game.chessBoard));
        // two squares first move
        assertTrue(pawnW.isPiecesMove(squareC6, game.chessBoard));
        // second move
        pawnW.setNotMoved(false);
        assertTrue(pawnW.isPiecesMove(squareC5, game.chessBoard)); //one square
    }

    /**
     * tests allowed moves for black piece
     */
    @Test
    public void isPiecesMoveBlack(){
        // one square first move
        assertTrue(pawnB.isPiecesMove(squareC5, game.chessBoard));
        // two squares first move
        assertTrue(pawnB.isPiecesMove(squareC4, game.chessBoard));
        // second move one square
        pawnB.setNotMoved(false);
        assertTrue(pawnB.isPiecesMove(squareC5, game.chessBoard));
    }

    /**
     * Test not allowed moves for both black and white pawns.
     */
    @Test
    public void isNotPiecesMove(){
        // path not empty
        pawnW = new Pawn(squareC5, Colour.WHITE);
        squareC5.setOccupiedBy(pawnW);
        assertFalse(pawnB.isPiecesMove(game.chessBoard.getSquareAt(2,4), game.chessBoard));
        squareC5.setOccupiedBy(null);
        // final square not empty
        pawnW = new Pawn(squareC4, Colour.WHITE);
        squareC4.setOccupiedBy(pawnW);
        assertFalse(pawnB.isPiecesMove(squareC4, game.chessBoard));
        // same square
        assertFalse(pawnW.isPiecesMove(squareC4, game.chessBoard));
        // x != 0
        assertFalse(pawnW.isPiecesMove(game.chessBoard.getSquareAt(1,2), game.chessBoard));
    }

    /**
     * Tests not allowed moves for white piece
     */
    @Test
    public void isNotPiecesMoveWhite(){
        game.chessBoard.clearBoard();
        pawnW = new Pawn(squareC4, Colour.WHITE);
        squareC4.setOccupiedBy(pawnW);
        // wrong direction
        assertFalse(pawnW.isPiecesMove(game.chessBoard.getBoard()[2][5], game.chessBoard));
        // first move too far
        assertFalse(pawnW.isPiecesMove(squareC8, game.chessBoard));
        //second move too far
        pawnW.setNotMoved(false);
        assertFalse(pawnW.isPiecesMove(squareC6, game.chessBoard));
    }

    /**
     * Test not allowed moves for black piece
     */
    @Test
    public void isNotPiecesMoveBlack(){
        game.chessBoard.clearBoard();
        pawnB = new Pawn(squareC6, Colour.BLACK);
        squareC6.setOccupiedBy(pawnB);
        // wrong direction
        assertFalse(pawnB.isPiecesMove(game.chessBoard.getBoard()[2][1], game.chessBoard));
        // second move two squares
        pawnB.setNotMoved(false);
        assertFalse(pawnB.isPiecesMove(game.chessBoard.getBoard()[2][4], game.chessBoard));
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
        // too far away
        assertFalse(pawnB.canCapture(squareF4));
    }

    /**
     * tests if enPassant is allowed for selected pawn
     */
    @Test
    public void isAllowedEnPassantWhite(){
        // possible
        Square squareA7 = game.chessBoard.getSquareAt(0,1);
        Square squareA5 = game.chessBoard.getSquareAt(0,3);
        Piece enemyB = squareA7.getOccupiedBy();
        enemyB.setSquare(squareA5);
        squareA7.setOccupiedBy(null);
        squareA5.setOccupiedBy(enemyB);
        Move lastEnemyMoveB = new Move(squareA7,squareA5);

        Square squareC7 = game.chessBoard.getSquareAt(2,1);
        Square squareC5 = game.chessBoard.getSquareAt(2,3);
        Piece enemyC = squareC7.getOccupiedBy();
        enemyC.setSquare(squareC5);
        squareC7.setOccupiedBy(null);
        squareC5.setOccupiedBy(enemyB);
        Move lastEnemyMoveC = new Move(squareC7,squareC5);

        Square squareB5 = game.chessBoard.getSquareAt(1,3);
        Pawn pawn = new Pawn(squareB5, Colour.WHITE);
        squareB5.setOccupiedBy(pawn);

        Square squareA6 = game.chessBoard.getSquareAt(0,2);
        assertTrue(pawn.isEnPassant(squareA6, lastEnemyMoveB));

        Square squareC6 = game.chessBoard.getSquareAt(2,2);
        assertTrue(pawn.isEnPassant(squareC6, lastEnemyMoveC));

        // not possible, one y-step too far
        assertFalse(pawn.isEnPassant(squareA7,lastEnemyMoveB));
        // not possible, one x-step too far
        Square squareD6 = game.chessBoard.getSquareAt(3,2);
        assertFalse(pawn.isEnPassant(squareD6,lastEnemyMoveB));
    }

    /**
     * tests if enPassant is allowed for selected pawn
     */
    @Test
    public void isAllowedEnPassantBlack() {
        // possible
        Square squareC2 = game.chessBoard.getSquareAt(2,6);
        Piece enemyW = squareC2.getOccupiedBy();
        Move lastEnemyMoveW = new Move(squareC2,squareC4);
        squareC4.setOccupiedBy(enemyW);
        squareC2.setOccupiedBy(null);
        Square squareB4 = game.chessBoard.getSquareAt(1,4);
        squareB4.setOccupiedBy(pawnB);
        pawnB.setSquare(squareB4);
        Square squareC3 = game.chessBoard.getSquareAt(2,5);
        assertTrue(pawnB.isEnPassant(squareC3, lastEnemyMoveW));
        // not possible, one y-step too far
        assertFalse(pawnB.isEnPassant(squareC2, lastEnemyMoveW));
        // not possible, one x-step too far
        Square squareD3 = game.chessBoard.getSquareAt(3,5);
        assertFalse(pawnB.isEnPassant(squareD3,lastEnemyMoveW));

        // not possible, no pawn
        Piece rook = game.chessBoard.getSquareAt(0,0).getOccupiedBy();
        squareC2.setOccupiedBy(rook);
        lastEnemyMoveW = new Move(squareC2,squareC4);
        squareC4.setOccupiedBy(rook);
        squareC2.setOccupiedBy(null);
        assertFalse(pawnB.isEnPassant(squareC2, lastEnemyMoveW));
    }


    /**
     * tests processing of enPassant
     */
    @Test
    public void isProcessEnPassant(){
        // possible, white pawn
        Square squareA7 = game.chessBoard.getSquareAt(0,1);
        Square squareA5 = game.chessBoard.getSquareAt(0,3);
        Move lastAllyMove = new Move(squareC4,squareC5);
        Move lastEnemyMove = new Move(squareA7,squareA5);
        moveHistory.add(lastAllyMove);
        moveHistory.add(lastEnemyMove);
        lastEnemyMove.doMove(game.chessBoard);
        Square squareB5 = game.chessBoard.getSquareAt(1,3);
        pawnW.setSquare(squareB5);
        squareB5.setOccupiedBy(pawnW);
        Square moveA6 = game.chessBoard.getSquareAt(0,2);
        assertTrue(pawnW.isEnPassant(moveA6, moveHistory));

        // possible, black pawn
        Square squareB4 = game.chessBoard.getSquareAt(1,4);
        Square squareB6 = game.chessBoard.getSquareAt(1,2);
        Piece enemy = game.chessBoard.getPieceAt(1,6);
        squareB4.setOccupiedBy(enemy);
        Move enemyMove = new Move(squareB4, squareB6);
        enemyMove.doMove(game.chessBoard);
        moveHistory.add(enemyMove);
        assertTrue(pawnB.isEnPassant(squareB5, moveHistory));
    }

    /**
     * tests if promotionPossibleQuery works
     */
    @Test
    public void promotionPossible() {
        // white pawn
        assertTrue(pawnW.promotionPossible(squareC8));
        //black Pawn
        Square squareA1 = new Square(a1,0,7);
        assertTrue(pawnB.promotionPossible(squareA1));
        // not possible
        assertFalse(pawnB.promotionPossible(squareC8));
    }


}
