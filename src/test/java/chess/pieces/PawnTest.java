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
    public Square squareF4;
    public Square squareH6;
    public Square squareE6;
    public Square squareC5;
    public Square squareC8;
    public Square squareC6;
    public Stack<Move> moveHistory;

    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = new Square(c4, 2, 4);
        pawnW = new Pawn(squareC4, Colour.WHITE);
        squareC6 = game.chessBoard.getSquareAt(2,2);
        pawnB = new Pawn(squareC6, Colour.BLACK);
        squareF4 = new Square(f4, 5, 4);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
        squareC5 = new Square(c5, 2, 3);
        squareC8 = new Square(c8, 2, 0);
        moveHistory = new Stack<>();
    }

    @Test
    public void getSquare() {
        assertEquals(squareC4, pawnW.getSquare());
    }

    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        pawnW.setSquare(squareC4);
        assertEquals(squareC4, pawnW.getSquare());
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, pawnW.getColour());
    }

    @Test
    public void getType() {
        assertEquals(Type.PAWN, pawnW.getType());
    }

    @Test
    public void getHasMoved() {
        assertTrue(pawnW.hasNotMoved());
    }

    @Test
    public void setHasMoved() {
        pawnW.setNotMoved(false);
        assertFalse(pawnW.hasNotMoved());
    }

    @Test
    public void isPiecesMove() {
        // white pawn
        assertTrue(pawnW.isPiecesMove(squareC5, game.chessBoard));
        // two squares
        assertTrue(pawnW.isPiecesMove(game.chessBoard.getChessBoard()[2][2], game.chessBoard));
        //second Move
        pawnW.setNotMoved(false);
        assertFalse(pawnW.isPiecesMove(game.chessBoard.getChessBoard()[2][2], game.chessBoard));
        assertTrue(pawnW.isPiecesMove(squareC5, game.chessBoard));
        // is not pieces move
        assertFalse(pawnW.isPiecesMove(squareH6, game.chessBoard));
        // same square
        assertFalse(pawnW.isPiecesMove(squareC4, game.chessBoard));

        // black Pawn
        assertTrue(pawnB.isPiecesMove(squareC5, game.chessBoard));
        // two squares
        assertTrue(pawnB.isPiecesMove(game.chessBoard.getChessBoard()[2][4], game.chessBoard));
        // second move
        pawnB.setNotMoved(false);
        assertFalse(pawnB.isPiecesMove(game.chessBoard.getChessBoard()[2][4], game.chessBoard));
        assertTrue(pawnB.isPiecesMove(squareC5, game.chessBoard));
        // is not pieces move
        assertFalse(pawnW.isPiecesMove(squareH6, game.chessBoard));
        // same square
        assertFalse(pawnW.isPiecesMove(squareC6, game.chessBoard));
    }

    @Test
    public void isHasMoved() {
        pawnW.setNotMoved(false);
        assertFalse(pawnW.hasNotMoved());
    }

    @Test
    public void testToString() {
        // white pawn
        assertEquals("P", pawnW.toString());
        // black pawn
        Piece pawn2 = game.chessBoard.getPieceAt(0,1);
        assertEquals("p", pawn2.toString());
    }

    @Test
    public void canCapture() {
        // white pawn
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

        // black pawn
        enemy = new Pawn(squareB5, Colour.BLACK);
        squareB5.setOccupiedBy(enemy);
        assertTrue(pawnB.canCapture(squareB5));
        // in front of Pawn
        squareC5.setOccupiedBy(enemy);
        assertFalse(pawnB.canCapture(squareC5));
        // to far away
        assertFalse(pawnB.canCapture(squareF4));
    }

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

    @Test
    public void promotionPossible() {
        // white pawn
        assertTrue(pawnW.promotionPossible(squareC8));
        //black Pawn
        Piece pawn2 = game.chessBoard.getPieceAt(0,1);
        Square squareA1 = game.chessBoard.getSquareAt(0,7);
        assertTrue(((Pawn)pawn2).promotionPossible(squareA1));
    }
}
