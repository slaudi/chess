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
        this.movingPiece = start.occupiedBy;
    }


    protected void doMove (Piece piece, Board board){
            piece.setSquare(finalSquare);
            board.board[finalSquare.x][finalSquare.y].occupiedBy = piece;
            board.board[startSquare.x][startSquare.y].occupiedBy = null;
    }

    protected void undoMove (Stack<Move> history, Board board){
        Move actualMove = history.pop();
        Square start = actualMove.startSquare;
        Square finalSquare = actualMove.finalSquare;
        board.board[start.x][start.y].occupiedBy = actualMove.movingPiece;
        actualMove.movingPiece.setSquare(start);
        board.board[finalSquare.x][finalSquare.y].occupiedBy = null;
    }

    protected void castlingMove(Board board) {
        int king_x = this.startSquare.x;
        int king_y = this.startSquare.y;
        int rook_x = this.finalSquare.x;
        int rook_y = this.finalSquare.y;

        int diff = Math.abs(rook_x - king_x);

        board.board[king_x][king_y].occupiedBy = null;
        board.board[rook_x][rook_y].occupiedBy = null;

        if (diff == 3) {
            //kingside castling
            king_x += 2;
            rook_x -= 2;
            board.board[king_x][king_y].occupiedBy = this.startSquare.occupiedBy;
            board.board[rook_x][rook_y].occupiedBy = this.finalSquare.occupiedBy;
        } else {
            // queenside castling
            king_x -= 2;
            rook_x += 3;
            board.board[king_x][king_y].occupiedBy = this.startSquare.occupiedBy;
            board.board[rook_x][rook_y].occupiedBy = this.finalSquare.occupiedBy;
        }
    }
}
