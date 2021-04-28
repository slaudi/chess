package chess.game;

/**
 * Board class representing the Chess-Board of current game.
 */
public class Board {
    public Square[] board;

    /**
     * Create a new Board instance when starting a new Game.
     */
    public Board() {
        board = new Square[64];
        for (int i = 0; i < board.length; i++){
            board[i] = new Square(i);
        }
        startingFormation();
    }
    /**
     * Sets each Chess-Piece on its initial position.
     *
     * TODO insert Piece_Format
     * TODO not very fast implementation
     */
    public void startingFormation() {
        for (int i = 0; i < 64; i++){
            switch (i){
                case 0:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackRook);
                    break;
                case 1:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackKnight);
                    break;
                case 2:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackBishop);
                    break;
                case 3:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackQueen);
                    break;
                case 4:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackKing);
                    break;
                case 5:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackBishop);
                    break;
                case 6:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackKnight);
                    break;
                case 7:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackRook);
                    break;
                case 8:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 9:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 10:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 11:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 12:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 13:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 14:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 15:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(BlackPawn);
                    break;
                case 48:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 49:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 50:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 51:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 52:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 53:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 54:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 55:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhitePawn);
                    break;
                case 56:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteRook);
                    break;
                case 57:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteKnight);
                    break;
                case 58:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteBishop);
                    break;
                case 59:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteQueen);
                    break;
                case 60:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteKing);
                    break;
                case 61:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteBishop);
                    break;
                case 62:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteKnight);
                    break;
                case 63:
                    this.board[i].occupied = true;
                    this.board[i].occupiedBy = PIECE(WhiteRook);
                    break;
            }
        }
    }
}
