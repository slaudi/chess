package chess.game;

import chess.pieces.*;

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
        return this.finalSquare;
    }

    /**
     * A function executing a move on the board
     *
     * @param board current board
     */
    protected void doMove (Board board){
            this.movingPiece.setSquare(this.finalSquare);
            board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(this.movingPiece);
            board.getChessBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(null);
    }

    /**
     * A function undoing the last move on the board if it meant that the King was in check
     *
     * @param history Stack of already done movements
     * @param board current board
     */
    protected void undoMove (Stack<Move> history, Board board){
        Move lastMove = history.pop();
        Square start = lastMove.startSquare;
        Square finalSquare = lastMove.finalSquare;
        board.getChessBoard()[start.getX()][start.getY()].setOccupiedBy(lastMove.movingPiece);
        lastMove.movingPiece.setSquare(start);
        board.getChessBoard()[finalSquare.getX()][finalSquare.getY()].setOccupiedBy(null);
    }

    /**
     * A function executing the castling move on the board
     *
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
        board.getChessBoard()[king_x][king_y].setOccupiedBy(this.startSquare.getOccupiedBy());
        board.getChessBoard()[rook_x][rook_y].setOccupiedBy(this.finalSquare.getOccupiedBy());
        board.getChessBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(null);
        board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(null);
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

        board.getChessBoard()[selectedPawn_x][selectedPawn_y].setOccupiedBy(null);
        board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(this.movingPiece);
        this.movingPiece.setSquare(this.finalSquare);
        board.getChessBoard()[end_x][end_y].setOccupiedBy(null);

        return enemyPawn;
    }

    /**
     * A function executing a promotion on the board
     *
     * @param key   the letter the player enters indicating which Piece they want the Pawn to promote to
     * @param board the current board
     */
    protected void promotion(char key, Board board) {
        this.movingPiece.getSquare().setOccupiedBy(null);
        int promo_x = this.finalSquare.getX();
        int promo_y = this.finalSquare.getY();

        if(key == 'Q' || key == ' ') {
            board.getChessBoard()[promo_x][promo_y].setOccupiedBy(new Queen(this.finalSquare, this.movingPiece.getColour()));
        } else if (key == 'R') {
            board.getChessBoard()[promo_x][promo_y].setOccupiedBy(new Rook(this.finalSquare, this.movingPiece.getColour()));
        } else if (key == 'N') {
            board.getChessBoard()[promo_x][promo_y].setOccupiedBy(new Knight(this.finalSquare, this.movingPiece.getColour()));
        } else if (key == 'B'){
            board.getChessBoard()[promo_x][promo_y].setOccupiedBy(new Bishop(this.finalSquare, this.movingPiece.getColour()));
        }
    }

}
