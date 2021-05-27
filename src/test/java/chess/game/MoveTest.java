package chess.game;

import chess.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The MoveTest class test the methods of the Move class
 */
public class MoveTest {

    Game game1;
    Move move1;
    Square start1;
    Square end1;
    Piece piece;
    Piece kingB;
    Piece kingW;


    /**
     * setUp for MoveTesting
     */
    @BeforeEach
    public void setUp() {
        game1 = new Game();
        start1 = game1.chessBoard.getBoard()[0][6];
        end1 = game1.chessBoard.getBoard()[0][5];
        move1 = new Move(start1, end1);
        piece = game1.chessBoard.getBoard()[0][6].getOccupiedBy();
        kingB = game1.chessBoard.getPieceAt(4,0);
        kingW = game1.chessBoard.getPieceAt(4,7);
    }

    /**
     * tests getter for start square from move
     */
    @Test
    public void getStartSquare() {
        assertEquals(start1, move1.getStartSquare());
    }

    /**
     * tests getter for final square from move
     */
    @Test
    public void getFinalSquare() {
        assertEquals(end1, move1.getFinalSquare());
    }

    /**
     * tests getter for List movingPiece
     */
    @Test
    public void getMovingPiece() {
        assertEquals(piece, move1.getMovingPiece());
    }

    /**
     * tests implementation for doing a move
     */
    @Test
    public void doMove() {
        Game game2 = new Game();
        move1.doMove(game1.chessBoard);
        move1.doMove(game2.chessBoard);
        assertEquals(Arrays.deepToString(game1.chessBoard.getBoard()), Arrays.deepToString(game2.chessBoard.getBoard()));
    }

    /**
     * tests implementation for undoing a move without a capture
     */
    @Test
    public void undoMove() {
        move1.doMove(game1.chessBoard);
        move1.undoMove(game1.chessBoard);
        Board board = new Board(8,8);
        String chessBoard = Arrays.deepToString(board.getBoard());
        assertEquals(chessBoard, Arrays.deepToString(game1.chessBoard.getBoard()));
    }

    /**
     * tests implementation for undoing a move after a capture
     */
    @Test
    public void undoMovePiece() {
        Piece rook = game1.chessBoard.getPieceAt(0,0);
        game1.chessBoard.setPieceAt(end1.getX(), end1.getY(), rook);
        move1.doMove(game1.chessBoard);
        move1.undoMove(rook, game1.chessBoard);
        Board test = new Board(8,8);
        test.setPieceAt(end1.getX(), end1.getY(), rook);
        String chessBoard = Arrays.deepToString(test.getBoard());
        assertEquals(chessBoard, Arrays.deepToString(game1.chessBoard.getBoard()));
    }

    /**
     * tests implementation for enPassant
     */
    @Test
    public void enPassantMove() {
        Square squareW = game1.chessBoard.getBoard()[0][3];
        Square squareW2 = game1.chessBoard.getBoard()[1][2];
        Piece pawnW = new Pawn(squareW,Colour.WHITE);
        squareW.setOccupiedBy(pawnW);
        Move move = new Move(squareW, squareW2);

        Square squareB = game1.chessBoard.getBoard()[1][1];
        Square squareB2 = game1.chessBoard.getBoard()[1][3];
        Piece pawnB = new Pawn(squareB2, Colour.BLACK);
        squareB2.setOccupiedBy(pawnB);
        Move lastEnemyMove = new Move(squareB,squareB2);

        move.enPassantMove(lastEnemyMove,game1.chessBoard);
        assertNull(squareW.getOccupiedBy());
        assertEquals(pawnW, squareW2.getOccupiedBy());
        assertNull(squareB2.getOccupiedBy());

    }

    /**
     * tests implementation for undoing enPassant
     */
    @Test
    public void undoEnPassant() {
        Square squareW = game1.chessBoard.getBoard()[0][3];
        Square squareW2 = game1.chessBoard.getBoard()[1][2];
        Piece pawnW = new Pawn(squareW,Colour.WHITE);
        squareW.setOccupiedBy(pawnW);
        Move move = new Move(squareW, squareW2);

        Square squareB = game1.chessBoard.getBoard()[1][1];
        Square squareB2 = game1.chessBoard.getBoard()[1][3];
        Piece pawnB = new Pawn(squareB2, Colour.BLACK);
        squareB2.setOccupiedBy(pawnB);
        Move lastEnemyMove = new Move(squareB,squareB2);

        move.enPassantMove(lastEnemyMove,game1.chessBoard);
        move.undoEnPassant(pawnB, lastEnemyMove,game1.chessBoard);
        assertEquals(pawnW, squareW.getOccupiedBy());
        assertEquals(pawnB, squareB2.getOccupiedBy());
        assertNull(squareW2.getOccupiedBy());
    }

