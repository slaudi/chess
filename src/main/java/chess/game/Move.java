package chess.game;

import chess.pieces.*;

/**
 * The Move class executes moves and undoes them if it is evaluated that the move would put the
 * King of the current Player doing the move in check.
 */
public class Move {
    private final Square startSquare;
    private final Square finalSquare;
    private Piece movingPiece;

    private int boardValueAfterMove = 0;

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

    public int getBoardValueAfterMove() {
        return boardValueAfterMove;
    }

    public void setBoardValueAfterMove(int value){
        this.boardValueAfterMove = value;
    }

    /**
     * Helper-Method for Game-Loading. At last move of move-history you need to set the moving piece, so that e.g. enPassant-Evaluation works properly
     */
    public void setMovingPieceToPieceOnFinalSquare(){
        this.movingPiece = this.finalSquare.getOccupiedBy();
    }

    /**
     * A function executing a move on the board.
     *
     * @param board The current board.
     */
    public void doMove (Board board){
            this.movingPiece.setSquare(this.finalSquare);
            board.getBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(this.movingPiece);
            board.getBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(null);
    }

    /**
     * A function undoing the last move on the board if it would put the current players King
     * in check (if the final Square was empty).
     *
     * @param board The current board.
     */
    public void undoMove (Board board){
        board.getBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(this.movingPiece);
        this.movingPiece.setSquare(this.startSquare);
        board.getBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(null);
    }

    /**
     * A function undoing the last move on the board if it would put the current players King
     * in check (if the final Square was occupied by an enemy Piece).
     *
     * @param targetPiece   The Piece captured by the move of the selected Piece.
     * @param board         The current board.
     */
    public void undoMove (Piece targetPiece, Board board){
        board.getBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(this.movingPiece);
        this.movingPiece.setSquare(this.startSquare);
        board.getBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(targetPiece);
    }

    /**
     * A function executing the castling move on the board.
     *
     * @param board The current board.
     */
    public void castlingMove(Board board) {
        Piece rook;
        if(this.finalSquare.getX() == 2 && this.finalSquare.getY() == 7){           //queenside white
            rook = board.getPieceAt(0, 7);
            board.setPieceAt(2, 7, this.movingPiece);
            this.movingPiece.setSquare(board.getSquareAt(2, 7));
            board.getSquareAt(4, 7).setOccupiedBy(null);
            board.setPieceAt(3, 7, rook);
            rook.setSquare(board.getSquareAt(3, 7));
            board.getSquareAt(0, 7).setOccupiedBy(null);
        }
        else if(this.finalSquare.getX() == 6 && this.getFinalSquare().getY() == 7){      //kingside white
            rook = board.getPieceAt(7, 7);
            board.setPieceAt(6, 7, this.movingPiece);
            this.movingPiece.setSquare(board.getSquareAt(6, 7));
            board.getSquareAt(4, 7).setOccupiedBy(null);
            board.setPieceAt(5, 7, rook);
            rook.setSquare(board.getSquareAt(5, 7));
            board.getSquareAt(7, 7).setOccupiedBy(null);
        }
        else if(this.finalSquare.getX() == 2 && this.finalSquare.getY() == 0){      //queenside black
            rook = board.getPieceAt(0, 0);
            board.setPieceAt(2, 0, this.movingPiece);
            this.movingPiece.setSquare(board.getSquareAt(2, 0));
            board.getSquareAt(4, 0).setOccupiedBy(null);
            board.setPieceAt(3, 0, rook);
            rook.setSquare(board.getSquareAt(3, 0));
            board.getSquareAt(0, 0).setOccupiedBy(null);
        }
        else{                                       //kingside black
            rook = board.getPieceAt(7, 0);
            board.setPieceAt(6, 0, this.movingPiece);
            this.movingPiece.setSquare(board.getSquareAt(6, 0));
            board.getSquareAt(4, 0).setOccupiedBy(null);
            board.setPieceAt(5, 0, rook);
            rook.setSquare(board.getSquareAt(5, 0));
            board.getSquareAt(7, 0).setOccupiedBy(null);
        }
        rook.setNotMoved(false);
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

        board.getBoard()[selectedPawn_x][selectedPawn_y].setOccupiedBy(null);
        board.getBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(this.movingPiece);
        board.getBoard()[end_x][end_y].setOccupiedBy(null);
    }

    /**
     * A function undoing the en passant move if it would put the current players King in check.
     *
     * @param board The current board.
     */
    void undoEnPassant(Piece enemy, Move lastEnemyMove, Board board) {
        Square finalEnemySquare = lastEnemyMove.finalSquare; // the square the enemy stood on before being captured

        board.getBoard()[this.startSquare.getX()][this.startSquare.getY()].setOccupiedBy(this.movingPiece);
        board.getBoard()[this.finalSquare.getX()][this.finalSquare.getY()].setOccupiedBy(null);
        board.getBoard()[finalEnemySquare.getX()][finalEnemySquare.getY()].setOccupiedBy(enemy);
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
        Piece piece = null;
        int promo_x = this.finalSquare.getX();
        int promo_y = this.finalSquare.getY();
        if(key == 'Q' || key == ' ') {
            piece = new Queen(this.finalSquare, this.movingPiece.getColour());
            board.getBoard()[promo_x][promo_y].setOccupiedBy(piece);
        } else if (key == 'R') {
            piece = new Rook(this.finalSquare, this.movingPiece.getColour());
            board.getBoard()[promo_x][promo_y].setOccupiedBy(piece);
        } else if (key == 'N') {
            piece = new Knight(this.finalSquare, this.movingPiece.getColour());
            board.getBoard()[promo_x][promo_y].setOccupiedBy(piece);
        } else if (key == 'B'){
            piece = new Bishop(this.finalSquare, this.movingPiece.getColour());
            board.getBoard()[promo_x][promo_y].setOccupiedBy(piece);
        }
        Colour colour = movingPiece.getColour();
        if (colour == Colour.WHITE) {
            board.addWhiteAlliance(piece);
        } else {
            board.addBlackAlliance(piece);
        }

    }

}
