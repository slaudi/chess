package chess.game;

import chess.pieces.Piece;

import java.util.Stack;

/**
 * Move class to calculate and save the movement of a Chess-Piece.
 */
public class Move {
    Square startSquare;
    Square finalSquare;
    Piece movingPiece;

    /**
     * Constructor of Move-Class.
     *
     * @param start The Square the Movement of Chess-Piece starts at.
     * @param finish The Square the Movement goes to.
     */
    public Move(Square start, Square finish) {
        this.startSquare = start;
        this.finalSquare = finish;
        this.movingPiece = start.getOccupiedBy();
    }

    public Square getStartSquare() {
        return this.startSquare;
    }

    public Square getFinalSquare() {
        return finalSquare;
    }

    /**
     * implementation of doing a move
     * @param board current board
     */
    protected void doMove (Board board){
            movingPiece.setSquare(finalSquare);
            board.getBoard()[finalSquare.getX()][finalSquare.getY()].setOccupiedBy(movingPiece);
            board.getBoard()[startSquare.getX()][startSquare.getY()].setOccupiedBy(null);
    }

    /**
     * undoing a move
     * @param history Stack of already done movements
     * @param board current board
     */
    protected void undoMove (Stack<Move> history, Board board){
        Move actualMove = history.pop();
        Square start = actualMove.startSquare;
        Square finalSquare = actualMove.finalSquare;
        board.getBoard()[start.getX()][start.getY()].setOccupiedBy(actualMove.movingPiece);
        actualMove.movingPiece.setSquare(start);
        board.getBoard()[finalSquare.getX()][finalSquare.getY()].setOccupiedBy(null);
    }

    /**
     * switching positions of King and rook
     * @param board current board
     */
    protected void castlingMove(Board board) {
        int king_x = this.startSquare.getX();
        int king_y = this.startSquare.getY();
        int rook_x = this.finalSquare.getX();
        int rook_y = this.finalSquare.getY();

        int diff = Math.abs(rook_x - king_x);

        if (diff == 3) {
            //kingside castling
            king_x += 2;
            rook_x -= 2;
        } else {
            // queenside castling
            king_x -= 2;
            rook_x += 3;
        }
        board.getBoard()[king_x][king_y].setOccupiedBy(this.startSquare.getOccupiedBy());
        board.getBoard()[rook_x][rook_y].setOccupiedBy(this.finalSquare.getOccupiedBy());
        board.getBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(null);
        board.getBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(null);
    }

    /**
     * a function executing the en passant and extracting the beaten piece from the last move history
     *
     * @param history   a stack where the last moves are stored
     * @param board     the board on which the move shall be executed
     * @return a piece which represents the beaten piece in this move
     */
    protected Piece enPassantMove(Stack<Move> history, Board board) {
        Move lastMove = history.pop();
        Square lastMoveFinalSquare = lastMove.getFinalSquare();
        int end_x = lastMoveFinalSquare.getX();
        int end_y = lastMoveFinalSquare.getY();
        Piece enemyPawn = lastMoveFinalSquare.getOccupiedBy();
        history.add(lastMove);

        int selectedPawn_x = this.startSquare.getX();
        int selectedPawn_y = this.startSquare.getY();

        board.getBoard()[selectedPawn_x][selectedPawn_y].setOccupiedBy(null);
        board.getBoard()[finalSquare.getX()][finalSquare.getY()].setOccupiedBy(movingPiece);
        movingPiece.setSquare(finalSquare);
        board.getBoard()[end_x][end_y].setOccupiedBy(null);

        return enemyPawn;
    }

}
