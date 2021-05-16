package chess.pieces;

import chess.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The King class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type King.
 */
public class King extends Piece {

    final Type type = Type.KING;

    /**
     * Constructor for creating a King piece.
     *
     * @param square The location of the King on the board.
     * @param colour The Colour associated with the King.
     */
    public King(Square square, Colour colour) {
        super(square, colour);
    }

    @Override
    public Square getSquare() {
        return this.square;
    }

    @Override
    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public Colour getColour() {
        return this.colour;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public boolean hasNotMoved() {
        return this.notMoved;
    }

    @Override
    public void setNotMoved(boolean x) {
        this.notMoved = x;
    }

    @Override
    public String toString() {
        if (this.colour == Colour.WHITE) {
            return "K";
        } else {
            return "k";
        }
    }

    /**
     * A function determining if the King is moving only one Square in any direction and doesn't
     * stay on its original square.
     *
     * @param finalSquare The square where the Bishop should move to.
     * @return boolean Returns 'true' if the move is only one Square in any direction.
     */
    @Override
    public boolean isPiecesMove(Square finalSquare, Board chessBoard) {
        int diff_x = Math.abs(this.square.getX() - finalSquare.getX());
        int diff_y = Math.abs(this.square.getY() - finalSquare.getY());
        if (diff_x == 0 && diff_y == 0) {
            return false;
        }
        return diff_x < 2 && diff_y < 2;
    }

    /**
     * Evaluates if the castling move is possible by checking the selected King and Rook.
     *
     * @param finalSquare   The selected Square which can be kingside or queenside.
     * @return boolean Returns 'true' if castling is possible.
     */
    public boolean canDoCastling (Square finalSquare, List<Piece> enemies, Board currentBoard, Game game) {
        List<Square> castlingPath;
        if (this.hasNotMoved()){
            if(this.getSquare().getX() - finalSquare.getX() == 2){  //queenside
                castlingPath = queensideCastling(currentBoard);
                if (castlingPath.isEmpty()) {
                    return false;
                }
            } else if (this.getSquare().getX() - finalSquare.getX() == -2){     //kingside
                castlingPath = kingsideCastling(currentBoard);
                if (castlingPath.isEmpty()){
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        // check if the Kings current Square and/or any Squares the King visits are in check/under attack
        for (Square field : castlingPath){
            for (Piece enemyPiece : enemies) {
                if(game.isMoveAllowed(enemyPiece, field)){
                    return false;
                }
            }
        }
        return true;
    }

    private List<Square> queensideCastling(Board chessBoard) {
        List<Square> castlingPath = new ArrayList<>();
        if (this.getColour() == Colour.WHITE && chessBoard.getBoard()[0][7].getOccupiedBy() != null) {
            for (int i = 1; i < 4; i++) {
                if (chessBoard.getPieceAt(i, 7) != null || !chessBoard.getPieceAt(0, 7).hasNotMoved()) {
                    return castlingPath; // empty path
                }
            }
            castlingPath.add(chessBoard.getSquareAt(3, 7));
            castlingPath.add(chessBoard.getSquareAt(2, 7));
            castlingPath.add(chessBoard.getSquareAt(1, 7));

        } else if (this.getColour() == Colour.BLACK && chessBoard.getBoard()[0][0].getOccupiedBy() != null){
            for (int i = 1; i < 4; i++) {
                if (chessBoard.getPieceAt(i, 0) != null || !chessBoard.getPieceAt(0, 0).hasNotMoved()) {
                    return castlingPath; // empty path
                }
            }
            castlingPath.add(chessBoard.getSquareAt(3, 0));
            castlingPath.add(chessBoard.getSquareAt(2, 0));
            castlingPath.add(chessBoard.getSquareAt(1, 0));
        }
        return castlingPath;
    }


    private List<Square> kingsideCastling(Board chessBoard) {
        List<Square> castlingPath = new ArrayList<>();
        if (this.getColour() == Colour.WHITE && chessBoard.getBoard()[7][7].getOccupiedBy() != null
                && chessBoard.getPieceAt(5, 7) == null && chessBoard.getPieceAt(6, 7) == null) {
            if(chessBoard.getPieceAt(7, 7).hasNotMoved()){
                castlingPath.add(chessBoard.getSquareAt(5, 7));
                castlingPath.add(chessBoard.getSquareAt(6, 7));
            }
        } else if (this.getColour() == Colour.BLACK && chessBoard.getBoard()[7][0].getOccupiedBy() != null
                && chessBoard.getPieceAt(5, 0) == null && chessBoard.getPieceAt(6, 0) == null) {
            if(chessBoard.getPieceAt(7, 0).hasNotMoved()){
                castlingPath.add(chessBoard.getSquareAt(5, 0));
                castlingPath.add(chessBoard.getSquareAt(6, 0));
            }
        }
        return castlingPath;
    }
}