    /**
     * tests if promotion works without key
     */
    @Test
    public void promotionMove() {
        Square start2 = game1.chessBoard.getBoard()[0][1];
        Square end2 = game1.chessBoard.getBoard()[0][2];
        Move move2 = new Move(start2, end2);
        // no key:Queen
        move1.doPromotion(' ',game1.chessBoard);
        Piece queen = new Queen(end2, Colour.WHITE);
        assertEquals(queen.getType(), end1.getOccupiedBy().getType());

        //bishop
        move1.doPromotion('B',game1.chessBoard);
        Piece bishop = new Bishop(end2, Colour.WHITE);
        assertEquals(bishop.getType(), end1.getOccupiedBy().getType());

        // rook
        move1.doPromotion('R',game1.chessBoard);
        Piece rook = new Rook(end2, Colour.BLACK);
        assertEquals(rook.getType(), end1.getOccupiedBy().getType());

        // Knight
        move2.doPromotion('N',game1.chessBoard);
        Piece knight = new Knight(end2, Colour.WHITE);
        assertEquals(knight.getType(), end2.getOccupiedBy().getType());

        // not the right key
        move1.doPromotion('J', game1.chessBoard);
        knight = new Knight(end1, Colour.WHITE);
        assertNotEquals(knight.getType(), end1.getOccupiedBy().getType());
    }


    /**
     * tests castling white kingside
     */
    @Test
    public void castlingMoveWhiteKingside() {
        game1.chessBoard.setPieceAt(5, 7, null);
        game1.chessBoard.setPieceAt(6, 7, null);
        Move castling = new Move(kingW.getSquare(), game1.chessBoard.getSquareAt(6,7));
        castling.castlingMove(game1.chessBoard);
        assertNull(game1.chessBoard.getPieceAt(7, 7));
        assertNull(game1.chessBoard.getPieceAt(4,7));
        assertEquals(Type.ROOK, game1.chessBoard.getPieceAt(5, 7).getType());
        assertEquals(Type.KING, game1.chessBoard.getPieceAt(6,7).getType());
    }

    /**
     * tests castling white queenside
     */
    @Test
    public void castlingMoveWhiteQueenside() {
        game1.chessBoard.setPieceAt(1, 7, null);
        game1.chessBoard.setPieceAt(2, 7, null);
        game1.chessBoard.setPieceAt(3, 7, null);
        Move castling = new Move(kingW.getSquare(), game1.chessBoard.getSquareAt(2,7));
        castling.castlingMove(game1.chessBoard);
        assertNull(game1.chessBoard.getPieceAt(0, 7));
        assertNull(game1.chessBoard.getPieceAt(4,7));
        assertEquals(Type.ROOK, game1.chessBoard.getPieceAt(3, 7).getType());
        assertEquals(Type.KING, game1.chessBoard.getPieceAt(2,7).getType());
    }

    /**
     * tests castling black queenside
     */
    @Test
    public void castlingMoveBlackQueenside() {
        game1.chessBoard.setPieceAt(1, 0, null);
        game1.chessBoard.setPieceAt(2, 0, null);
        game1.chessBoard.setPieceAt(3, 0, null);
        Move castling = new Move(game1.chessBoard.getSquareAt(4,0), game1.chessBoard.getSquareAt(2,0));
        castling.castlingMove(game1.chessBoard);
        assertNull(game1.chessBoard.getPieceAt(0, 0));
        assertNull(game1.chessBoard.getPieceAt(4,0));
        assertEquals(Type.ROOK, game1.chessBoard.getPieceAt(3, 0).getType());
        assertEquals(Type.KING, game1.chessBoard.getPieceAt(2,0).getType());
    }

    /**
     * tests castling black kingside
     */
    @Test
    public void castlingMoveBlackKingside() {
        game1.chessBoard.setPieceAt(5, 0, null);
        game1.chessBoard.setPieceAt(6, 0, null);
        Move castling = new Move(kingB.getSquare(),game1.chessBoard.getSquareAt(6,0));
        castling.castlingMove(game1.chessBoard);
        assertNull(game1.chessBoard.getPieceAt(7, 0));
        assertNull(game1.chessBoard.getPieceAt(4,0));
        assertEquals(Type.ROOK, game1.chessBoard.getPieceAt(5, 0).getType());
        assertEquals(Type.KING, game1.chessBoard.getPieceAt(6,0).getType());
    }
}