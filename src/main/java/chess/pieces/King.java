package chess.pieces;

import chess.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The King class is a Subclass of the Piece class, implements the interface MovingDirection
 * and represents a Piece of the Type King.
 */
public class King extends Piece {
    //CPD-OFF
    final Type type = Type.KING;

    /**
     * Constructor for creating a King piece.
     *
     * @param square The location of the King on the board.
     * @param colour The Colour associated with the King.
     */
    //it's overriding abstract methods from super class
    public King(Square square, Colour colour) {
        super(square, colour);
    }

    @Override
    //it's overriding abstract methods from super class
    public Square getSquare() {
        return this.square;
    }

    @Override
    //it's overriding abstract methods from super class
    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    //it's overriding abstract methods from super class
    public Colour getColour() {
        return this.colour;
    }

    @Override
    //it's overriding abstract methods from super class
    public Type getType() {
        return this.type;
    }

    @Override
    //it's overriding abstract methods from super class
    public void setNotMoved(boolean x) {
        this.notMoved = x;
    }

    @Override
    //it's overriding abstract methods from super class
    public boolean hasNotMoved() {
        return this.notMoved;
    }

    @Override
    //it's overriding abstract methods from super class
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

    @Override
    //it's overriding abstract methods from super class
    public int getPositionalValue(int x, int y, boolean endgame) {
        int[][] kingSquareTable;
        if(endgame) {
            if (this.colour == Colour.WHITE) {
                kingSquareTable = new int[][]
                        {
                                {-50,-40,-30,-20,-20,-30,-40,-50},
                                {-30,-20,-10,  0,  0,-10,-20,-30},
                                {-30,-10, 20, 30, 30, 20,-10,-30},
                                {-30,-10, 30, 40, 40, 30,-10,-30},
                                {-30,-10, 30, 40, 40, 30,-10,-30},
                                {-30,-10, 20, 30, 30, 20,-10,-30},
                                {-30,-30,  0,  0,  0,  0,-30,-30},
                                {-50,-30,-30,-30,-30,-30,-30,-50}
                        };
            } else {
                kingSquareTable = new int[][]
                        {
                                {-50,-30,-30,-30,-30,-30,-30,-50},
                                {-30,-30,  0,  0,  0,  0,-30,-30},
                                {-30,-10, 20, 30, 30, 20,-10,-30},
                                {-30,-10, 30, 40, 40, 30,-10,-30},
                                {-30,-10, 30, 40, 40, 30,-10,-30},
                                {-30,-10, 20, 30, 30, 20,-10,-30},
                                {-30,-20,-10,  0,  0,-10,-20,-30},
                                {-50,-40,-30,-20,-20,-30,-40,-50}
                        };
            }
        } else {
            if(this.colour == Colour.WHITE){
                kingSquareTable = new int[][]
                        {
                                {-30, -40, -40, -50, -50, -40, -40, -30},
                                {-30, -40, -40, -50, -50, -40, -40, -30},
                                {-30, -40, -40, -50, -50, -40, -40, -30},
                                {-30, -40, -40, -50, -50, -40, -40, -30},
                                {-20, -30, -30, -40, -40, -30, -30, -20},
                                {-10, -20, -20, -20, -20, -20, -20, -10},
                                { 20,  20,   0,   0,   0,   0,  20,  20},
                                { 20,  30,  10,   0,   0,  10,  30,  20}
                        };

            }else {
                kingSquareTable = new int[][]
                        {
                                { 20, 30, 10,  0,  0, 10, 30, 20},
                                { 20, 20,  0,  0,  0,  0, 20, 20},
                                {-10,-20,-20,-20,-20,-20,-20,-10},
                                {-20,-30,-30,-40,-40,-30,-30,-20},
                                {-30,-40,-40,-50,-50,-40,-40,-30},
                                {-30,-40,-40,-50,-50,-40,-40,-30},
                                {-30,-40,-40,-50,-50,-40,-40,-30},
                                {-30,-40,-40,-50,-50,-40,-40,-30},
                        };
            }

        }
        return kingSquareTable[y][x];
    }
    // CPD-ON

