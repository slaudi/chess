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
    void castlingMove(Board board) {
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

        // set the square of the Rook to it's new square
        Square rookSquare = board.getChessBoard()[rook_x][rook_y];
        this.finalSquare.getOccupiedBy().setSquare(rookSquare);
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
