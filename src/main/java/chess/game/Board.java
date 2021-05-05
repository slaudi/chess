package chess.game;

import chess.pieces.*;

import java.util.Vector;

import static chess.game.Colour.BLACK;
import static chess.game.Colour.WHITE;



/**
 * Board class representing the Chess-Board of current game.
 */
public class Board {
    public Square[][] board;
    public Vector<Piece> whitePieces = new Vector<>(16);
    public Vector<Piece> blackPieces = new Vector<>(16);


    /**
     * Create a new Board instance when starting a new Game.
     */
    public Board() {
        board = new Square[8][8];
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                board[x][y] = new Square(Label.values()[8*x+y], x, y);
            }
        }
        startingFormation();
        setCollection();
    }
    /**
     * Sets each Chess-Piece on its initial position.
     */
    public void startingFormation() {
        // Black pieces
        this.board[0][0].occupiedBy = new Rook(this.board[0][0],BLACK);
        this.board[1][0].occupiedBy = new Knight(this.board[1][0], BLACK);
        this.board[2][0].occupiedBy = new Bishop(this.board[2][0], BLACK);
        this.board[3][0].occupiedBy = new Queen(this.board[3][0], BLACK);
        this.board[4][0].occupiedBy = new King(this.board[4][0], BLACK);
        this.board[5][0].occupiedBy = new Bishop(this.board[5][0], BLACK);
        this.board[6][0].occupiedBy = new Knight(this.board[6][0], BLACK);
        this.board[7][0].occupiedBy = new Rook(this.board[7][0], BLACK);
        for(int i = 0; i < 8; i++) {
            this.board[i][1].occupiedBy = new Pawn(this.board[i][1], BLACK);
        }

        // White pieces
        this.board[0][7].occupiedBy = new Rook(this.board[0][7], WHITE);
        this.board[1][7].occupiedBy = new Knight(this.board[1][7], WHITE);
        this.board[2][7].occupiedBy = new Bishop(this.board[2][7], WHITE);
        this.board[3][7].occupiedBy = new Queen(this.board[3][7], WHITE);
        this.board[4][7].occupiedBy = new King(this.board[4][7], WHITE);
        this.board[5][7].occupiedBy = new Bishop(this.board[5][7], WHITE);
        this.board[6][7].occupiedBy = new Knight(this.board[6][7], WHITE);
        this.board[7][7].occupiedBy = new Rook(this.board[7][7], WHITE);
        for(int i = 0; i < 8; i++) {
            this.board[i][6].occupiedBy = new Pawn(this.board[i][6], WHITE);
        }
    }

    /**
     * Add all pieces on the board to the Vectors whitePieces and blackPieces according to their colour
     */
    public void setCollection() {
        for(int i = 0; i < 8; i++) {
            whitePieces.add(this.board[i][7].occupiedBy);
            whitePieces.add(this.board[i][6].occupiedBy);
            blackPieces.add(this.board[i][0].occupiedBy);
            blackPieces.add(this.board[i][1].occupiedBy);
        }
    }

    /**
     * Prints current state of game to console.
     */
    public void toConsole(){
        for (int y = 0; y < 8; y++){
            System.out.print(8-y);
            for (int x = 0; x < 8; x++){
                if (this.board[x][y].occupiedBy != null){
                    System.out.print(" " + this.board[x][y].occupiedBy.toString());
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("  a b c d e f g h");
    }

    /**
     * Returns the Piece standing on selected Square.
     * @param input Console-Input as String
     * @return Piece which is on selected Square.
     */
    public Piece getMovingPieceFromInput(String input){
        String start = input.substring(0, 2);
        int startX = Square.getXFromString(start);
        int startY = Square.getYFromString(start);
        return this.board[startX][startY].occupiedBy;
    }

    /**
     * Computes Square where Piece-Movement should start from console input.
     * @param input Console-Input as String.
     * @return Square where Piece-Movement is going to start.
     */
    public Square getStartSquareFromInput(String input){
        String start = input.substring(0, 2);
        int startX = Square.getXFromString(start);
        int startY = Square.getYFromString(start);
        return this.board[startX][startY];
    }

    /**
     * Computes Square where Piece-Movement should end from console input.
     * @param input Console-Input as String
     * @return Square where Piece-Movement is going to end.
     */
    public Square getFinalSquareFromInput(String input){
        String end = input.substring(3, 5);
        int finalX = Square.getXFromString(end);
        int finalY = Square.getYFromString(end);
        return this.board[finalX][finalY];
    }
    public Square getSquareOfWhiteKing(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].occupiedBy != null) {
                    if (board[i][j].occupiedBy.getType() == Type.KING && board[i][j].occupiedBy.getColour() == WHITE) {
                        return board[i][j];
                    }
                }
            }
        }
        return null;
    }
    public Square getSquareOfBlackKing(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (board[i][j].occupiedBy != null) {
                    if (board[i][j].occupiedBy.getType() == Type.KING && board[i][j].occupiedBy.getColour() == BLACK) {
                        return board[i][j];
                    }
                }
            }
        }
        return null;
    }
}
