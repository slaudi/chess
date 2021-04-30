package chess.game;

import chess.pieces.*;

import static chess.game.Colour.BLACK;
import static chess.game.Colour.WHITE;



/**
 * Board class representing the Chess-Board of current game.
 */
public class Board {
    public Square[][] board;


    /**
     * Create a new Board instance when starting a new Game.
     */
    public Board() {
        board = new Square[8][8];
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                board[x][y] = new Square(Label.values()[(8*x+y)], x, y);
            }
        }
        startingFormation();
    }
    /**
     * Sets each Chess-Piece on its initial position.
     */
    public void startingFormation() {
        // Black pieces
        this.board[0][0].occupied = true;
        this.board[0][0].occupiedBy = new Rook(this.board[0][0],BLACK);
        this.board[0][1].occupied = true;
        this.board[0][1].occupiedBy = new Knight(this.board[0][1], BLACK);
        this.board[0][2].occupied = true;
        this.board[0][2].occupiedBy = new Bishop(this.board[0][2], BLACK);
        this.board[0][3].occupied = true;
        this.board[0][3].occupiedBy = new Queen(this.board[0][3], BLACK);
        this.board[0][4].occupied = true;
        this.board[0][4].occupiedBy = new King(this.board[0][4], BLACK);
        this.board[0][5].occupied = true;
        this.board[0][5].occupiedBy = new Bishop(this.board[0][5], BLACK);
        this.board[0][6].occupied = true;
        this.board[0][6].occupiedBy = new Knight(this.board[0][6], BLACK);
        this.board[0][7].occupied = true;
        this.board[0][7].occupiedBy = new Rook(this.board[0][7], BLACK);
        for(int i = 0; i < 8; i++) {
            this.board[1][i].occupied = true;
            this.board[1][i].occupiedBy = new Pawn(this.board[1][i], BLACK);
        }

        // White pieces
        this.board[7][0].occupied = true;
        this.board[7][0].occupiedBy = new Rook(this.board[7][0], WHITE);
        this.board[7][1].occupied = true;
        this.board[7][1].occupiedBy = new Knight(this.board[7][1], WHITE);
        this.board[7][2].occupied = true;
        this.board[7][2].occupiedBy = new Bishop(this.board[7][2], WHITE);
        this.board[7][3].occupied = true;
        this.board[7][3].occupiedBy = new Queen(this.board[7][3], WHITE);
        this.board[7][4].occupied = true;
        this.board[7][4].occupiedBy = new King(this.board[7][4], WHITE);
        this.board[7][5].occupied = true;
        this.board[7][5].occupiedBy = new Bishop(this.board[7][5], WHITE);
        this.board[7][6].occupied = true;
        this.board[7][6].occupiedBy = new Knight(this.board[7][6], WHITE);
        this.board[7][7].occupied = true;
        this.board[7][7].occupiedBy = new Rook(this.board[7][7], WHITE);
        for(int i = 0; i < 8; i++) {
            this.board[6][i].occupied = true;
            this.board[6][i].occupiedBy = new Pawn(this.board[6][i], WHITE);
        }
    }

    public void toConsole(){
        for (int y = 0; y < 8; y++){
            System.out.print(8-y);
            for (int x = 0; x < 8; x++){
                if (this.board[y][x].occupied){
                    System.out.print(" " + this.board[y][x].occupiedBy.toString());
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("  a b c d e f g h");
    }
}
