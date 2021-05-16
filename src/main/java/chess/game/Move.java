package chess.game;

import chess.pieces.*;

/**
 * The Move class executes moves and undoes them if it is evaluated that the move would put the
 * King of the current Player doing the move in check.
 */
public class Move {
    final Square startSquare;
    final Square finalSquare;
    final Piece movingPiece;

    /**
     * Constructor for creating new player.
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

    public Piece getMovingPiece() {
        return movingPiece;
    }

    /**
     * A function executing a move on the board.
     *
     * @param board The current board.
     */
    void doMove (Board board){
            this.movingPiece.setSquare(this.finalSquare);
            board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(this.movingPiece);
            board.getChessBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(null);
    }

    /**
     * A function undoing the last move on the board if it would put the current players King
     * in check (if the final Square was empty).
     *
     * @param board The current board.
     */
    void undoMove (Board board){
        board.getChessBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(this.movingPiece);
        this.movingPiece.setSquare(this.startSquare);
        board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(null);
    }

    /**
     * A function undoing the last move on the board if it would put the current players King
     * in check (if the final Square was occupied by an enemy Piece).
     *
     * @param targetPiece   The Piece captured by the move of the selected Piece.
     * @param board         The current board.
     */
    void undoMove (Piece targetPiece, Board board){
        board.getChessBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(this.movingPiece);
        this.movingPiece.setSquare(this.startSquare);
        board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(targetPiece);
    }

    /**
     * A function executing the castling move on the board.
     *
     * @param board The current board.
     */
    public void castlingMove(Board board, Square finalSquare) {
        int final_x = finalSquare.getX();
        int final_y = finalSquare.getY();
        if(final_x == 2 && final_y == 7){           //queenside white
            board.setPieceAt(2, 7, board.getPieceAt(4, 7));
            board.getPieceAt(2, 7).setSquare(board.getSquareAt(2, 7));
            board.getSquareAt(4, 7).setOccupiedBy(null);
            board.setPieceAt(3, 7, board.getPieceAt(0, 7));
            board.getPieceAt(3, 7).setSquare(board.getSquareAt(3, 7));
            board.getSquareAt(0, 7).setOccupiedBy(null);
        }
        else if(final_x == 6 && final_y == 7){      //kingside white
            board.setPieceAt(6, 7, board.getPieceAt(4, 7));
            board.getPieceAt(6, 7).setSquare(board.getSquareAt(6, 7));
            board.getSquareAt(4, 7).setOccupiedBy(null);
            board.setPieceAt(5, 7, board.getPieceAt(7, 7));
            board.getPieceAt(5, 7).setSquare(board.getSquareAt(5, 7));
            board.getSquareAt(7, 7).setOccupiedBy(null);
        }
        else if(final_x == 2 && final_y == 0){      //queenside black
            board.setPieceAt(2, 0, board.getPieceAt(4, 0));
            board.getPieceAt(2, 0).setSquare(board.getSquareAt(2, 0));
            board.getSquareAt(4, 0).setOccupiedBy(null);
            board.setPieceAt(3, 0, board.getPieceAt(0, 0));
            board.getPieceAt(3, 0).setSquare(board.getSquareAt(3, 0));
            board.getSquareAt(0, 0).setOccupiedBy(null);
        }
        else{                                       //kingside black
            board.setPieceAt(6, 0, board.getPieceAt(4, 0));
            board.getPieceAt(6, 0).setSquare(board.getSquareAt(6, 0));
            board.getSquareAt(4, 0).setOccupiedBy(null);
            board.setPieceAt(5, 0, board.getPieceAt(7, 0));
            board.getPieceAt(5, 0).setSquare(board.getSquareAt(5, 0));
            board.getSquareAt(7, 0).setOccupiedBy(null);
        }
    }

    /**
     * A function executing the en passant move.
     *
     * @param board The current board.
     */
    void enPassantMove(Move lastEnemyMove, Board board) {
        Square lastMoveFinalSquare = lastEnemyMove.getFinalSquare();
        int end_x = lastMoveFinalSquare.getX();
        int end_y = lastMoveFinalSquare.getY();

        int selectedPawn_x = this.startSquare.getX();
        int selectedPawn_y = this.startSquare.getY();

        board.getChessBoard()[selectedPawn_x][selectedPawn_y].setOccupiedBy(null);
        board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(this.movingPiece);
        board.getChessBoard()[end_x][end_y].setOccupiedBy(null);
    }

    /**
     * A function undoing the en passant move if it would put the current players King in check.
     *
     * @param board The current board.
     */
    void undoEnPassant(Piece enemy, Move lastEnemyMove, Board board) {
        Square finalEnemySquare = lastEnemyMove.finalSquare; // the square the enemy stood on before being captured

        board.getChessBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(this.movingPiece);
        board.getChessBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(null);
        board.getChessBoard()[finalEnemySquare.getX()][finalEnemySquare.getY()].setOccupiedBy(enemy);
    }

    /**
     * A function executing a promotion on the board after the move was done by 'doMove' and that didn't
     * put your King in check.
     *
     * @param key   The letter the player entered indicating which Piece they want the Pawn to promote to.
     *              If the player didn't enter a letter the promotion is automatically to a Queen.
     * @param board The current board.
     */
    void doPromotion(char key, Board board) {
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
