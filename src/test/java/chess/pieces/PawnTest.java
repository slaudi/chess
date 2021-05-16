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
    public Pawn pawn;
    public Square squareC4;
    public Square squareF4;
    public Square squareH6;
    public Square squareE6;
    public Square squareC5;
    public Square squareC8;
    public Stack<Move> moveHistory;

    @BeforeEach
    public void setUp() {
        game = new Game();
        squareC4 = new Square(c4, 2, 4);
        pawn = new Pawn(squareC4, Colour.WHITE);
        squareF4 = new Square(f4, 5, 4);
        squareH6 = new Square(h6, 7, 2);
        squareE6 = new Square(e6, 4, 2);
        squareC5 = new Square(c5, 2, 3);
        squareC8 = new Square(c8, 2, 0);
        moveHistory = new Stack<>();
    }

    @Test
    public void getSquare() {
        assertEquals(squareC4, pawn.getSquare());
    }

    @Test
    public void setSquare() {
        squareC4 = new Square(g5,7,3);
        pawn.setSquare(squareC4);
        assertEquals(squareC4, pawn.getSquare());
    }

    @Test
    public void getColour() {
        assertEquals(Colour.WHITE, pawn.getColour());
    }

    @Test
    public void getType() {
        assertEquals(Type.PAWN, pawn.getType());
    }

    @Test
    public void getHasMoved() {
        assertTrue(pawn.hasNotMoved());
    }

    @Test
    public void setHasMoved() {
        pawn.setNotMoved(false);
        assertFalse(pawn.hasNotMoved());
    }

    @Test
    public void isPiecesMove() {
        assertTrue(pawn.isPiecesMove(squareC5, game.chessBoard));
    }

    @Test
    public void isPiecesTwoMove() {
        assertTrue(pawn.isPiecesMove(game.chessBoard.getChessBoard()[2][2], game.chessBoard));
    }

    @Test
    public void isPiecesSecondMove() {
        pawn.setNotMoved(false);
        assertTrue(pawn.isPiecesMove(squareC5, game.chessBoard));
    }

    @Test
    public void isHasMoved() {
        pawn.setNotMoved(false);
        assertFalse(pawn.hasNotMoved());
    }

    @Test
    public void testToStringWhite() {
        assertEquals("P", pawn.toString());
    }

    @Test
    public void testToStringBlack() {
        Piece pawn2 = game.chessBoard.getPieceAt(0,1);
        assertEquals("p", pawn2.toString());
    }

    @Test
    public void canCapture() {
        assertFalse(pawn.canCapture(squareF4));
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
    }

    @Test
    public void isNotProcessEnPassant() {
        assertFalse(pawn.isEnPassant(squareC5, moveHistory));
    }

    @Test
    public void promotionPossibleWhite() {
        assertTrue(pawn.promotionPossible(squareC8));
    }

    @Test
    public void promotionPossibleBlack() {
        Piece pawn2 = game.chessBoard.getPieceAt(0,1);
        Square squareA1 = game.chessBoard.getSquareAt(0,7);
        assertTrue(((Pawn)pawn2).promotionPossible(squareA1));
    }
}