    /**
     * Evaluates if the castling move is possible by checking the selected King and Rook.
     *
     * @param enemies a List of enemy-pieces of current king
     * @param currentBoard state of game.
     * @param finalSquare   The selected Square which can be kingside or queenside.
     * @return boolean Returns 'true' if castling is possible.
     */
    public boolean canDoCastling (Square finalSquare, List<Piece> enemies, Board currentBoard) {
        List<Square> castlingPath;
        if (this.notMoved){
            if(this.getSquare().getX() - finalSquare.getX() == 2 && this.getSquare().getY() - finalSquare.getY() == 0){  //queenside
                castlingPath = queensideCastling(currentBoard);
                if (castlingPath.isEmpty()) {
                    return false;
                }
            } else if (this.getSquare().getX() - finalSquare.getX() == -2 && this.getSquare().getY() - finalSquare.getY() == 0){     //kingside
                castlingPath = kingsideCastling(currentBoard);
                if (castlingPath.isEmpty()){
                    return false;
                }
            } else {
                // Y is not 0
                return false;
            }
        } else {
            // King has already moved
            return false;
        }
        // check if the Kings current Square and/or any Squares the King visits are in check/under attack
        return !underAttack(castlingPath, enemies, currentBoard);
    }

    private List<Square> queensideCastling(Board chessBoard) {
        List<Square> castlingPath = new ArrayList<>();
        if (helperCastling(Colour.WHITE, 0,7, chessBoard)) {
            for (int i = 1; i < 4; i++) {
                if (chessBoard.getPieceAt(i, 7) != null ) {
                    return castlingPath; // empty path
                }
            }
            castlingPath.add(chessBoard.getSquareAt(4,7));
            castlingPath.add(chessBoard.getSquareAt(3,7));
            castlingPath.add(chessBoard.getSquareAt(2,7));

        } else if (helperCastling(Colour.BLACK,0,0,chessBoard)){
            for (int i = 1; i < 4; i++) {
                if (chessBoard.getPieceAt(i, 0) != null) {
                    return castlingPath; // empty path
                }
            }
            castlingPath.add(chessBoard.getSquareAt(4,0));
            castlingPath.add(chessBoard.getSquareAt(3,0));
            castlingPath.add(chessBoard.getSquareAt(2,0));
        }
        return castlingPath;
    }

    private List<Square> kingsideCastling(Board chessBoard) {
        List<Square> castlingPath = new ArrayList<>();
        if (helperCastling(Colour.WHITE,7,7,chessBoard)) {
            if(chessBoard.getPieceAt(5, 7) == null && chessBoard.getPieceAt(6, 7) == null){
                castlingPath.add(chessBoard.getSquareAt(4,7));
                castlingPath.add(chessBoard.getSquareAt(5,7));
                castlingPath.add(chessBoard.getSquareAt(6,7));
            }
        } else if (helperCastling(Colour.BLACK,7,0,chessBoard)) {
            if (chessBoard.getPieceAt(5, 0) != null && chessBoard.getPieceAt(6, 0) != null) {
                return castlingPath;
            }
            castlingPath.add(chessBoard.getSquareAt(4, 0));
            castlingPath.add(chessBoard.getSquareAt(5, 0));
            castlingPath.add(chessBoard.getSquareAt(6, 0));
        }
        return castlingPath;
    }


    private boolean helperCastling(Colour colour, int x, int y, Board chessBoard) {
        return this.getColour() == colour && chessBoard.getBoard()[x][y].getOccupiedBy() != null
                && chessBoard.getPieceAt(x, y).notMoved;
    }


    private boolean underAttack(List<Square> castlingPath, List<Piece> enemies, Board currentBoard){
        for (Square field : castlingPath){
            for (Piece enemyPiece : enemies) {
                if(enemyPiece.getType() == Type.PAWN) {
                    if (pawnPathCapture(enemyPiece, field)) {
                        return true;
                    }
                } else if(enemyPiece.isPiecesMove(field, currentBoard)
                        && enemyPiece.isPathEmpty(field, currentBoard)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean pawnPathCapture(Piece pawn, Square attackSquare) {
        int diff_x = attackSquare.getX() - pawn.getSquare().getX();
        int diff_y = attackSquare.getY() - pawn.getSquare().getY();

        if (pawn.colour == Colour.WHITE) {
            return Math.abs(diff_x) == 1 && diff_y == -1;
        } else {
            return Math.abs(diff_x) == 1 && diff_y == 1;
        }
    }
}
