package chess.game;

import chess.pieces.Piece;

import java.util.ArrayList;
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
            piece.setMoved(true);
            piece.setSquare(finalSquare);
            board.board[finalSquare.x][finalSquare.y].occupiedBy = piece;
            board.board[startSquare.x][startSquare.y].occupiedBy = null;
    }

    protected void undoMove (Stack<Move> history, Board board){
        // TODO: Wie 'moved' behandeln? Muss nur zurückgesetzt werden, wenn es der erste Move war!
        Move actualMove = history.pop();
        Square start = actualMove.startSquare;
        Square finalSquare = actualMove.finalSquare;
        board.board[start.x][start.y].occupiedBy = actualMove.movingPiece;
        actualMove.movingPiece.setSquare(start);
        board.board[finalSquare.x][finalSquare.y].occupiedBy = null;
    }

}
